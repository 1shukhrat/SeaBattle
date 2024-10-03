package ru.saynurdinov;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final int rows;
    private final int columns;
    private final Cell[][] cells;
    private final List<Ship> ships;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new Cell[rows][columns];
        ships = new ArrayList<>();
        initializeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void placeShipsRandomly() {
        int[][] shipConfigurations = {
                {4, 1},
                {3, 2},
                {2, 3},
                {1, 4}
        };

        for (int[] shipConfig : shipConfigurations) {
            int size = shipConfig[0];
            int count = shipConfig[1];
            for (int i = 0; i < count; i++) {
                placeShipRandomly(size);
            }
        }
    }

    private void placeShipRandomly(int size) {
        Random random = new Random();
        boolean placed = false;

        while (!placed) {
            int x = random.nextInt(rows);
            int y = random.nextInt(columns);
            boolean horizontal = random.nextBoolean();
            Ship ship = new Ship(size, horizontal);

            if (canPlaceShip(ship, x, y)) {
                for (int i = 0; i < size; i++) {
                    if (horizontal) {
                        cells[x][y + i].setShip(ship);
                    } else {
                        cells[x + i][y].setShip(ship);
                    }
                }
                ships.add(ship);
                placed = true;
            }
        }
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int size = ship.getSize();
        if (ship.isHorizontal()) {
            return y + size <= columns && isAreaClear(x, y, x, y + size - 1);
        } else {
            return x + size <= rows && isAreaClear(x, y, x + size - 1, y);
        }
    }

    private boolean isAreaClear(int startX, int startY, int endX, int endY) {
        int minX = Math.max(0, startX - 1);
        int maxX = Math.min(rows - 1, endX + 1);
        int minY = Math.max(0, startY - 1);
        int maxY = Math.min(columns - 1, endY + 1);

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                if (cells[i][j].hasShip()) {
                    return false;
                }
            }
        }
        return true;
    }

    public AttackResult attackCell(int x, int y) {
        if (!cells[x][y].isAttacked()) {
            cells[x][y].attack();
            if (cells[x][y].hasShip()) {
                if (cells[x][y].getShip().isSunk()) {
                    markSurroundingCellsAsMissed(cells[x][y].getShip());
                    return AttackResult.SHIP_SUNK;
                } else {
                    return AttackResult.HIT;
                }

            } else {
                return AttackResult.MISS;
            }
        }
        return AttackResult.ALREADY_ATTACKED;
    }

    public boolean areAllShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) return false;
        }
        return true;
    }

    private void markSurroundingCellsAsMissed(Ship sunkShip) {
        for (Cell cell : getShipCells(sunkShip)) {
            int x = cell.getX();
            int y = cell.getY();

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int newX = x + dx;
                    int newY = y + dy;

                    if (isWithinBounds(newX, newY) && !cells[newX][newY].isAttacked()) {
                        cells[newX][newY].attack();
                    }
                }
            }
        }
    }

    private List<Cell> getShipCells(Ship ship) {
        List<Cell> shipCells = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cells[i][j].getShip() == ship) {
                    shipCells.add(cells[i][j]);
                }
            }
        }
        return shipCells;
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < columns;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
