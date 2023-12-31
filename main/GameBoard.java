package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameBoard {
    private int[][] values;
    private Random rng;

    public GameBoard() {
        this.values = initialiseValues();
        this.rng = new Random();
    }

    public GameBoard(GameBoard board) {
        this.values = setValues(board.getValues());
        this.rng = new Random();
    }

    public int[][] getValues() {
        return this.values;
    }

    public int moveGameBoard(String direction) {
        int score = 0;
        switch (direction) {
            case "up":
                this.compactUp(this.values);
                score += this.mergeUp(this.values);
                this.compactUp(this.values);
                break;
            case "left":
                this.compactLeft(this.values);
                score += this.mergeLeft(this.values);
                this.compactLeft(this.values);
                break;
            case "down":
                this.compactDown(this.values);
                score += this.mergeDown(this.values);
                this.compactDown(this.values);
                break;
            case "right":
                this.compactRight(this.values);
                score += this.mergeRight(this.values);
                this.compactRight(this.values);
                break;
        }
        return score;

    }

    public boolean isBoardFull() {
        for (int row = 0; row < this.values.length; row++) {
            for (int column = 0; column < this.values.length; column++) {
                if (values[row][column] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeNewValue() {
        int[] coordinates = this.getRandomEmptySpace();
        int decider = rng.nextInt(100);
        int value = 0;
        if (decider < 90) {
            value = 2;
        } else {
            value = 4;
        }
        this.values[coordinates[0]][coordinates[1]] = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameBoard other = (GameBoard) obj;
        if (!Arrays.deepEquals(values, other.values))
            return false;
        return true;
    }

    private int[][] initialiseValues() {
        int[][] newValues = new int[4][4];
        for (int row = 0; row < newValues.length; row++) {
            for (int column = 0; column < newValues.length; column++) {
                newValues[row][column] = 0;
            }
        }
        return newValues;
    }

    private int[][] setValues(int[][] boardValues) {
        int[][] newValues = new int[4][4];
        for (int row = 0; row < newValues.length; row++) {
            for (int column = 0; column < newValues.length; column++) {
                newValues[row][column] = boardValues[row][column];
            }
        }
        return newValues;
    }

    private int[] getRandomEmptySpace() {
        int[] coordinates = { -1, -1 };
        if (isBoardFull()) {
            throw new IllegalStateException("Board is full cannot find empty space");
        }
        boolean validSpace = false;
        while (!validSpace) {
            coordinates[0] = rng.nextInt(this.values.length);
            coordinates[1] = rng.nextInt(this.values.length);
            if (this.values[coordinates[0]][coordinates[1]] == 0) {
                validSpace = true;
            }
        }
        return coordinates;
    }

    private void compactLeft(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int column = 0; column < board.length; column++) {
                tileValues.add(board[row][column]);
            }
            List<Integer> sortedTileValues = tileValues.stream().sorted(Comparator.comparing(tile -> tile == 0))
                    .collect(Collectors.toList());
            for (int col = 0; col < board.length; col++) {
                board[row][col] = sortedTileValues.get(col);
            }
        }
    }

    private void compactRight(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int column = 0; column < board.length; column++) {
                tileValues.add(board[row][column]);
            }
            List<Integer> sortedTileValues = tileValues.stream().sorted(Comparator.comparing(tile -> tile != 0))
                    .collect(Collectors.toList());
            for (int col = 0; col < board.length; col++) {
                board[row][col] = sortedTileValues.get(col);
            }
        }
    }

    private void compactUp(int[][] board) {
        for (int column = 0; column < board.length; column++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int row = 0; row < board.length; row++) {
                tileValues.add(board[row][column]);
            }
            List<Integer> sortedTileValues = tileValues.stream().sorted(Comparator.comparing(tile -> tile == 0))
                    .collect(Collectors.toList());
            for (int r = 0; r < board.length; r++) {
                board[r][column] = sortedTileValues.get(r);
            }
        }
    }

    private void compactDown(int[][] board) {
        for (int column = 0; column < board.length; column++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int row = 0; row < board.length; row++) {
                tileValues.add(board[row][column]);
            }
            List<Integer> sortedTileValues = tileValues.stream().sorted(Comparator.comparing(tile -> tile != 0))
                    .collect(Collectors.toList());
            for (int r = 0; r < board.length; r++) {
                board[r][column] = sortedTileValues.get(r);
            }
        }
    }

    private int mergeLeft(int[][] board) {
        int score = 0;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length - 1; column++) {
                if (board[row][column] == board[row][column + 1]) {
                    board[row][column] *= 2;
                    board[row][column + 1] = 0;
                    score += board[row][column];
                }
            }
        }
        return score;
    }

    private int mergeRight(int[][] board) {
        int score = 0;
        for (int row = 0; row < board.length; row++) {
            for (int column = board.length - 1; column > 0; column--) {
                if (board[row][column] == board[row][column - 1]) {
                    board[row][column] *= 2;
                    board[row][column - 1] = 0;
                    score += board[row][column];
                }
            }
        }
        return score;
    }

    private int mergeUp(int[][] board) {
        int score = 0;
        for (int column = 0; column < board.length; column++) {
            for (int row = 0; row < board.length - 1; row++) {
                if (board[row][column] == board[row + 1][column]) {
                    board[row][column] *= 2;
                    board[row + 1][column] = 0;
                    score += board[row][column];
                }
            }
        }
        return score;
    }

    private int mergeDown(int[][] board) {
        int score = 0;
        for (int column = 0; column < board.length; column++) {
            for (int row = board.length - 1; row > 0; row--) {
                if (board[row][column] == board[row - 1][column]) {
                    board[row][column] *= 2;
                    board[row - 1][column] = 0;
                    score += board[row][column];
                }
            }
        }
        return score;
    }

}
