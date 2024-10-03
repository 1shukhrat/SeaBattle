package ru.saynurdinov;

import java.util.Random;

public class Game {
    private final Player player;
    private final Player computer;
    private final PlayerInputHandler inputHandler;
    private final BoardPrinter boardPrinter;

    public Game() {
        player = new Player("Игрок");
        computer = new Player("Компьютер");
        inputHandler = new PlayerInputHandler();
        boardPrinter = new BoardPrinter();
    }

    public void start() {
        Board playerBoard = player.getBoard();
        Board computerBoard = computer.getBoard();
        playerBoard.placeShipsRandomly();
        computerBoard.placeShipsRandomly();
        System.out.println("Бой начался!");
        GAME:
        while (true) {
            while (playerTurn()) {
                if (computerBoard.areAllShipsSunk()) {
                    System.out.printf("%s победили!\n", player.getName());
                    break GAME;
                }
            }
            while (computerTurn()) {
                if (playerBoard.areAllShipsSunk()) {
                    System.out.printf("%s победили!\n", computer.getName());
                    break GAME;
                }
            }
        }
        System.out.println("Результаты игры:");
        boardPrinter.print(player.getBoard(), false);
        boardPrinter.print(computer.getBoard(), true);
    }

    private boolean playerTurn() {
        boardPrinter.print(player.getBoard(), false);
        boardPrinter.print(computer.getBoard(), true);
        System.out.printf("%s, ваш ход...\n", player.getName());
        AttackResult attackResult;
        do {
            int[] coordinates = inputHandler.getPlayerMove();
            attackResult = player.attack(computer, coordinates[1], coordinates[0]);
        } while (attackResult == AttackResult.ALREADY_ATTACKED);
        return attackResult != AttackResult.MISS;
    }

    private boolean computerTurn() {
        System.out.printf("%s, ваш ход...\n", computer.getName());
        Random random = new Random();
        int x, y;
        AttackResult attackResult;
        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
            attackResult = computer.attack(player, y, x);
        } while (attackResult == AttackResult.ALREADY_ATTACKED);
        return attackResult != AttackResult.MISS;
    }


}
