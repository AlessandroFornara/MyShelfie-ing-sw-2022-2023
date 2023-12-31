package it.polimi.ingsw.model;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.Constants;
import it.polimi.ingsw.ItemEnum;
import it.polimi.ingsw.Network.messages.Converter;
import it.polimi.ingsw.model.CommonCards.CommonCardStrategy;

import java.util.Random;

/**
 * This class implements the logic of the game
 * @author Donato Fiore
 */
public class Model {
    @Expose
    private Board board;
    @Expose
    private final int numPlayers;
    @Expose
    private Player[] players;
    @Expose
    private Player activePlayer;
    @Expose
    private Card[] personalCards;
    @Expose
    private int commonPoints1, commonPoints2;
    @Expose
    private int idFirstPlayer;
    @Expose
    private int idActivePlayer;
    @Expose
    private boolean lastTurn;
    @Expose
    private boolean stopMatch;

    public Model(int numPlayers){
        this.numPlayers = numPlayers;
        this.board = new Board(this.numPlayers);
        this.personalCards = new Card[numPlayers];
        this.GeneratePersonalCards();
        this.players = new Player[numPlayers];
        this.commonPoints1 = 8;
        this.commonPoints2 = 8;
        this.idFirstPlayer = -1;
        this.idActivePlayer = 0;
        this.lastTurn = false;
        this.stopMatch = false;
    }

    /**
     * Method that checks if a tile has a free side
     * @param x x coordinate of the tile
     * @param y y coordinate of the tile
     * @return true if the tile has a free side, false otherwise
     */
    public boolean tileFreeSide(int x, int y){
        return this.board.tileFreeSide(x, y);
    }

    /**
    * it returns the username of the player that will play the turn
    * */
    public String getActivePlayerName(){
        return new String(this.activePlayer.getUsername());
    }

    /**
     * it returns the username of the player with that id
     * @param id the id of the player
     * @return the username of the player
     * */
    public String getUsernamePlayer(int id){
        return new String(this.players[id].getUsername());
    }

    /**
    * it implements the player class setting the username of the player; it also set the PersonalCard of the player
    * 
    */
    public void setUsernamePlayer(String username){
        this.idFirstPlayer++;
        this.players[this.idFirstPlayer] = new Player(username);
        this.players[this.idFirstPlayer].setPersonalCard(personalCards[this.idFirstPlayer]);
    }

    public int getNumPlayers(){
        return this.numPlayers;
    }

    public String[] getCommonCardsDesigns(){
        return board.getCommonCardDesigns();
    }

    /**
     * This method generates random Personal Cards which are contained in the class PersonalClass, (all different from one another).
     */
    private void GeneratePersonalCards(){
        int[] idPersonalCards = new int[numPlayers];
        Random rand = new Random();
        PersonalCards playerCard = new PersonalCards();
        this.personalCards = new Card[numPlayers];

        idPersonalCards[0] = rand.nextInt(12);
        idPersonalCards[1] = idPersonalCards[0];
        while (idPersonalCards[1] == idPersonalCards[0])
            idPersonalCards[1] = rand.nextInt(12);
        this.personalCards[0] = playerCard.getCard(idPersonalCards[0]);
        this.personalCards[0].setId(idPersonalCards[0]);
        this.personalCards[1] = playerCard.getCard(idPersonalCards[1]);
        this.personalCards[1].setId(idPersonalCards[1]);

        if(this.numPlayers >= 3){
            idPersonalCards[2] = idPersonalCards[0];
            while (idPersonalCards[2]==idPersonalCards[0] || idPersonalCards[2]==idPersonalCards[1])
                idPersonalCards[2] = rand.nextInt(12);
            this.personalCards[2] = playerCard.getCard(idPersonalCards[2]);
            this.personalCards[2].setId(idPersonalCards[2]);
        }

        if(this.numPlayers == 4){
            idPersonalCards[3] = idPersonalCards[0];
            while (idPersonalCards[3]==idPersonalCards[0] || idPersonalCards[3]==idPersonalCards[1] || idPersonalCards[3]==idPersonalCards[2])
                idPersonalCards[3] = rand.nextInt(12);
            this.personalCards[3] = playerCard.getCard(idPersonalCards[3]);
            this.personalCards[3].setId(idPersonalCards[3]);
        }
    }

    /**
    * it sets casually the first player of the match
    * the 'i' value is between 0 inclusive and numPlayers exclusive
    * */
    public void setFirstPlayer(){
        Random xyz = new Random();
        int i;
        i = xyz.nextInt(numPlayers);
        this.idFirstPlayer = i;
        this.idActivePlayer = i;
        this.activePlayer = players[i];
    }

    /**
    * Method that checks if the player has enough space in his bookshelf to insert the tiles
    * @param x is the number of tiles you want to insert in the bookshelf
    * @return true if the maximum number of tiles that can be inserted is >= than x
    */
    public boolean enoughSpaceBookshelf(int x){
        return x <= activePlayer.maxTilesPick();
    }

    /**
     * Method that checks if the player has enough space in a chosen column to insert the tiles
    * @param y column selected
    * @param num number of tiles to insert
    * @return true if the selected column has enough space to insert the 'num' tiles
    * 
    * */
    public boolean enoughSpaceColumn(int y, int num){
        return !this.activePlayer.isColumnFull(y,num);
    }

    /**
     * checks if two tiles are adjacent
     * @return true if the two tiles are adjacent; on the same 'x' coordinates or on the same 'y' coordinates
     * */
    public boolean adjacentTiles(int x1, int y1, int x2, int y2){
        boolean isAdjacent;

        if(x1 == x2){
            isAdjacent = (y1 == y2 + 1) || (y1 == y2 - 1);
        }else if(y1 == y2){
            isAdjacent = (x1 == x2 + 1) || (x1 == x2 - 1);
        }else
            isAdjacent = false;

        return isAdjacent;
    }

    /**
     * checks if the three tiles are adjacent
     * @return true if the three tiles are adjacent; on the same 'x' coordinates or on the same 'y' coordinates
     * */
    public boolean adjacentTiles(int x1, int y1, int x2, int y2, int x3, int y3){
        boolean isAdjacent;

        isAdjacent = (x1 == x2 && x2 == x3 && ((adjacentTiles(x1, y1, x2, y2) && adjacentTiles(x2, y2, x3, y3)) || (adjacentTiles(x1, y1, x3, y3) && adjacentTiles(x2, y2, x3, y3)) || (adjacentTiles(x2, y2, x1, y1) && adjacentTiles(x1, y1, x3, y3)))) || (y1 == y2 && y2 == y3 && ((adjacentTiles(x1, y1, x2, y2) && adjacentTiles(x2, y2, x3, y3)) || (adjacentTiles(x1, y1, x3, y3) && adjacentTiles(x2, y2, x3, y3)) || (adjacentTiles(x2, y2, x1, y1) && adjacentTiles(x1, y1, x3, y3))));

        return isAdjacent;
    }

    //istanzio il model, creo una board, inserisco nella shelf, getactiveboard e le confronto

    /**
     * insert a tile in the bookshelf
     * @param x coordinate of the tile in the board
     * @param y coordinate of the tile in the board
     * @param col column of the bookshelf
     * it inserts the selected tile in the lowest position of the bookshelf's column removing it from the board
     * */
    public void insert(int x, int y, int col){
        this.activePlayer.insert(col,this.board.deleteItemEnum(x,y));
    }

    /**
     * insert two tiles in the bookshelf
     * @param x1 coordinate of the first selected tile in the board
     * @param y1 coordinate of the first selected tile in the board
     * @param x2 coordinate of the second selected tile in the board
     * @param y2 coordinate of the second selected tile in the board
     * @param col column of the bookshelf
     * it inserts the selected tiles in the lowest position of the bookshelf's column removing them from the board
     * */
    public void insert(int x1, int y1, int x2, int y2, int col){
        this.activePlayer.insert(col, this.board.deleteItemEnum(x1,y1), this.board.deleteItemEnum(x2,y2));
    }

    /**
     * insert three tiles in the bookshelf
     * @param x1 coordinate of the first selected tile in the board
     * @param y1 coordinate of the first selected tile in the board
     * @param x2 coordinate of the second selected tile in the board
     * @param y2 coordinate of the second selected tile in the board
     * @param x3 coordinate of the third selected tile in the board
     * @param y3 coordinate of the third selected tile in the board
     * @param col column of the bookshelf
     * it inserts the selected tiles in the lowest position of the bookshelf's column removing them from the board
     * */
    public void insert(int x1, int y1, int x2, int y2, int x3, int y3, int col) {
        this.activePlayer.insert(col, this.board.deleteItemEnum(x1,y1), this.board.deleteItemEnum(x2,y2), this.board.deleteItemEnum(x3,y3));
    }

    /**
     * checks if the player has achieved the goal of the selected CommonCard
     * @param x it is the number of the CommonCard; it can be 0 or 1
     * @return true if the player has achieved the goal
     * */
    public boolean controlCommonCards(int x){
        boolean done = false;
        int y=0;
        if(x == 0){
            done = this.activePlayer.getCommonDone1();
            y = this.commonPoints1;
        } else if (x == 1) {
            done = this.activePlayer.getCommonDone2();
            y = this.commonPoints2;
        }
        if(!done && board.getCommonCards()[x].checkBookshelf(this.activePlayer.getMatrixBookshelf())){
            this.activePlayer.updateCommonPoints(y,x);
            updateCommonPoints(x);
            return true;
        }
        return false;
    }

    /**
     * gets the available points of the CommonCards
     * @param card it is the number of the CommonCard; it can be 0 or 1
     * @return the available points of the selected CommonCard
     * */
    public int getCommonCardsPoints(int card){
        if(card == 0)
            return this.commonPoints1;
        else if(card == 1)
            return this.commonPoints2;
        return 0;
    }

    /**
     * updates the available points of the CommonCards
     * @param numCard it is the number of the CommonCard; it can be 0 or 1
     * it updates the available points of the CommonCards
     * */
    private void updateCommonPoints(int numCard){
        switch (this.numPlayers){
            case 4:
                if(numCard == 0){
                    if(this.commonPoints1 > 0)
                        this.commonPoints1 -= 2;
                }else if(numCard == 1){
                    if(this.commonPoints2 > 0)
                        this.commonPoints2 -= 2;
                }
                break;
            case 3:
                if(numCard == 0){
                    if(this.commonPoints1 == 4)
                        this.commonPoints1 -= 4;
                    else if(this.commonPoints1 > 0)
                        this.commonPoints1 -= 2;
                }else if(numCard == 1){
                    if(this.commonPoints2 == 4)
                        this.commonPoints2 -= 4;
                    else if(this.commonPoints2 > 0)
                        this.commonPoints2 -= 2;
                }
                break;
            case 2:
                if(numCard == 0){
                    if(this.commonPoints1 > 0)
                        this.commonPoints1 -= 4;
                }else if(numCard == 1){
                    if(this.commonPoints2 > 0)
                        this.commonPoints2 -= 4;
                }
                break;
        }

    }

    /**
     * Method that returns a matrix of ItemEnum representing the board
     * @return a matrix of ItemEnum representing the board
     * */
    public ItemEnum[][] getBoardMatrix(){
        return this.board.getMatrix();
    }

    /**
     * Method that returns 2 Common Cards
     * @return 2 Common Cards
     */
    public CommonCardStrategy[] getCommonCards(){
        return board.getCommonCards();
    }

    /**
     * @return the active player's bookshelf
     */
    public ItemEnum[][] getActivePlayerBookshelf(){return activePlayer.getMatrixBookshelf();}

    /**
     * get the player's bookshelf
     * @param username the name of the player concerned
     * @return the player's bookshelf
     */
    public ItemEnum[][] getPlayerBookshelf(String username){
        int i;
        int x=-1;
        for(i=0; i<players.length; i++){
            if(players[i].getUsername().equals(username))
                x = i;
        }
        if(x<0){
            return null;
        }
        return players[x].getMatrixBookshelf();
    }

    /**
     * get the player's PersonalCard
     * @param username the name of the player concerned
     * @return the player's PersonalCard
     */
    public Card getPlayerPersonalCard(String username){
        int i;
        int x=-1;
        for(i=0; i<players.length; i++){
            if(players[i].getUsername().equals(username))
                x = i;
        }
        if(x<0){
            return null;
        }
        return players[x].getPersonalCard();
    }

    /**
     * it controls if the activePlayer has filled his bookshelf, changes the activePlayer and refills the board if it is
     * necessary.
     * @return true if match is finished
     * */
    public boolean finishTurn(){
        boolean finish;
        int x;

        if(this.stopMatch){
            return true;
        }

        finish = this.activePlayer.checkIfFull();
        if(finish)
            this.lastTurn = true;

        if(this.lastTurn){
            finish = true;
            x = this.idActivePlayer+1;
            x %= this.numPlayers;
            if(x != this.idFirstPlayer)
                finish = false;
            while(players[x].isDisconnected()){
                x++;
                x %= this.numPlayers;
            }
            if(x == this.idFirstPlayer)
                finish = true;
            this.idActivePlayer = x;
            this.activePlayer = this.players[this.idActivePlayer];
        }else{
            this.changeActivePlayer();
        }

        if(board.isRefillable())
            board.refill();

        return finish;
    }

    /**
     * it upgrades the player who will play the turn
     * */
    private void changeActivePlayer(){
        this.idActivePlayer++;
        this.idActivePlayer %= this.numPlayers;
        while(this.players[this.idActivePlayer].isDisconnected()){
            this.idActivePlayer++;
            this.idActivePlayer %= this.numPlayers;
        }
        this.activePlayer = this.players[this.idActivePlayer];
    }

    /**
     * it calculates the winner player and sets him as the activePlayer
     * @return the points of the winner player
     * */
    public int[] theWinnerIs(){
        int i, x, max, id;
        int[] finalPoints = new int[this.numPlayers];

        for(i=0; i<this.numPlayers; i++)
            finalPoints[i] = 0;

        max = -1;
        id = -1;
        if(!stopMatch){
            for(i=0; i<this.numPlayers; i++){
                //if the player is disconnected, we won't calculate his points;
                if(!this.players[i].isDisconnected()){
                    x = this.players[i].calculatePoints();
                    finalPoints[i] = x;
                    if(x > max){
                        max = x;
                        id = i;
                    }else if(x == max){
                        if(distancePlayer(this.idFirstPlayer,i) > distancePlayer(this.idFirstPlayer,id)){
                            id = i;
                        }
                    }
                }
            }
            this.setActivePlayer(id);
        }else{
            //we enter here if there's only one connected player
            i = theOnlyPlayerConnected();
            this.setActivePlayer(i);
            int score = this.players[i].calculatePoints();
            finalPoints[this.idActivePlayer] = score;
        }
        return finalPoints;
    }

    /**
     * this method will be called only if connectedPlayers < MIN_PLAYERS and gives the id of the only connected player
     * @return the id of the only connected player
     * */
    private int theOnlyPlayerConnected(){
        for(int i=0; i<this.numPlayers; i++)
            if(!this.players[i].isDisconnected())
                return i;
        return -3;
    }

    /**
     * Method hat calculates the distance between 2 players
     * @param x the position of the first player
     * @param y the position of the interested player
     * @return the distance (mod numPlayers) between the 2 players
     * */
    private int distancePlayer(int x, int y){
        int z;

        z = y - x;
        while(z < 0){
            z = z + this.numPlayers;
        }
        return z;
    }

    /**
     * sets the selected player as activePlayer
     * @param id index of the selected player
     */
    public void setActivePlayer(int id){
        this.activePlayer = this.players[id];
        this.idActivePlayer = id;
    }

    /**
     * Method used to saave the model, it converts the model in a json string
     * @return a json string containing all information of the model
     */
    public String saveModel(){
        return Converter.convertModelToJSON(this);
    }

    /**
     * sets the connection's boolean of the player class
     * @param username the name of the player connected

     * */
    public void setConnected(String username){
        int x=-1;
        for(int i=0; i<players.length; i++){
            if(players[i].getUsername().equals(username)){
                x = i;
                break;
            }
        }
        if(x>-1)
            players[x].setConnected();
    }

    /**
     * Method that sets a player as disconnected
     * */
    public void setDisconnected(String username) {
        int x=-1;
        for(int i=0; i<players.length; i++){
            if(players[i].getUsername().equals(username)){
                x = i;
                break;
            }
        }
        if(x>-1)
            players[x].setDisconnected();
    }

    /**
     * Method that checks if a player is disconnected
     * @return true if the player is disconnected;
     * */
    public boolean isDisconnected(String username) {
        int x=-1;
        for(int i=0; i<players.length; i++){
            if(players[i].getUsername().equals(username)){
                x = i;
                break;
            }
        }
        if(x>-1)
            return players[x].isDisconnected();
        return true;
    }

    /**
     * Method that sets stopMatch to true if there are less than MIN_PLAYERS connected
     * */
    public void checkDisconnections() {
        int counter = 0;
        for(int i=0; i<this.numPlayers; i++){
            if(!isDisconnected(this.players[i].getUsername())){
                counter++;
            }
        }
        if(counter < Constants.MIN_PLAYERS){
            this.stopMatch = true;
        }
    }

    /**
     * Method that checks if there's only 1 player connected
     * @return true if there's only 1 player connected
     * */
    public boolean getStopMatch(){return this.stopMatch;}

    public Board getBoard() {
        return board;
    }

    /**
     * This method returns the players of the game
     * @return an array of players {@link Player}
     */
    public Player[] getPlayers() {
        return this.players;
    }

    public int getIdActivePlayer(){
        return this.idActivePlayer;
    }

    public void setStopMatch() {
        this.stopMatch = false;
    }

    public int getIdFirstPlayer() {
        return this.idFirstPlayer;
    }

    /*
* 0 - 1 - 2 - 3
* start 1;
* '2' e '0' same points;
* dist(1,2) = 2 - 1 = 1;
* dist(1,0) = 0 - 1 = -1(mod 4) = -1 + 4 = 3;
* dist(1,3) = 3 - 1 = 2;
*
*/
}