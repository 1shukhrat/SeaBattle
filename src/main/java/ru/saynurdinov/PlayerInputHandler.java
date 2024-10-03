package ru.saynurdinov;

import java.util.Scanner;

public class PlayerInputHandler {

    Scanner scanner = new Scanner(System.in);

    public int[] getPlayerMove() {
        int[] coordinates;
        while (true) {
            System.out.print("Введите координаты: ");
            String move = scanner.nextLine();
            coordinates = parseCoordinates(move);
            if (coordinates != null) {
                return coordinates;
            } else {
                System.out.println("Неверный ввод! Попробуйте еще раз.");
            }
        }
    }

    private int[] parseCoordinates(String move) {
        if (move.length() < 2 || move.length() > 3) return null;
        int row = move.toUpperCase().charAt(0) - 'А';
        int column;
        try {
            column = Integer.parseInt(move.substring(1)) - 1;
        } catch (NumberFormatException e) {
            return null;
        }
        if (row < 0 || row >= 10 || column < 0 || column >= 10) return null;
        return new int[]{row, column};
    }
}
