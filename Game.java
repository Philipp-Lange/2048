import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.*;

public class Game {
    private Tile[][] board;
    private Random rng;
    private GameState state;

    public Game() {
        this.rng = new Random();
        this.board = new Tile[4][4];
        this.state = new GameState();
        initializeBoard();
    }

    public Tile[][] getBoard() {
        return this.board;
    }

    public void moveRight() {
        this.compactRight();
        this.mergeRight();
        this.compactRight();
        if (!this.isBoardFull()) {
            this.placeNewTile();
        } else {
            this.state.canMoveRight = false;
        }
    }

    public void moveLeft() {
        this.compactLeft();
        this.mergeLeft();
        this.compactLeft();
        if (!this.isBoardFull()) {
            this.placeNewTile();
        } else {
            this.state.canMoveLeft = false;
        }
    }

    public void moveDown() {
        this.compactDown();
        this.mergeDown();
        this.compactDown();
        if (!this.isBoardFull()) {
            this.placeNewTile();
        } else {
            this.state.canMoveDown = false;
        }
    }

    public void moveUp() {
        this.compactUp();
        this.mergeUp();
        this.compactUp();
        if (!this.isBoardFull()) {
            this.placeNewTile();
        } else {
            state.canMoveUp = false;
        }
    }

    public boolean isGameOver() {
        return this.state.isGameOver();
    }

    public boolean isGameWon() {
        return this.state.isGameWon(this.board);
    }

    private void compactLeft() {
        for (int row = 0; row < this.board.length; row++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int column = 0; column < this.board.length; column++) {
                tileValues.add(this.board[row][column].getValue());
            }
            List<Integer> sortedTileValues = tileValues.stream().sorted(Comparator.comparing(tile -> tile == 0))
                    .collect(Collectors.toList());
            for (int col = 0; col < this.board.length; col++) {
                this.board[row][col].setValue(sortedTileValues.get(col));
            }
        }
    }

    private void compactRight() {
        for (int row = 0; row < this.board.length; row++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int column = 0; column < this.board.length; column++) {
                tileValues.add(this.board[row][column].getValue());
            }
            List<Integer> sortedTileValues = tileValues.stream().sorted(Comparator.comparing(tile -> tile != 0))
                    .collect(Collectors.toList());
            for (int col = 0; col < this.board.length; col++) {
                this.board[row][col].setValue(sortedTileValues.get(col));
            }
        }
    }

    private void compactUp() {
        for (int column = 0; column < this.board.length; column++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int row = 0; row < this.board.length; row++) {
                tileValues.add(this.board[row][column].getValue());
            }
            List<Integer> sortedTileValues = tileValues.stream().sorted(Comparator.comparing(tile -> tile == 0))
                    .collect(Collectors.toList());
            for (int r = 0; r < this.board.length; r++) {
                this.board[r][column].setValue(sortedTileValues.get(r));
            }
        }
    }

    private void compactDown() {
        for (int column = 0; column < this.board.length; column++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int row = 0; row < this.board.length; row++) {
                tileValues.add(this.board[row][column].getValue());
            }
            List<Integer> sortedTileValues = tileValues.stream().sorted(Comparator.comparing(tile -> tile != 0))
                    .collect(Collectors.toList());
            for (int r = 0; r < this.board.length; r++) {
                this.board[r][column].setValue(sortedTileValues.get(r));
            }
        }
    }

    private void mergeLeft() {
        for (int row = 0; row < this.board.length; row++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int column = 0; column < this.board.length - 1; column++) {
                if (this.board[row][column].getValue() == this.board[row][column + 1].getValue()) {
                    tileValues.add(this.board[row][column].getValue() * 2);
                    tileValues.add(0);
                    column++;
                } else {
                    tileValues.add(this.board[row][column].getValue());
                }
            }
            if (this.board[row][2].getValue() != this.board[row][3].getValue())
                tileValues.add(this.board[row][3].getValue());

            while (tileValues.size() < 4) {
                tileValues.add(0);
            }

            for (int col = 0; col < this.board.length; col++) {
                this.board[row][col].setValue(tileValues.get(col));
            }
        }
    }

    private void mergeRight() {
        for (int row = 0; row < this.board.length; row++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int column = this.board.length - 1; column > 0; column--) {
                if (this.board[row][column].getValue() == this.board[row][column - 1].getValue()) {
                    tileValues.add(this.board[row][column].getValue() * 2);
                    tileValues.add(0);
                    column--;
                } else {
                    tileValues.add(this.board[row][column].getValue());
                }
            }

            if (this.board[row][0].getValue() != this.board[row][1].getValue())
                tileValues.add(this.board[row][0].getValue());

            while (tileValues.size() < 4) {
                tileValues.add(0);
            }

            Collections.reverse(tileValues);

            for (int col = 0; col < this.board.length; col++) {
                this.board[row][col].setValue(tileValues.get(col));
            }
        }
    }

    private void mergeUp() {
        for (int column = 0; column < this.board.length; column++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int row = 0; row < this.board.length - 1; row++) {
                if (this.board[row][column].getValue() == this.board[row + 1][column].getValue()) {
                    tileValues.add(this.board[row][column].getValue() * 2);
                    tileValues.add(0);
                    row++;
                } else {
                    tileValues.add(this.board[row][column].getValue());
                }
            }

            if (this.board[2][column].getValue() != this.board[3][column].getValue())
                tileValues.add(this.board[3][column].getValue());

            while (tileValues.size() < 4) {
                tileValues.add(0);
            }

            for (int r = 0; r < this.board.length; r++) {
                this.board[r][column].setValue(tileValues.get(r));
            }
        }
    }

    private void mergeDown() {
        for (int column = 0; column < this.board.length; column++) {
            List<Integer> tileValues = new ArrayList<>();
            for (int row = this.board.length - 1; row > 0; row--) {
                if (this.board[row][column].getValue() == this.board[row - 1][column].getValue()) {
                    tileValues.add(this.board[row][column].getValue() * 2);
                    tileValues.add(0);
                    row--;
                } else {
                    tileValues.add(this.board[row][column].getValue());
                }
            }

            if (this.board[0][column].getValue() != this.board[1][column].getValue())
                tileValues.add(this.board[0][column].getValue());

            while (tileValues.size() < 4) {
                tileValues.add(0);
            }

            Collections.reverse(tileValues);

            for (int r = 0; r < this.board.length; r++) {
                this.board[r][column].setValue(tileValues.get(r));
            }
        }
    }

    private void initializeBoard() {
        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board.length; column++) {
                this.board[row][column] = new Tile();
            }
        }
        placeNewTile();
        placeNewTile();
    }

    private void placeNewTile() {
        boolean validTarget = false;
        int targetRow = -1;
        int targetColumn = -1;
        while (!validTarget) {
            targetRow = rng.nextInt(this.board.length);
            targetColumn = rng.nextInt(this.board.length);

            if (this.board[targetRow][targetColumn].getValue() == 0) {
                validTarget = true;
            }
        }
        int decider = rng.nextInt(100);
        int value = 0;
        if (decider < 90) {
            value = 2;
        } else {
            value = 4;
        }
        this.board[targetRow][targetColumn].setValue(value);
    }

    private boolean isBoardFull() {
        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board.length; column++) {
                if (this.board[row][column].getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
