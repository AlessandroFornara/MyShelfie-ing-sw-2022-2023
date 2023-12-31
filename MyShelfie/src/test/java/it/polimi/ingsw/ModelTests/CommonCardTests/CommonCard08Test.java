package it.polimi.ingsw.ModelTests.CommonCardTests;

import it.polimi.ingsw.ItemEnum;
import it.polimi.ingsw.model.CommonCards.CommonCard08;
import it.polimi.ingsw.model.CommonCards.CommonCardStrategy;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class is a test for the eighth common card
 * @author Alessandro Fornara
 * @author Samuele Pietro Galli
 */
public class CommonCard08Test {
    private CommonCardStrategy c;
    private ItemEnum[][] matrix;
    public CommonCard08Test() {
        c = new CommonCard08();
        matrix = new ItemEnum[6][5];
    }
        /**
     * This method inserts in a column of an ItemEnum matrix some colors
     * @param j Column
     * @param x1 First color
     * @param x2 Second color
     * @param x3 Third color
     * @param x4 Fourth color
     * @param x5 Fifth color
     * @param x6 Sixth color
     */
     private void insertColumn(int j, ItemEnum x1, ItemEnum x2, ItemEnum x3, ItemEnum x4, ItemEnum x5, ItemEnum x6){
        matrix[0][j]=x1;
        matrix[1][j]=x2;
        matrix[2][j]=x3;
        matrix[3][j]=x4;
        matrix[4][j]=x5;
        matrix[5][j]=x6;
    }

    @Test
    public void Test(){
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        Assert.assertFalse(c.checkBookshelf(matrix));

        //CORRECT CASE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.GREEN1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.AZURE1, ItemEnum.YELLOW1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.WHITE1, ItemEnum.AZURE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));


        //CORRECT CASE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.GREEN1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.GREEN1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.WHITE1, ItemEnum.AZURE1, ItemEnum.YELLOW1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.WHITE1, ItemEnum.AZURE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.AZURE1, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //WRONG CASE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.GREEN1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.GREEN1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.WHITE1, ItemEnum.AZURE1, ItemEnum.YELLOW1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.BLUE1, ItemEnum.AZURE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.AZURE1, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertFalse(c.checkBookshelf(matrix));

        //CORRECT CASE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.GREEN1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.GREEN1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.AZURE1, ItemEnum.BLANK, ItemEnum.WHITE1, ItemEnum.AZURE1, ItemEnum.YELLOW1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.WHITE1, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.WHITE1, ItemEnum.AZURE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.BLANK, ItemEnum.AZURE1, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //CORRECT CASE
        insertColumn(0, ItemEnum.PURPLE1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.GREEN1);
        insertColumn(1, ItemEnum.GREEN1, ItemEnum.BLUE1, ItemEnum.WHITE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.GREEN1);
        insertColumn(2, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.WHITE1, ItemEnum.AZURE1, ItemEnum.YELLOW1);
        insertColumn(3, ItemEnum.YELLOW1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.YELLOW1, ItemEnum.YELLOW1, ItemEnum.AZURE1);
        insertColumn(4, ItemEnum.WHITE1, ItemEnum.YELLOW1, ItemEnum.BLUE1, ItemEnum.AZURE1, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //CORRECT CASE
        insertColumn(0, ItemEnum.PURPLE1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.GREEN1);
        insertColumn(1, ItemEnum.PURPLE1, ItemEnum.BLUE1, ItemEnum.WHITE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.GREEN1);
        insertColumn(2, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.WHITE1, ItemEnum.AZURE1, ItemEnum.YELLOW1);
        insertColumn(3, ItemEnum.YELLOW1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.YELLOW1, ItemEnum.YELLOW1, ItemEnum.AZURE1);
        insertColumn(4, ItemEnum.WHITE1, ItemEnum.YELLOW1, ItemEnum.BLUE1, ItemEnum.AZURE1, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertFalse(c.checkBookshelf(matrix));

        Assert.assertTrue(c.getNumber() == 8);

        Assert.assertTrue(c.getCommonCardDesign().equals("Card number 8        Description:\n"+
                "|≠| |≠| |≠| |≠| |≠|  Two lines each formed by 5 different\n"+
                "                     types of tiles. One line can show \n"+
                "       x2            the same or a different combination\n"+
                "                     of another line.\n"));

    }
}
