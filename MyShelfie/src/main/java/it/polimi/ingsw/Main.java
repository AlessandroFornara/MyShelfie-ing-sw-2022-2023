package it.polimi.ingsw;

import java.util.Scanner;

public class Main {
    public static void main( String[] args ){
        Bookshelf testBookshelf = new Bookshelf();
        PersonalCard testCards = new PersonalCard();
        int i, j, personalPoints;
        ItemEnum testTiles;
        Scanner readInput = new Scanner(System.in);

        System.out.println(Constant.MY_SHELFIE_TITLE);

        Board b = new Board(2);
        ItemEnum.generateCharMatrix(b.getMatrix(), Board.BOARD_SIZE, Board.BOARD_SIZE)
                .addHeaders(Board.BOARD_SIZE).printMatrix();


        System.out.println("\n\n");
        Board b2 = new Board(2);
        ItemEnum.generateCharMatrix(b2.getMatrix(), Board.BOARD_SIZE, Board.BOARD_SIZE)
                .addHeaders(Board.BOARD_SIZE).printMatrix();


        System.out.println("\n\n");
        ItemEnum.generateCharMatrix(b.getMatrix(), Board.BOARD_SIZE, Board.BOARD_SIZE)
                .addHeaders(Board.BOARD_SIZE).appendToAllRows("   ").addOnRight(ItemEnum.generateCharMatrix(b2.getMatrix(), Board.BOARD_SIZE, Board.BOARD_SIZE)
                        .addHeaders(Board.BOARD_SIZE)).printMatrix();


        System.out.println("\nALLINEATO:\n");


        System.out.println("\n\n");
        ItemEnum.generateCharMatrix(b.getMatrix(), Board.BOARD_SIZE, Board.BOARD_SIZE)
                .addHeaders(Board.BOARD_SIZE).appendToAllRows("   ").alignColumn().addOnRight(ItemEnum.generateCharMatrix(b2.getMatrix(), Board.BOARD_SIZE, Board.BOARD_SIZE)
                        .addHeaders(Board.BOARD_SIZE)).printMatrix();

        for(i=0; i<30; i++){
            j = readInput.nextInt();
            testTiles = ItemEnum.valueOf(readInput.next());
            testBookshelf.insert(j, testTiles);
        }
        System.out.println("Print Bookshelf");
        ItemEnum.generateCharMatrix(testBookshelf.getMatrix(), 6,5 ).printMatrix();
        personalPoints = testBookshelf.pointPersonalCard(testCards.getCard(0));
        System.out.println("Points done: " + personalPoints);

    }

}

