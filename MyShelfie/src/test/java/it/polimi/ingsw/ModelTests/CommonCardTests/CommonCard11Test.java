package it.polimi.ingsw.ModelTests.CommonCardTests;

import it.polimi.ingsw.ItemEnum;
import it.polimi.ingsw.model.CommonCards.CommonCard11;
import it.polimi.ingsw.model.CommonCards.CommonCardStrategy;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class is a test for the eleventh common card
 * @author Samuele Pietro Galli
 * @author Alessandro Fornara
 */
public class CommonCard11Test {
    private CommonCardStrategy c;
    private ItemEnum[][] matrix;
    public CommonCard11Test() {
        c = new CommonCard11();
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

        //TRUE CASE (10 ITEMS)
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.YELLOW1, ItemEnum.YELLOW1, ItemEnum.PURPLE1, ItemEnum.WHITE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //9 BLUE TRUE CASE
        insertColumn(0, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.YELLOW1, ItemEnum.YELLOW1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //WRONG CASE 7 ITEMS
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1);
        Assert.assertFalse(c.checkBookshelf(matrix));

        //CORRECT CASE 8 ITEMS
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //WRONG CASE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.GREEN1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1);
        Assert.assertFalse(c.checkBookshelf(matrix));

        Assert.assertTrue(c.getNumber() == 11);

        Assert.assertTrue(c.getCommonCardDesign().equals("Card number 11  Description:\n"+
                "  |=| |=|       Eight tiles of the same type. There's\n"+
                "|=| |=| |=|     no restriction about the positions\n"+
                "|=| |=| |=|     of these tiles.\n"));

    }
}
