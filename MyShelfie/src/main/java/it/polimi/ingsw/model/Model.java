package it.polimi.ingsw.model;

import it.polimi.ingsw.ItemEnum;
import it.polimi.ingsw.model.CommonCards.CommonCardStrategy;

import java.util.Random;

public class Model {
    private Board board;
    private final int numPlayers;
    private Player[] players;
    private Player activePlayer;
    private Card[] personalCards;
    private int commonPoints1, commonPoints2;
    private int idFirstPlayer, idActivePlayer;
    private boolean lastTurn;

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
    }

    public boolean isFeasiblePickMove(int x, int y){
        return this.board.tileFreeSide(x, y);
    }

    private void changeActivePlayer(){
        this.idActivePlayer++;
        this.idActivePlayer %= this.numPlayers;
        this.activePlayer = this.players[this.idActivePlayer];
    }

    public String getActivePlayerName(){
        return new String(this.activePlayer.getUsername());
    }

    public void setUsernamePlayer(String username){
        this.idFirstPlayer++;
        this.players[this.idFirstPlayer] = new Player(username);
        this.players[this.idFirstPlayer].setPersonalCard(personalCards[this.idFirstPlayer]);
    }

    public int getNumPlayers(){
        return this.numPlayers;
    }


    //TODO: DOESN'T WORK (la seconda volta che viene richiamata y è uguale a 0 e quindi non entra nel ciclo e non fa il controllo)
    public boolean duplicatedUsername(String x){
        int y = idFirstPlayer;
        if(y==-1)
            return false;
        for(int i=0; i<y; i++){
            if(x.equals(players[i].getUsername()))
                return true;
        }
        return false;
    }

    /**
     * This method generates random Personal Cards which are contained in the class PersonalClass, (all different from one another).
     * @author Alessandro Fornara
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
        this.personalCards[1] = playerCard.getCard(idPersonalCards[1]);

        if(this.numPlayers >= 3){
            idPersonalCards[2] = idPersonalCards[0];
            while (idPersonalCards[2]==idPersonalCards[0] || idPersonalCards[2]==idPersonalCards[1])
                idPersonalCards[2] = rand.nextInt(12);
            this.personalCards[2] = playerCard.getCard(idPersonalCards[2]);
        }

        if(this.numPlayers == 4){
            idPersonalCards[3] = idPersonalCards[0];
            while (idPersonalCards[3]==idPersonalCards[0] || idPersonalCards[3]==idPersonalCards[1] || idPersonalCards[3]==idPersonalCards[2])
                idPersonalCards[3] = rand.nextInt(12);
            this.personalCards[3] = playerCard.getCard(idPersonalCards[3]);
        }
    }

    public void setFirstPlayer(){
        Random xyz = new Random();
        int i;
        i = xyz.nextInt(numPlayers);
        this.idFirstPlayer = i;
        this.idActivePlayer = i;
        this.activePlayer = players[i];
    }

    public boolean enoughSpaceBookshelf(int x){
        if(x>activePlayer.maxTilesPick())
            return false;
        return true;
    }

    public boolean enoughSpaceColumn(int y, int num){
        return !this.activePlayer.isColumnFull(y,num);
    }

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

    public boolean adjacentTiles(int x1, int y1, int x2, int y2, int x3, int y3){
        boolean isAdjacent;

        isAdjacent = (x1 == x2 && x2 == x3 && ((adjacentTiles(x1, y1, x2, y2) && adjacentTiles(x2, y2, x3, y3)) || (adjacentTiles(x1, y1, x3, y3) && adjacentTiles(x2, y2, x3, y3)) || (adjacentTiles(x2, y2, x1, y1) && adjacentTiles(x1, y1, x3, y3)))) || (y1 == y2 && y2 == y3 && ((adjacentTiles(x1, y1, x2, y2) && adjacentTiles(x2, y2, x3, y3)) || (adjacentTiles(x1, y1, x3, y3) && adjacentTiles(x2, y2, x3, y3)) || (adjacentTiles(x2, y2, x1, y1) && adjacentTiles(x1, y1, x3, y3))));


        return isAdjacent;
    }

    public void insert(int x, int y, int col){
        this.activePlayer.insert(col,this.board.deleteItemEnum(x,y));
    }

    public void insert(int x1, int y1, int x2, int y2, int col){
        this.activePlayer.insert(col, this.board.deleteItemEnum(x1,y1), this.board.deleteItemEnum(x2,y2));
    }

    public void insert(int x1, int y1, int x2, int y2, int x3, int y3, int col) {
        this.activePlayer.insert(col, this.board.deleteItemEnum(x1,y1), this.board.deleteItemEnum(x2,y2), this.board.deleteItemEnum(x3,y3));
    }

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

    public int getCommonCardsPoints(int card){
        if(card == 0)
            return this.commonPoints1;
        else if(card == 1)
            return this.commonPoints2;
        return 0;
    }

    private void updateCommonPoints(int numCard){
        if(numCard == 0){
            this.commonPoints1 -= 2;
        }else if(numCard == 1){
            this.commonPoints2 -= 2;
        }
    }

    public ItemEnum[][] getBoardMatrix(){
        return this.board.getMatrix();
    }

    /**
     * @author Alessandro Fornara
     * @return 2 Common Cards
     */
    public CommonCardStrategy[] getCommonCards(){
        return board.getCommonCards();
    }

    /**
     * @author Alessandro Fornara
     * @return the active player's bookshelf
     */
    public ItemEnum[][] getShelf(){return activePlayer.getMatrixBookshelf();}

    /**
     * @author Alessandro Fornara
     * @return The active player's personal card
     */
    public Card getPersonalCard(){return activePlayer.getPersonalCard();}

    //todo: test this method
    public boolean finishTurn(){
        boolean finish;
        int x;

        finish = this.activePlayer.checkIfFull();
        if(finish)
            this.lastTurn = true;

        if(this.lastTurn){
            finish = true;
            x = this.idActivePlayer+1;
            x %= this.numPlayers;
            if(x != this.idFirstPlayer)
                finish = false;
        }

        this.changeActivePlayer();
        if(board.isRefillable())
            board.refill();

        return finish;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    //TODO: test this method
    public int theWinnerIs(){
        int i, x, max, id;

        max = 0;
        id = -1;
        for(i=0; i<this.numPlayers; i++){
            x = this.players[i].calculatePoints();
            if(x > max){
                max = x;
                id = i;
            }else if(x == max){
                if(distancePlayer(this.idFirstPlayer,i) > distancePlayer(this.idFirstPlayer,id)){
                    id = i;
                    max = x;
                }
            }
        }
        this.idActivePlayer = id;
        this.setActivePlayer(id);

        return this.activePlayer.getMyPoints();
    }

    //TODO: test this method
    private int distancePlayer(int x, int y){
        int z;

        z = y - x;
        if(z < 0){
            z = z + this.numPlayers;
        }
        return z;
    }

    private void setActivePlayer(int id){
        this.activePlayer = this.players[id];
    }

/* 0 - 1 - 2 - 3
* start 1;
* '2' e '0' same points;
* dist(1,2) = 2 - 1 = 1;
* dist(1,0) = 0 - 1 = -1(mod 4) = -1 + 4 = 3;
* dist(1,3) = 3 - 1 = 2;
*
*/
/*
    public ItemEnum[][] getCommonCards() {
        return
    }*/
}