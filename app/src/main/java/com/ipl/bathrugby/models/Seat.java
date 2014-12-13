package com.ipl.bathrugby.models;

import java.io.Serializable;

public class Seat implements Serializable{
    private int row;
    private int column;
    private boolean isUser;
    private boolean isTaken;
    private int pixelColour = 0xffffffff;

    public Seat() {
        this(0,0,false,false);
    }

    public Seat(int row, int column, boolean isUser, boolean isTaken) {
        this.row = row;
        this.column = column;
        this.isUser = isUser;
        this.isTaken = isTaken;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean isUser) {
        this.isUser = isUser;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean isTaken) {
        this.isTaken = isTaken;
    }

    public int getPixelColour() {
        return pixelColour;
    }

    public void setPixelColour(int pixelColour) {
        this.pixelColour = pixelColour;
    }
}
