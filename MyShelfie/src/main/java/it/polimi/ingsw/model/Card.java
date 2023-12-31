package it.polimi.ingsw.model;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.ItemEnum;

/**
 * This class is used in the personal card's implementation
 * @author Donato Fiore
 */
public class Card {
    @Expose
    private final Triplet[] dataTriad = new Triplet[6];
    @Expose
    private final ItemEnum[][] matrixPersonal = new ItemEnum[6][5];
    @Expose
    private int id;

    public Card() {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                matrixPersonal[i][j] = ItemEnum.BLANK;
    }

    /**
     * This method is used to add a triplet to the card
     * @param idTriplet id
     * @param x row
     * @param y column
     * @param color color
     */
    public void addTriplet(int idTriplet, int x, int y, ItemEnum color) {
        matrixPersonal[x][y] = color;
        dataTriad[idTriplet] = new Triplet(x, y, color);
    }

    /**
     * This method is used to get the matrix of the card
     */
    public ItemEnum[][] getMatrix() {
        ItemEnum[][] copy = new ItemEnum[6][5];
        for (int i = 0; i < 6; i++)
            System.arraycopy(this.matrixPersonal[i], 0, copy[i], 0, 5);
        return copy;
    }

/**
     * This method is used to get a triplet from the card
     * @param i the index of the triplet
     */
    public Triplet getTriplet(int i) {
        Triplet tris;

        tris = dataTriad[i];

        return tris;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
