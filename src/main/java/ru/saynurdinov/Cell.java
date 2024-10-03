package ru.saynurdinov;

public class Cell {
    private final int x;
    private final int y;
    private boolean attacked;
    private Ship ship;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.attacked = false;
        this.ship = null;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }


    public boolean isAttacked() {
        return attacked;
    }

    public void attack() {
        attacked = true;
        if (ship != null) {
            ship.hit();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        if (attacked) {
            if (ship == null) {
                return "⬜";
            } else {
                return ship.isSunk() ? "❌" : "\uD83D\uDD25";
            }
        } else {
            return (ship == null) ? "\uD83D\uDFE6" : "🚢";
        }
    }

    public String toOpponentString() {
        if (attacked) {
            if (ship == null) {
                return "⬜";
            } else {
                return ship.isSunk() ? "❌" : "\uD83D\uDD25";
            }
        } else {
            return "\uD83D\uDFE6";
        }
    }
}
