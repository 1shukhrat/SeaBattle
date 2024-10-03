package ru.saynurdinov;

public class Ship {
    private final int size;
    private int hits;
    private final boolean isHorizontal;

    public Ship(int size, boolean isHorizontal) {
        this.size = size;
        this.isHorizontal = isHorizontal;
        this.hits = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void hit() {
        hits++;
    }

    public boolean isSunk() {
        return hits >= size;
    }
}

