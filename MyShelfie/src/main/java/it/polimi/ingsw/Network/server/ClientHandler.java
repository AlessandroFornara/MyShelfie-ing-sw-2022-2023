package it.polimi.ingsw.Network.server;

import it.polimi.ingsw.Network.client.ClientInformation;
import it.polimi.ingsw.Network.messages.*;
import it.polimi.ingsw.Network.messages.ErrorMessages.NotValidMove;
import it.polimi.ingsw.Network.messages.ErrorMessages.NotValidUsernameError;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.io.IOException;
import java.io.PrintWriter;

public class ClientHandler implements Runnable, Observer {
    private Server server;
    private Observable observable;
    private ClientInformation clientInformation;
    private final Converter c = new Converter();

    public ClientHandler(Server server, ClientInformation client, Observable observable) {
        this.server = server;
        this.clientInformation = client;
        this.observable = observable;
        this.observable.addObserver(this);
    }

    @Override
    public void run() {
        try {

            sendMessage(new ChooseUsernameMessage(), clientInformation.getOut());
            String line = clientInformation.getIn().nextLine();

            Message m = c.convertFromJSON(line);

            while(!server.isUsernameTaken(((UsernameAnswer) m).getS())){
                sendMessage(new NotValidUsernameError(), clientInformation.getOut());
                line = clientInformation.getIn().nextLine();
                m = c.convertFromJSON(line);
            }

            Server.Lock2.release();

            while (true) {

                line = clientInformation.getIn().nextLine();

                if (line.equals("quit")) {
                    break;
                } else {
                    m = c.convertFromJSON(line);
                    switch (m.getType()){
                        case "NumberOfPlayers" -> {
                            server.setNumberOfPlayers(((NumberOfPlayersAnswer) m).getNum());
                            Server.Lock1.release();
                        }
                        case "Move" -> {
                            if (server.controller.dummyInput(((MoveMessage) m).getS())){
                                sendMessage(new NotValidMove(), clientInformation.getOut());
                            }
                            else {
                                String[] tiles = ((MoveMessage) m).getS().split(",");
                                int i = tiles.length;
                                boolean done = false;
                                //i = number of tiles * 2 + 1;
                                switch (i) {
                                    case 3:
                                        //we have taken 1 tile;
                                        done = server.controller.pickCard(tiles[0].charAt(0)-'a', Integer.parseInt(tiles[1]), Integer.parseInt(tiles[2]));
                                        break;
                                    case 5:
                                        //we have taken 2 tiles;
                                        done = server.controller.pickCard(tiles[0].charAt(0)-'a', Integer.parseInt(tiles[1]), tiles[2].charAt(0)-'a', Integer.parseInt(tiles[3]), Integer.parseInt(tiles[4]));
                                        break;
                                    case 7:
                                        //we have taken 3 tiles;
                                        done = server.controller.pickCard(tiles[0].charAt(0)-'a', Integer.parseInt(tiles[1]), tiles[2].charAt(0)-'a', Integer.parseInt(tiles[3]), tiles[4].charAt(0)-'a', Integer.parseInt(tiles[5]), Integer.parseInt(tiles[6]));
                                        break;
                                }

                                if(!done){
                                    sendMessage(new NotValidMove(), clientInformation.getOut());
                                }else{
                                    server.controller.finishTurn();
                                    Server.Lock1.release();
                                }
                            }

                            //TODO: create handleMessage method in serve
                        }
                    }
                }
            }
            //Chiudo gli stream e il socket
            clientInformation.getIn().close();
            clientInformation.getOut().close();
            clientInformation.getSocket().close();
            System.out.println("User disconnected");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Message message) {
        sendMessage(message, clientInformation.getOut());
    }

    public void sendMessage(Message m, PrintWriter out){
        String jsonString = c.convertToJSON(m);
        out.println(jsonString);
    }

    public ClientInformation getClientInformation() {
        return clientInformation;
    }
}

