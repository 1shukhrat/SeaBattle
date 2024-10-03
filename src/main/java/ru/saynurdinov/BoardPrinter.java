package ru.saynurdinov;

public class BoardPrinter {

    public void print(Board board, boolean isOpponentBoard) {
        int columns = board.getColumns();
        int rows = board.getRows();
        Cell[][] cells = board.getCells();
        System.out.println(isOpponentBoard ? "Поле противника:" : "Поле игрока:");
        System.out.print("      ");
        for (char c = 'А'; c < 'А' + columns; c++) {
            System.out.print(c + "    ");
        }
        System.out.println();


        for (int i = 0; i < rows; i++) {
            System.out.printf("%2d    ", i + 1);
            for (int j = 0; j < columns; j++) {
                System.out.print((isOpponentBoard ? cells[i][j].toOpponentString() : cells[i][j].toString()) + "   ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
