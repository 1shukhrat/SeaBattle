package ru.saynurdinov;

public class Player {
    private final Board board;
    private final String name;

    public Player(String name) {
        this.board = new Board(10, 10);
        this.name = name;
    }

    public AttackResult attack(Player opponent, int x, int y) {
        AttackResult attackResult = opponent.getBoard().attackCell(x, y);
        switch (attackResult) {
            case HIT -> System.out.printf("%s попал в корабль игрока %s!\n", name, opponent.getName());
            case MISS -> System.out.printf("%s промахнулся!\n", name);
            case SHIP_SUNK -> System.out.printf("%s потопил корабль игрока %s!\n", name, opponent.getName());
            case ALREADY_ATTACKED -> System.out.printf("%s пытается поразить уже атакованную клетку!\n", name);
        }
        return attackResult;
    }

    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }
}
