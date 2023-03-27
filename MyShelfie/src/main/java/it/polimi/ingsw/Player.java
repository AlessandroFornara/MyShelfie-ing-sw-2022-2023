package it.polimi.ingsw;

import java.util.Scanner;

public class Player {
    private int id, myPoints;
    public String username;
    Bookshelf myShelf;
    Card myGoals;

    Player(int id, String username){
        this.id = id;
        this.username = username;
        myPoints = 0;
        myShelf = new Bookshelf();
    }

    public void setPersonalCard(Card goals){
        myGoals = new Card();
        myGoals = goals;
    }

    //"calculatePoints" counts the points excluding the "common cards"
    public int calculatePoints(){
        myPoints = myShelf.pointPersonalCard(myGoals);
        myPoints += myShelf.adjacentTilesPoints();
        return myPoints;
    }

    public void fish(ItemEnum[][] tileBoard, int x1, int y1){
        boolean done, readAgain;
        Scanner readCoordinates = new Scanner(System.in);
        done = false;
        readAgain = false;
        int y;

        while(!done){
            if(tileBoard[x1][y1] == ItemEnum.BLANK){
                readAgain = true;
                System.out.println("you have selected an empty position");
            }else if(tileBoard[x1-1][y1] == ItemEnum.BLANK || tileBoard[x1+1][y1] == ItemEnum.BLANK || tileBoard[x1][y1-1] == ItemEnum.BLANK || tileBoard[x1][y1+1] == ItemEnum.BLANK){
                done = true;
                readAgain = false;
            }else{
                System.out.println("The selected tile hasn't a free side");
                readAgain = true;
            }
            if(readAgain){
                System.out.println("Select another tile: x - y");
                x1 = readCoordinates.nextInt();
                y1 = readCoordinates.nextInt();
            }
        }
        //TODO: remove tile(x1, y1);
        System.out.println("Decide the column of your shelf");
        y = readCoordinates.nextInt();
        myShelf.insert(y,tileBoard[x1][y1]);
    }

    public void fish(ItemEnum[][] tileBoard, int x1, int y1, int x2, int y2){
        int j;
        boolean done, readAgain;
        Scanner readCoordinates = new Scanner(System.in);
        done = false;
        readAgain = false;

        while(!done){
            if(tileBoard[x1][y1] == ItemEnum.BLANK || tileBoard[x2][y2] == ItemEnum.BLANK){
                System.out.println("you have selected an empty position");
                readAgain = true;
            }else{
                //the selected tiles aren't adjacent
                if(!((x1==x2 && (y1==y2+1 || y1==y2-1)) || (y1==y2 && (x1==x2+1 || x1==x2-1)))){
                    readAgain = true;
                    System.out.println("The tiles aren't adjacent");
                }else{
                    readAgain = false;
                }
            }
            if(!readAgain){
                if((tileBoard[x1+1][y1] == ItemEnum.BLANK || tileBoard[x1-1][y1] == ItemEnum.BLANK || tileBoard[x1][y1-1] == ItemEnum.BLANK || tileBoard[x1][y1+1] == ItemEnum.BLANK) && (tileBoard[x2+1][y2] == ItemEnum.BLANK || tileBoard[x2-1][y2] == ItemEnum.BLANK || tileBoard[x2][y2-1] == ItemEnum.BLANK || tileBoard[x2][y2+1] == ItemEnum.BLANK)){
                    done = true;
                }else
                    readAgain = true;
            }
            if(readAgain){
                System.out.println("Select another 2 tiles: x1 y1; x2 y2");
                x1 = readCoordinates.nextInt();
                y1 = readCoordinates.nextInt();
                x2 = readCoordinates.nextInt();
                y2 = readCoordinates.nextInt();
            }
        }
        //TODO: remove tile(x1, y1), tile(x2, y2);
        System.out.println("Decide the column of your shelf");
        j = readCoordinates.nextInt();
        myShelf.insert(j,tileBoard[x1][y1], tileBoard[x2][y2]);
    }

    public void fish(ItemEnum[][] tileBoard, int x1, int y1, int x2, int y2, int x3, int y3){
        int j;
        boolean done, readAgain;
        Scanner readCoordinates = new Scanner(System.in);
        done = false;
        readAgain = false;

        while(!done){
            if(tileBoard[x1][y1] == ItemEnum.BLANK || tileBoard[x2][y2] == ItemEnum.BLANK || tileBoard[x3][y3] == ItemEnum.BLANK){
                System.out.println("you have selected an empty position");
                readAgain = true;
            }else{
                //the selected tiles aren't adjacent
                //TODO: control the warning
                if(!((x1==x2 && x2==x3 && ((y1==y2+1 && y2==y3+1) || (y1==y3+1 && y3==y2+1) || (y2==y1+1 && y1==y3+1) || (y2==y3+1 && y3==y1+1) || (y3==y2+1 && y2==y1+1) || (y3==y1+1 && y1==y2+1))) || ((y1==y2 && y2==y3 && ((x1==x2+1 && x2==x3+1) || (x1==x3+1 && x3==x2+1) || (x2==x1+1 && x1==x3+1) || (x2==x3+1 && x3==x1+1) || (x3==x2+1 && x2==x1+1) || (x3==x1+1 && x1==x2+1)))))){
                    readAgain = true;
                    System.out.println("The tiles aren't adjacent");
                }else{
                    readAgain = false;
                }
            }
            if(!readAgain){
                if((tileBoard[x1+1][y1] == ItemEnum.BLANK || tileBoard[x1-1][y1] == ItemEnum.BLANK || tileBoard[x1][y1-1] == ItemEnum.BLANK || tileBoard[x1][y1+1] == ItemEnum.BLANK) && (tileBoard[x2+1][y2] == ItemEnum.BLANK || tileBoard[x2-1][y2] == ItemEnum.BLANK || tileBoard[x2][y2-1] == ItemEnum.BLANK || tileBoard[x2][y2+1] == ItemEnum.BLANK) && (tileBoard[x3+1][y3] == ItemEnum.BLANK || tileBoard[x3-1][y3] == ItemEnum.BLANK || tileBoard[x3][y3-1] == ItemEnum.BLANK || tileBoard[x3][y3+1] == ItemEnum.BLANK)){
                    done = true;
                }else
                    readAgain = true;
            }
            if(readAgain){
                System.out.println("Select another 3 tiles: x1 y1; x2 y2; x3 y3");
                x1 = readCoordinates.nextInt();
                y1 = readCoordinates.nextInt();
                x2 = readCoordinates.nextInt();
                y2 = readCoordinates.nextInt();
                x3 = readCoordinates.nextInt();
                y3 = readCoordinates.nextInt();
            }
        }
        //TODO: remove tile(x1, y1), tile(x2, y2), tile(x3,y3);
        System.out.println("Decide the column of your shelf");
        j = readCoordinates.nextInt();
        myShelf.insert(j,tileBoard[x1][y1], tileBoard[x2][y2], tileBoard[x3][y3]);
    }
}