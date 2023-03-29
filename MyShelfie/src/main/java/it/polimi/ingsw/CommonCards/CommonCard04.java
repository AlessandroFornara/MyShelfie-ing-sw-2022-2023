package it.polimi.ingsw.CommonCards;

import it.polimi.ingsw.CharMatrix;
import it.polimi.ingsw.ItemEnum;

/**
 * Contains the algorithms for the fourth common card:
 * Four lines each formed by 5 tiles of maximum three different types. One line can show the same or a different combination of another line.
 * @author Alessandro Fornara
 */
public class CommonCard04 implements CommonCardStrategy {
    private final String constant4=
            "Card number 4        Description:\n"+
            "|■| |■| |■| |■| |■|  Four lines each formed by 5 tiles of\n"+
            "     MAX 3 ≠         maximum three different types.\n"+
            "       x4            Lines can be different from one another.\n";
    @Override
    public boolean checkBookshelf(ItemEnum[][] b) {
        ItemEnum firstColor;
        ItemEnum secondColor=ItemEnum.WHITE;
        ItemEnum thirdColor=ItemEnum.WHITE;
        int counter=0;
        final int numberOfRows=b.length;
        final int numberOfColumns=b[0].length;
        int j;

        for (int i = numberOfRows-1; i >=0; i--) {
            j = 0;

            //colore1
            if (!b[i][j].equals(ItemEnum.BLANK)) {
                firstColor = b[i][j];
                do {j++;}
                while (j < numberOfColumns && b[i][j].equals(firstColor));

                //colore 2
                if(j!=numberOfColumns) {
                    secondColor = b[i][j];
                    do {j++;}
                    while (j < numberOfColumns
                            && (b[i][j].equals(firstColor) || b[i][j].equals(secondColor)));

                    //colore 3
                    if(j!=numberOfColumns) {
                        thirdColor = b[i][j];
                        do {j++;}
                        while (j < numberOfColumns
                                && (b[i][j].equals(firstColor) || b[i][j].equals(secondColor) || b[i][j].equals(thirdColor)));
                    }
                }
                if (j == numberOfColumns
                        && !firstColor.equals(ItemEnum.BLANK)
                        && !secondColor.equals(ItemEnum.BLANK)
                        && !thirdColor.equals(ItemEnum.BLANK)) counter++;
            }
            if (counter == 4)
                return true;
        }
        return false;
    }
    @Override
    public CharMatrix printCommonCardMatrix(){

        return  new CharMatrix()
                .addNewLine("Card number 4         Description:")
                .addNewLine("|■| |■| |■| |■| |■|   Four lines each formed by 5 tiles of")
                .addNewLine("   MAX 3 ≠            maximum three different types.")
                .addNewLine("     x4               Lines can be different from one another.");               .addNewLine("                different between different groups.");
    }
    @Override
    public void printCommonCard() {
        System.out.println(constant4);
    }
}

