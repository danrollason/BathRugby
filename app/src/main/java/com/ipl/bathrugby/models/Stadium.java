package com.ipl.bathrugby.models;

import java.io.Serializable;
import java.util.Random;

public class Stadium implements Serializable{
    public static final int ROWS = 15;
    public static final int COLUMNS = 25;
    private static final double FRACTION_OF_SEATS_TAKEN = 0.9;

    private Seat[][] seats;

    public Stadium() {
        seats = new Seat[ROWS][COLUMNS];
    }

    public Stadium(Seat userSeat) {
        this();
        for(int r = 0; r< Stadium.ROWS; r++) {
            for (int c = 0; c <Stadium.COLUMNS; c++) {
                boolean isSeatTaken = new Random().nextDouble()<FRACTION_OF_SEATS_TAKEN;
                seats[r][c] = new Seat(r, c, false, isSeatTaken);
            }
        }
        seats[userSeat.getRow()][userSeat.getColumn()] = userSeat;
    }

    public Stadium(Seat[][] seats) {
        this.seats = seats;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public void getSeats(Seat[][] seats) {
        this.seats = seats;
    }

    public Seat getSeat(int r, int c) {
        return seats[r][c];
    }
}
