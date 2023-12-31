package it.polimi.ingsw.Network.server;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.ItemEnum;
import it.polimi.ingsw.Network.client.RmiClientInterface;
import it.polimi.ingsw.Network.messages.Answers.*;
import it.polimi.ingsw.Network.messages.*;
import it.polimi.ingsw.Network.messages.ErrorMessages.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.Utils;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.sleep;

/**
 * This class manages socket and rmi servers
 * @author Alessandro Fornara
 * @author Donato Fiore
 */
public class ServerManager implements Runnable{
    private final Map<Integer, Socket> socketClients = new HashMap<>();
    private final Map<Integer, RmiClientInterface> rmiClients = new HashMap<>();
    private final Map<Integer, String> answers = new HashMap<>();
    private final Map<Integer, Boolean> answerReady = new HashMap<>();
    private final Map<Integer, String> lobby = new ConcurrentHashMap<>();
    private final Map<Integer, String> nicknames = new HashMap<>();
    private Controller activeMatch;
    private final List<Integer> disconnectedPlayers = new ArrayList<>();
    private SocketServer socketServer;
    private RmiServer rmiServer;
    private int idClient;
    private boolean firstPlayer;
    private int numberOfPlayers;
    private boolean win;
    private boolean isTimeExceeded;
    private boolean gameStarted;
    private boolean isTimeExceededPt2;

    public ServerManager() {
        this.firstPlayer = true;
        this.numberOfPlayers = 0;
        this.idClient = 0;
        this.win = false;
        this.isTimeExceeded = false;
        this.isTimeExceededPt2 = false;
        this.gameStarted = false;
    }

    /**
     * This method adds a socket client to the hashmaps
     * @param client the client to be added
     */
    void addClient(Socket client) {
        socketClients.put(idClient, client);
        answerReady.put(idClient, true);
        idClient++;
    }

    /**
     * This method adds a RMI client to the hashmaps
     * @param client the client to be added to the hashmaps
     */
    void addClient(RmiClientInterface client) {
        rmiClients.put(idClient, client);
        answerReady.put(idClient, true);
        idClient++;
    }

    public String getNickname(int playerId) {
        return nicknames.get(playerId);
    }

    /**
     * This method is used to tell a rmi client to shut down
     */
    protected boolean stopRmiClient() {
        return this.isTimeExceededPt2;
    }

    protected void setTimeExceededPt2(){
        this.isTimeExceededPt2 = false;
    }

    /**
     * This method returns the number of a player by his username
     * @param value the username of the player
     * @return the number of the player
     */
    private int getNumberByUsernameFromLobby(String value) {
        for (Map.Entry<Integer, String> entry : lobby.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * This method returns the number of the active player
     * @return the number of the player
     */
    protected int getNumberActivePlayer() {
        String activeUsername = activeMatch.getActivePlayerUsername();
        for (Map.Entry<Integer, String> entry : lobby.entrySet()) {
            if (entry.getValue().equals(activeUsername)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * This method returns the number of a player
     * @param client the socket client of the player
     * @return the number of the player
     */
    public int getNumber(Socket client){
        for (Map.Entry<Integer, Socket> entry : socketClients.entrySet())
            if (entry.getValue() == client)
                return entry.getKey();
        return -1;
    }

        /**
     * This method returns the number of a player
     * @param client the RMI client of the player
     * @return the number of the player
     */
    public int getNumber(RmiClientInterface client) {
        for (Map.Entry<Integer, RmiClientInterface> entry : rmiClients.entrySet())
            if (entry.getValue() == client)
                return entry.getKey();
        throw new NoSuchElementException();
    }

    protected void setAnswer(int client, String answer) {
        answers.put(client, answer);
        answerReady.put(client, true);
    }

    /**
     * Removes the client from the lobby
     * @param client
     */
    protected void removeClient(Socket client) {
        try {
            int number = getNumber(client);
            System.out.println("number getNumber: " + number);
            if(number > -1){
                socketClients.get(number).close();
                socketClients.remove(number);
                removeClient(number);
            }
        } catch (NoSuchElementException e) {
            //Do nothing
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * the client is disconnected for some problems or the timeout; so we remove him from lobby, and we add him to disconnectedPlayers
     * */
    void removeClient(RmiClientInterface client) {
        try {
            int number = getNumber(client);
            rmiClients.remove(number);
            removeClient(number);
        } catch (NoSuchElementException e) {
            //Do nothing
        }
    }

    private void removeClient(int number) {
        System.out.println("remove Client method with number: " + number);
            if (lobby.containsKey(number)) {
                removeClientFromLobby(number);
            }
        this.disconnectedPlayers.add(number);
        if (this.gameStarted && !activeMatch.isDisconnected(nicknames.get(number))){
            System.out.println("controller set disconnected");
            activeMatch.disconnect(nicknames.get(number));
        }
        System.out.println("Client " + number + " removed.");
    }

    /**
     * This method removes a client from the lobby
     * @param number the number of the client to be removed
     */
    private void removeClientFromLobby(int number) {
        String name = lobby.get(number);
        lobby.remove(number);
        System.out.println("remove client from lobby: " + number);

        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients) {
            sendMessageAndWaitForAnswer(i, new UserDisconnection(name));
//            notifyTimeLeft(i, clients.length);//to be removed?
        }
    }


    /**
     * This method sends a message to a client using rmi or socket technology and waits for its answer
     * @param number Client id
     * @param message Message
     * @return Client's answer
     */
    protected String sendMessageAndWaitForAnswer(int number, Message message) {
        String serializedMessage = Converter.convertToJSON(message);
        while (!answerReady.get(number)) {
            try {
                sleep(Constants.MILLIS_TO_WAIT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        answerReady.put(number, false);
        Communication communication;
        if (socketClients.containsKey(number)) {
            communication = new SocketCommunication(serializedMessage, socketClients.get(number), socketServer, number, this);
            new Thread(communication).start();
        } else if (rmiClients.containsKey(number)) {
            communication = new RmiCommunication(serializedMessage, rmiClients.get(number), rmiServer, number, this);
            new Thread(communication).start();
        } else {
            System.out.println("Unregistered Client");
            return Constants.GENERIC_ERROR;
        }

        this.isTimeExceeded = false;
        int counter = 0;

        while (!answerReady.get(number)) {
            try {
                sleep(Constants.MILLIS_TO_WAIT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            counter++;
            if (counter % Constants.MILLIS_TO_WAIT == 0) {
                if (rmiClients.containsKey(number)) {
                    try {
                        rmiClients.get(number).testAliveness();
                    } catch (RemoteException e) {
                        System.out.println("Unable to reach client " + e.getMessage());
                        rmiServer.unregister(rmiClients.get(number));
                        return Constants.GENERIC_ERROR;
                    }
                }
                if (socketClients.containsKey(number)) {
                    try {
                        socketClients.get(number).getInetAddress().isReachable(Constants.MILLIS_IN_SECOND);
                    } catch (IOException e) {
                        System.out.println("Unable to reach client " + e.getMessage());
                        socketServer.unregister(socketClients.get(number));
                        return Constants.GENERIC_ERROR;
                    }
                }
            }
            if (counter > Constants.secondsDuringTurn * Constants.MILLIS_IN_SECOND / Constants.MILLIS_TO_WAIT) {
                System.out.println("expired time in sendMessageAndWaitForAnswer");
                isTimeExceeded = true;
                break;
            }
        }

        if (isTimeExceeded) {
            //communication.setTimeExceeded();
            if(rmiClients.containsKey(number)){
                this.isTimeExceededPt2 = true;
                rmiServer.unregister(rmiClients.get(number));
            }else if(socketClients.containsKey(number))
                socketServer.unregister(socketClients.get(number));

            return Constants.DISCONNECT;

        }
        return answers.get(number);
    }

    /**
     * This method is used when a player connects to the server
     * @param number id of the player
     * @return username the player chose
     */
    private String login(int number){
        String answer = sendMessageAndWaitForAnswer(number, new ChooseUsernameMessage());
        if(answer.equals(Constants.DISCONNECT) || answer.equals(Constants.GENERIC_ERROR))
            return answer;
        Message m = Converter.convertFromJSON(answer);

        while (isUsernameTaken(((UsernameAnswer) m).getString(), number)){
            answer = sendMessageAndWaitForAnswer(number, new NotValidUsernameError());

            if(answer.equals(Constants.DISCONNECT) || answer.equals(Constants.GENERIC_ERROR))
                return answer;
            m = Converter.convertFromJSON(answer);
        }
        return ((UsernameAnswer) m).getString();
    }

    /**
     * This method adds a client to the lobby
     * @param number Client's id
     * @throws IOException
     */
    protected void addClientToLobby(int number) throws IOException {
        String username = login(number);
        if(username.equals(Constants.DISCONNECT))
            return ;
        sendMessageAndWaitForAnswer(number, new UserIdMessage(number));

        if(this.firstPlayer){
            String answer = sendMessageAndWaitForAnswer(number, new FirstPlayerMessage());
            if(answer.equals(Constants.DISCONNECT) || answer.equals(Constants.GENERIC_ERROR)) {
                return;
            }
            Message m = Converter.convertFromJSON(answer);
            this.numberOfPlayers = ((NumberOfPlayersAnswer) m).getNum();
            this.firstPlayer = false;
        }
        lobby.put(number, username);
        notifyNewConnection(this.numberOfPlayers);

        if(lobby.size() == this.numberOfPlayers && !gameStarted){
            this.gameStarted = true;
            startGame();
        }
    }

    /**
     * adds a client to the log of the server
     * @param temporaryId Temporary id for the client
     */
    void addClientToLog(int temporaryId) throws IOException, InterruptedException {
        String code;
        int oldId;
        Message m;
        while (true) {
            String answer = sendMessageAndWaitForAnswer(temporaryId, new ReconnectionMessage());
            if(answer.equals(Constants.DISCONNECT) || answer.equals(Constants.GENERIC_ERROR))
                break;
            m = Converter.convertFromJSON(answer);
            if(((ReconnectionAnswer) m).getString().equals(Constants.RECONNECT)) {
                code = sendMessageAndWaitForAnswer(temporaryId, new OldGameId());
                if (code.equals(Constants.GENERIC_ERROR) || code.equals(Constants.DISCONNECT))
                    break;
                m = Converter.convertFromJSON(code);
                oldId = ((OldGameIdAnswer) m).getId();
                if (checkIfDisconnected(oldId)) {

                    if (!switchClientId(oldId, temporaryId))
                        break;
                    disconnectedPlayers.remove(Integer.valueOf(oldId));
                    lobby.put(oldId, nicknames.get(oldId));
                    sendMessageAndWaitForAnswer(oldId, new WelcomeBackMessage(nicknames.get(oldId)));
                    activeMatch.reconnect(nicknames.get(oldId));
                    break;
                }else{
                    sendMessageAndWaitForAnswer(temporaryId, new OldIdNotValid());
                }
            } else {
                if(!this.gameStarted){
                    addClientToLobby(temporaryId);
                    break;
                }else if(!this.isAvailableSpace()){
                    sendMessageAndWaitForAnswer(temporaryId, new RefusedConnectionMessage());
                    if(rmiClients.containsKey(temporaryId)){
                        rmiServer.unregister(rmiClients.get(temporaryId));
                    }else if(socketClients.containsKey(temporaryId))
                        socketServer.unregister(socketClients.get(temporaryId));
                    break;
                }
                addClientToLobby(temporaryId);
                break;
            }
        }
    }

    /**
     * check if the id is in the list of disconnected players
     * @param code the id of the player
     * @return true if the player had been disconnected
     * */
    private boolean checkIfDisconnected(int code) {
        System.out.print("disconnected Players: ");
        for (Integer disconnectedPlayer : disconnectedPlayers)
            System.out.print(disconnectedPlayer);
        System.out.println();
        if (isDisconnected(code))
            return true;
        if (!answerReady.getOrDefault(code, false))
            return false;
        return isDisconnected(code);
    }

    private boolean isAvailableSpace(){
        return lobby.size() < this.numberOfPlayers;
    }

    /**
     * This method is used to switch the id of a player when he reconnects to the server
     * @param oldId the id of the player before his disconnection
     * @param temporaryId the new id of the player when he reconnected again with the server
     * @return true if the oldId is present in  socketClients or rmiClients maps
     * */
    private boolean switchClientId(int oldId, int temporaryId) {
        if (socketClients.containsKey(temporaryId)) {
            socketClients.put(oldId, socketClients.get(temporaryId));
            socketClients.remove(temporaryId);
            return true;
        }
        if (rmiClients.containsKey(temporaryId)) {
            rmiClients.put(oldId, rmiClients.get(temporaryId));
            rmiClients.remove(temporaryId);
            return true;
        }
        return false;
    }

    /**
     * This method is used to notify all the clients in the lobby that a new player has connected
     * @param numberOfPlayers the number of players in the lobby
     */
    private void notifyNewConnection(int numberOfPlayers) {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients) {
            sendMessageAndWaitForAnswer(i, new LobbyMessage(lobby.size(), numberOfPlayers));
            if(clients.length < this.numberOfPlayers) {
                sendMessageAndWaitForAnswer(i, new WaitingMessage());
            }
            else if (clients.length == numberOfPlayers) {
                sendMessageAndWaitForAnswer(i, new StartingGameMessage());
            }
        }
    }

    /**
     * This method check if a username has already been taken by another client, if not it is added to a map of usernames
     * @param nickname username
     * @return true if the username has already been taken, false otherwise
     */
    private boolean isUsernameTaken(String nickname, int idClient){
        for(String s: nicknames.values()){
            if(nickname.equals(s)){
                return true;
            }
        }
        nicknames.put(idClient, nickname);
        return false;
    }

    /**
     * This method starts a new game.
     * saves the game in a file.
     * sends the graphical info to the clients.
     * sends the first player the message to start his turn.
     * checks if the game is finished.
     * handles the game until it is finished.
     * handles the win.
     * @throws IOException if the file is not found
     */
    private void startGame() throws IOException {
        activeMatch = checkMemoryDisk();

        while (!win) {

            saveGame();
            System.out.println("Game has been saved");
            String activeUsername;
            int x;
            ItemEnum[][][] shelves = new ItemEnum[4][][];
            String[] usernames = new String[4];

            do{
                activeUsername = activeMatch.getActivePlayerUsername();
                //id of the active player;
                x = getNumberByUsernameFromLobby(activeUsername);
                System.out.println("the active user is: " + activeUsername + ", " + x);
            }while(x==-1);

            for (Integer i : this.lobby.keySet()) {
                shelves[i] = activeMatch.getPlayerBookshelf(this.lobby.get(i));
                usernames[i] = this.lobby.get(i);
            }
            //Sending graphical info on the game's status
            for (Integer i : this.lobby.keySet()) {
 //               System.out.println("sending graphical info to User " + i);
                sendMessageAndWaitForAnswer(i, new GraphicalGameInfo(activeMatch.getBoard(), activeMatch.getCommonCardsDesigns(), usernames, shelves, activeMatch.getPlayerPersonalCard(this.lobby.get(i)), activeUsername, i));
            }

            //Sending to the active player a move request and handling the answer;
            String answer = sendMessageAndWaitForAnswer(x, new MoveMessage(activeUsername));
            if(!answer.equals(Constants.DISCONNECT) && !answer.equals(Constants.GENERIC_ERROR)) {
                Message m = Converter.convertFromJSON(answer);
                handleMoveAnswer(x, m);
            }

            this.win = activeMatch.finishTurn();

            if(this.win) {
                Map<Integer, String> usernamePlayers = new HashMap<>();
                for(int j=0; j<this.numberOfPlayers; j++)
                    usernamePlayers.put(j, activeMatch.getUsernamePlayer(j));
//                int points = activeMatch.declareWinner();
                int[] points = new int[this.numberOfPlayers];
//                for(Integer j : this.lobby.keySet()){
//                    sendMessageAndWaitForAnswer(j, new WinMessage(activeMatch.getActivePlayerUsername(), points));
//                }
                if(this.activeMatch.getStopMatch()){
                    int counter = 0;
                    while(true) {
                        try {
                            sleep(Constants.MILLIS_IN_SECOND);
                            counter++;
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        if (counter >= Constants.secondsDuringTurn) {
                            points = activeMatch.declareWinner();
                            for (Integer j : this.lobby.keySet()) {
                                sendMessageAndWaitForAnswer(j, new WinMessage(activeMatch.getActivePlayerUsername(), points[activeMatch.getIdActivePlayer()], usernamePlayers, points));
                            }
                            break;
                        } else if (lobby.size() > 1) {
                            this.win = false;
                            this.activeMatch.setStopMatch();
                            break;
                        }
                    }
                }else{
                    points = activeMatch.declareWinner();
                    for (Integer j : this.lobby.keySet()) {
                        sendMessageAndWaitForAnswer(j, new WinMessage(activeMatch.getActivePlayerUsername(), points[activeMatch.getIdActivePlayer()], usernamePlayers, points));
                    }
                    saveGame();
                }
            }
            System.out.println();
        }
        //System.exit(0);
    }

    /**
     * This method handles a {@link MoveAnswer} message from a player
     * @param number id of the player
     * @param m message
     */
    private void handleMoveAnswer(int number, Message m) {
        int done = 6;
        boolean exit = false;
        while (!exit) {

            if (done == 1) {
                m = Converter.convertFromJSON(sendMessageAndWaitForAnswer(number, new EmptyPositionError()));
            } else if (done == 2) {
                m = Converter.convertFromJSON(sendMessageAndWaitForAnswer(number, new NotEnoughSpaceBookshelfError()));
            } else if (done == 3) {
                m = Converter.convertFromJSON(sendMessageAndWaitForAnswer(number, new NoFreeSideError()));
            } else if (done == 4) {
                m = Converter.convertFromJSON(sendMessageAndWaitForAnswer(number, new NotAdjacTiles()));
            } else if (done == 5) {
                m = Converter.convertFromJSON(sendMessageAndWaitForAnswer(number, new NotEnoughSpaceColumnError()));
            }
            while (activeMatch.dummyInput(((MoveAnswer) m).getS())) {
                m = Converter.convertFromJSON(sendMessageAndWaitForAnswer(number, new NotValidMoveError()));
            }
            String[] tiles = ((MoveAnswer) m).getS().split(",");
            int i = tiles.length;
            //i = number of tiles * 2 + 1;
            switch (i) {
                case 3 ->
                    //we have taken 1 tile;
                        done = activeMatch.pickCard(tiles[0].charAt(0) - 'a', Integer.parseInt(tiles[1]), Integer.parseInt(tiles[2]));
                case 5 ->
                    //we have taken 2 tiles;
                        done = activeMatch.pickCard(tiles[0].charAt(0) - 'a', Integer.parseInt(tiles[1]), tiles[2].charAt(0) - 'a', Integer.parseInt(tiles[3]), Integer.parseInt(tiles[4]));
                case 7 ->
                    //we have taken 3 tiles;
                        done = activeMatch.pickCard(tiles[0].charAt(0) - 'a', Integer.parseInt(tiles[1]), tiles[2].charAt(0) - 'a', Integer.parseInt(tiles[3]), tiles[4].charAt(0) - 'a', Integer.parseInt(tiles[5]), Integer.parseInt(tiles[6]));
            }

            if (done == 0) {
                int points1 = activeMatch.controlCommonCards(0);
                int points2 = activeMatch.controlCommonCards(1);
                if (points1 != 0) {
                    for (Integer j : this.lobby.keySet()) {
                        sendMessageAndWaitForAnswer(j, new CommonCardMessage(this.lobby.get(number), 1, points1));
                    }
                }
                if (points2 != 0) {
                    for (Integer j : this.lobby.keySet()) {
                        sendMessageAndWaitForAnswer(j, new CommonCardMessage(this.lobby.get(number), 2, points2));
                    }
                }
                exit = true;
            }
        }
    }

    /**
     * This method checks if a player is disconnected
     * @param code the id of the player
     * @return true if the player was disconnected
     * */
    public boolean isDisconnected(Integer code){
        //System.out.println("I'm in isDisconnected?? " + code);
        return disconnectedPlayers.contains(code);
    }

    @Override
    public void run() {
        socketServer = new SocketServer(this, Constants.PORT_SOCKET_GAME);
        try {
            rmiServer = new RmiServer(this, Constants.PORT_RMI_GAME);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        new Thread(socketServer).start();
        new Thread(rmiServer).start();
    }

    /**
     * This method saves the game
     */
    private void saveGame() throws IOException {
        String save = this.activeMatch.getModelSave();

        String filePath = "save.txt";
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(save);
        fileWriter.close();
    }

    /**
     * This method loads a game that has been saved in file
     * @return game {@link Model}
     */
    private Model loadGame() throws IOException {
        File file = new File("save.txt");

        if(file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String content = stringBuilder.toString();

            return Converter.convertModelFromJSON(content);
        }
        return null;
    }

    /**
     * This method checks if there is game present on the memory disk
     * @return a controller linked to the model loaded from memory
     */
    private Controller checkMemoryDisk() throws IOException {
        Model m = loadGame();

        boolean samePlayers = true;

        if(m!=null) {
            m.getBoard().setCommonCards();
            Player[] players = m.getPlayers();
            ArrayList<String> oldUsernames = new ArrayList<>();

            for (Player player : players) {
                oldUsernames.add(player.getUsername());
            }

            if (oldUsernames.size() == this.numberOfPlayers) {

                Utils.mergeSort(oldUsernames, 0, oldUsernames.size() - 1);
                List<String> lobbyList = new ArrayList<>(lobby.values());
                Utils.mergeSort(lobbyList, 0, lobbyList.size() - 1);

                for(int k = 0; k<oldUsernames.size() && samePlayers; k++)
                    if(!lobbyList.get(k).equals(oldUsernames.get(k))){
                        samePlayers = false;
                    }

                if (samePlayers) {
                    activeMatch = new Controller(m);
                    System.out.println("A game save has been restored");
                    activeMatch.setActivePlayer(activeMatch.getIdActivePlayer());
                }
                else{
                    activeMatch = new Controller(numberOfPlayers);
                    System.out.println("A new game has been created");
                    for (Integer j : this.lobby.keySet()) {
                        activeMatch.setUsernamePlayer(lobby.get(j));
                    }
                    activeMatch.setFirstPlayer();
                }
            }else{
                activeMatch = new Controller(numberOfPlayers);
                System.out.println("A new game has been created");
                for (Integer j : this.lobby.keySet()) {
                    activeMatch.setUsernamePlayer(lobby.get(j));
                }
                activeMatch.setFirstPlayer();
            }
        }
        else {
            activeMatch = new Controller(numberOfPlayers);
            System.out.println("A new game has been created");
            for (Integer j : this.lobby.keySet()) {
                activeMatch.setUsernamePlayer(lobby.get(j));
            }
            activeMatch.setFirstPlayer();
        }

        return activeMatch;
    }
}