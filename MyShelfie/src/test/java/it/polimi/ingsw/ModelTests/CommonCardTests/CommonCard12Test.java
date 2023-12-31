package it.polimi.ingsw.ModelTests.CommonCardTests;

import it.polimi.ingsw.ItemEnum;
import it.polimi.ingsw.model.CommonCards.CommonCard12;
import it.polimi.ingsw.model.CommonCards.CommonCardStrategy;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class is a test for the twelfth common card
 * @author Samuele Pietro Galli
 * @author Alessandro Fornara
 */
public class CommonCard12Test {

    private CommonCardStrategy c;
    private ItemEnum[][] matrix;
    public CommonCard12Test(){
        c=new CommonCard12();
        matrix= new ItemEnum[6][5];
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

        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        insertColumn(0, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        insertColumn(0, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        insertColumn(0, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.YELLOW1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //NOT INCREASING SCALE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.AZURE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.YELLOW1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.AZURE1, ItemEnum.WHITE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.PURPLE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //NOT DECREASING SCALE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.AZURE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.PURPLE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //NOT INCREASING SCALE
        insertColumn(0, ItemEnum.GREEN1, ItemEnum.PURPLE1, ItemEnum.AZURE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.PURPLE1, ItemEnum.YELLOW1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.YELLOW1, ItemEnum.PURPLE1, ItemEnum.AZURE1, ItemEnum.WHITE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.WHITE1, ItemEnum.PURPLE1, ItemEnum.PURPLE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.GREEN1, ItemEnum.YELLOW1, ItemEnum.PURPLE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        //NOT DECREASING SCALE
        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.AZURE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        Assert.assertTrue(c.checkBookshelf(matrix));

        insertColumn(0, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(1, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(2, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(3, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        insertColumn(4, ItemEnum.BLANK, ItemEnum.BLANK, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1, ItemEnum.BLUE1);
        Assert.assertFalse(c.checkBookshelf(matrix));

        Assert.assertTrue(c.getNumber() == 12);

        Assert.assertTrue(c.getCommonCardDesign().equals("Card number 12  Description:\n"+
                "|•|             Five columns of increasing o decreasing height.\n"+
                "|•|•|           Starting from the first column on the left or \n"+
                "|•|•|•|         on the right, each next column must be made of\n"+
                "|•|•|•|•|       exactly one more tile. Tiles can be of any type.\n"+
                "|•|•|•|•|•|\n"));

    }

}
