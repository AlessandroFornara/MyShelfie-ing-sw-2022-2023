package it.polimi.ingsw.ModelTests.CommonCardTests;

import it.polimi.ingsw.ItemEnum;
import it.polimi.ingsw.model.CommonCards.CommonCard04;
import it.polimi.ingsw.model.CommonCards.CommonCardStrategy;
import org.junit.Assert;
import org.junit.Test;


/**
 * This class is a test for the fourth common card
 * @author Samuele Pietro Galli
 */
public class CommonCard04Test {
    private CommonCardStrategy c;
    private ItemEnum[][] matrix;
    public CommonCard04Test() {
        c = new CommonCard04();
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
         //all blank case
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        Assert.assertFalse(c.checkBookshelf(matrix));

        //1 ITEM CASE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1);
        Assert.assertFalse(c.checkBookshelf(matrix));

        //CORRECT CASE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.PURPLE1, ItemEnum.YELLOW1, ItemEnum.GREEN1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.GREEN1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.WHITE1, ItemEnum.GREEN1, ItemEnum.BLUE1, ItemEnum.WHITE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.WHITE1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.GREEN1, ItemEnum.BLUE1, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //WRONG CASE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.PURPLE1, ItemEnum.YELLOW1, ItemEnum.GREEN1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.GREEN1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.WHITE1, ItemEnum.GREEN1, ItemEnum.WHITE1, ItemEnum.WHITE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.WHITE1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.GREEN1, ItemEnum.GREEN1, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertFalse(c.checkBookshelf(matrix));

        //WRONG CASE (3 LINES)
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.YELLOW1, ItemEnum.GREEN1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.GREEN1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.GREEN1, ItemEnum.BLUE1, ItemEnum.WHITE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.WHITE1, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.GREEN1, ItemEnum.BLUE1, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertFalse(c.checkBookshelf(matrix));

        //WRONG CASE (2 LINES)
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.GREEN1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.WHITE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.PURPLE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.YELLOW1, ItemEnum.BLUE1);
        Assert.assertFalse(c.checkBookshelf(matrix));

        Assert.assertTrue(c.getNumber() == 4);

        Assert.assertTrue(c.getCommonCardDesign().equals("Card number 4        Description:\n"+
                "|■| |■| |■| |■| |■|  Four lines each formed by 5 tiles of\n"+
                "     MAX 3 ≠         maximum three different types.\n"+
                "       x4            Lines can be different from one another.\n"));

    }

}
