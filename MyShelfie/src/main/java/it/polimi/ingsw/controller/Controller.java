package it.polimi.ingsw.controller;

import it.polimi.ingsw.ItemEnum;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CommonCards.CommonCardStrategy;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.view.CLI.CliView;

public class Controller {
    private CliView view;
    private Model model;

    public Controller(int x){
        model = new Model(x);
    }

    public Controller(int x, CliView view){
        this.model = new Model(x);
        this.view = view;
    }

    public int getNumPlayers(){
        return this.model.getNumPlayers();
    }

    public boolean duplicatedUsername(String x){
        return this.model.duplicatedUsername(x);
    }

    public void setUsernamePlayer(String name){
        this.model.setUsernamePlayer(name);
    }

    public void setFirstPlayer(){
        model.setFirstPlayer();
    }

    public boolean enoughSpaceBookshelf(int x){
        boolean done;

        done = model.enoughSpaceBookshelf(x);
        if(!done)
            view.notEnoughSpaceBookshelfPrint();

        return done;
    }

    private boolean isFeasiblePickMove(int x, int y){
        boolean done;
        done = model.isFeasiblePickMove(x,y);
        if(!done)
            view.noFreeSidesPrint(x,y);
        return done;
    }

    private boolean isFeasiblePickMove(int x1, int y1, int x2, int y2){
        boolean done;
        done = model.isFeasiblePickMove(x1,y1);
        if(!done)
            return false;
        done = model.isFeasiblePickMove(x2,y2);
        if(!done)
            return false;
        done = model.adjacentTiles(x1,y1,x2,y2);
            if(!done){
                view.notAdjacentTilesPrint();
            }
        return done;
    }

    private boolean isFeasiblePickMove(int x1, int y1, int x2, int y2, int x3, int y3){
        boolean done;
        done = model.isFeasiblePickMove(x1,y1);
        if(!done)
            return false;
        done = model.isFeasiblePickMove(x2,y2);
        if(!done)
            return false;
        done = model.isFeasiblePickMove(x3,y3);
        if(!done)
            return false;
        done = model.adjacentTiles(x1,y1,x2,y2,x3,y3);
        if(!done){
            view.notAdjacentTilesPrint();
        }
        return done;
    }

    public boolean pickCard(int x, int y, int col){
        boolean done;

        done = enoughSpaceBookshelf(1);
        if(!done)
            return false;

        done = isFeasiblePickMove(x,y);
        if(!done)
            return false;

        if(!model.enoughSpaceColumn(col, 1)){
            view.notEnoughSpaceBookshelfColPrint(col);
            return false;
        }
        model.insert(x,y,col);
        return true;
    }

    public boolean pickCard(int x1, int y1, int x2, int y2, int col){
        boolean done;

        done = enoughSpaceBookshelf(2);
        if(!done)
            return false;

        done = isFeasiblePickMove(x1,y1,x2,y2);
        if(!done)
            return false;

        if(!model.enoughSpaceColumn(col, 2)){
            view.notEnoughSpaceBookshelfColPrint(col);
            return false;
        }
        model.insert(x1,y1,x2,y2,col);
        return true;
    }

    public boolean pickCard(int x1, int y1, int x2, int y2, int x3, int y3, int col){
        boolean done;

        done = enoughSpaceBookshelf(3);
        if(!done)
            return false;

        done = isFeasiblePickMove(x1,y1,x2,y2,x3,y3);
        if(!done)
            return false;

        if(!model.enoughSpaceColumn(col, 3)){
            view.notEnoughSpaceBookshelfColPrint(col);
            return false;
        }
        model.insert(x1,y1,x2,y2,x3,y3,col);
        return true;
    }

    //return true if the match is finished
    public boolean finishTurn(){
        int points;
        boolean card;
        card = model.controlCommonCards(0);
        if(card){
            points = model.getCommonCardsPoints(0);
            view.commonPoints(model.getActivePlayerName(), points, 1);
        }
        card = model.controlCommonCards(1);
        if(card){
            points = model.getCommonCardsPoints(1);
            view.commonPoints(model.getActivePlayerName(), points, 2);
        }
        card = model.finishTurn();
        return card;
    }

    //TODO: finish this method
    public void declareWinner(){
        int x;
        x = model.theWinnerIs();
        view.winnerPrint(model.getActivePlayerName(), x);
    }

    public ItemEnum[][] getBoard(){
        return model.getBoardMatrix();
    }

    /**
     * @author Alessandro Fornara
     * @return 2 Common Cards
     */
    public CommonCardStrategy[] getCommonCards(){
        return model.getCommonCards();
    }

    /**
     * @author Alessandro Fornara
     * @return The active player's personal card
     */
    public Card getActivePlayerPersonalCard(){return model.getPersonalCard();}

    /**
     * @author Alessandro Fornara
     * @return The active player's username
     */
    public String getActivePlayerUsername(){return model.getActivePlayerName();}

    /**
     * @author Alessandro Fornara
     * @return the active player's bookshelf
     */
    public ItemEnum[][] getActivePlayershelf(){return model.getShelf();}

//    public ItemEnum[][] getCommonCards(int x){
//        return model.getCommonCards();
//    }
}