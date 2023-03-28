package it.polimi.ingsw;

import it.polimi.ingsw.CommonCards.CommonCardGenerator;
import it.polimi.ingsw.CommonCards.CommonCardStrategy;

import java.util.Random;

public class Board {
    public static final int BOARD_SIZE = 9;
    public static final int INITIAL_NUMBER_ITEMENUM = 20;
    private ItemEnum[][] matrix;
    private int numPlayers;
    private int[] numItemRemained = new int[ItemEnum.NUM_ITEMENUM];

    //TODO: modificare in un array in cui togliere i punti se si è in 3/4 giocatori
    //TODO: aggiungere funzione per togliere carta in una posizione, refill e prendere carta in una posizione

    private final static int[][] positionsAlwaysForbidden = new int[][]{{0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1},
            {3, 0}};
    private final static int numPosAlwaysForbidden = 9;
    private final static int[][] positionValid3Players = new int[][]{{0, 3}, {2, 2}};
    private final static int numPosValid3Players = 2;
    private final static int[][] positionValid4Players = new int[][]{{4, 0}, {3, 1}};
    private final static int numPosValid4Players = 2;
    private int[][] forbiddenPositions;
    public CommonCardStrategy[] CommonCards;

    public Board(int numPlayers) {
        matrix = new ItemEnum[BOARD_SIZE][BOARD_SIZE];

        for(int i = 0; i < ItemEnum.NUM_ITEMENUM; i++)
            numItemRemained[i] = INITIAL_NUMBER_ITEMENUM;
        refill();

        this.numPlayers = numPlayers;

        //Generating Common Goal Cards
        CommonCards =new CommonCardStrategy[2];
        CommonCardGenerator gen = new CommonCardGenerator();
        CommonCards = gen.GenerateCommonCards();
    }

    public ItemEnum[][] getMatrix() {
        ItemEnum[][] copy = new ItemEnum[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                copy[i][j] = matrix[i][j];
        return copy;
    }

    private boolean isPositionValid(int r, int c) {
        for (int i = 0; i < numPosAlwaysForbidden; i++) {
            if (arePositionsEqual4Square(positionsAlwaysForbidden[i][0], positionsAlwaysForbidden[i][1], r, c))
                return false;
        }
        return true;
    }

    private boolean arePositionsEqual4Square(int r1, int c1, int r2, int c2) {
        return (r1 == r2 && c1 == c2) ||
                (r1 == BOARD_SIZE - 1 - c2 && c1 == r2) ||
                (r1 == BOARD_SIZE - 1 - r2 && c1 == BOARD_SIZE - 1 - c2) ||
                (r1 == c2 && c1 == BOARD_SIZE - 1 - r2);
    }

    public ItemEnum getItemEnum(int r, int c){
        if(matrix[r][c] == ItemEnum.BLANK)
            System.out.println("Restituita una carta Blank!");
        return matrix[r][c];
    }
    public ItemEnum deleteItemEnum(int r, int c){
        ItemEnum i = matrix[r][c];
        if(i == ItemEnum.BLANK)
            System.out.println("Eliminata una carta Blank!");
        else
            matrix[r][c] = ItemEnum.BLANK;
        return i;
    }


    public void refill(){
        Random rand = new Random();
        int n;
        ItemEnum item;

        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isPositionValid(i, j)) {
                    n = rand.nextInt(ItemEnum.NUM_ITEMENUM); //blank is excluded
                    while (numItemRemained[n] == 0)
                        n = rand.nextInt(ItemEnum.NUM_ITEMENUM);

                    numItemRemained[n]--;
                    item = switch (n) {
                        case 0 -> ItemEnum.GREEN;
                        case 1 -> ItemEnum.WHITE;
                        case 2 -> ItemEnum.YELLOW;
                        case 3 -> ItemEnum.BLUE;
                        case 4 -> ItemEnum.AZURE;
                        case 5 -> ItemEnum.PURPLE;
                        default -> ItemEnum.BLANK; //covers also for case 6
                    };

                    matrix[i][j] = item;
                } else {
                    matrix[i][j] = ItemEnum.BLANK;
                }
            }
        }
    }


}
