import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.*;

public class Game {
    private int[][] board;
    private Random rng;
    private GameState state;

    public Game() {
        this.rng = new Random();
        this.state = new GameState();
        this.board = new int[4][4];

        initializeBoard(this.board);
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void moveRight() {
        int[][] newBoard = new int[4][4];
        copyBoardValues(this.board, newBoard);
        this.compactRight(newBoard);
        this.mergeRight(newBoard);
        this.compactRight(newBoard);
        if (!this.isBoardFull() && !Arrays.deepEquals(newBoard, this.board)) {
            this.placeNewTile(newBoard);
            this.state.reset();
        } else {
            this.state.canMoveRight = false;
        }
        copyBoardValues(newBoard, this.board);
    }

    public void moveLeft() {
        int[][] newBoard = new int[4][4];
        copyBoardValues(this.board, newBoard);
        this.compactLeft(newBoard);
        this.mergeLeft(newBoard);
        this.compactLeft(newBoard);
        if (!this.isBoardFull() && !Arrays.deepEquals(newBoard, this.board)) {
            this.placeNewTile(newBoard);
            this.state.reset();
        } else {
            this.state.canMoveLeft = false;
        }
        copyBoardValues(newBoard, this.board);
    }

    public void moveDown() {
        int[][] newBoard = new int[4][4];
        copyBoardValues(this.board, newBoard);
        this.compactDown(newBoard);
        this.mergeDown(newBoard);
        this.compactDown(newBoard);
        if (!this.isBoardFull() && !Arrays.deepEquals(newBoard, this.board)) {
            this.placeNewTile(newBoard);
            this.state.reset();
        } else {
            this.state.canMoveDown = false;
        }
        copyBoardValues(newBoard, this.board);
    }

    public void moveUp() {
        int[][] newBoard = new int[4][4];
        copyBoardValues(this.board, newBoard);
        this.compactUp(newBoard);
        this.mergeUp(newBoard);
        this.compactUp(newBoard);
        if (!this.isBoardFull() && !Arrays.deepEquals(newBoard, this.board)) {
            this.placeNewTile(newBoard);
        } else {
            state.canMoveUp = false;
        }
        copyBoardValues(newBoard, this.board);
        // maybe track high score by comparing old to new board.
    }

    public boolean isGameOver() {
        return this.state.isGameOver();
    }

    public boolean isGameWon() {
        return this.state.isGameWon(this.board);
    }

    public void reset() {
        initializeBoard(this.board);
        this.state.reset();
    }

    public void setTestCondition() {
        this.board[0][0] = 2048;
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

    private void mergeLeft(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length - 1; column++) {
                if (board[row][column] == board[row][column + 1]) {
                    board[row][column] *= 2;
                    board[row][column + 1] = 0;
                }
            }
        }
    }

    private void mergeRight(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int column = board.length - 1; column > 0; column--) {
                if (board[row][column] == board[row][column - 1]) {
                    board[row][column] *= 2;
                    board[row][column - 1] = 0;
                }
            }
        }
    }

    private void mergeUp(int[][] board) {
        for (int column = 0; column < board.length; column++) {
            for (int row = 0; row < board.length - 1; row++) {
                if (board[row][column] == board[row + 1][column]) {
                    board[row][column] *= 2;
                    board[row + 1][column] = 0;
                }
            }
        }
    }

    private void mergeDown(int[][] board) {
        for (int column = 0; column < board.length; column++) {
            for (int row = board.length - 1; row > 0; row--) {
                if (board[row][column] == board[row - 1][column]) {
                    board[row][column] *= 2;
                    board[row - 1][column] = 0;
                }
            }
        }
    }

    private void initializeBoard(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = 0;
            }
        }
        placeNewTile(board);
        placeNewTile(board);
    }

    private void placeNewTile(int[][] board) {
        boolean validTarget = false;
        int targetRow = -1;
        int targetColumn = -1;
        while (!validTarget) {
            targetRow = rng.nextInt(board.length);
            targetColumn = rng.nextInt(board.length);

            if (board[targetRow][targetColumn] == 0) {
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
        board[targetRow][targetColumn] = value;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board.length; column++) {
                if (this.board[row][column] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void copyBoardValues(int[][] boardToCopy, int[][] target) {
        for (int row = 0; row < boardToCopy.length; row++) {
            for (int col = 0; col < boardToCopy.length; col++) {
                target[row][col] = boardToCopy[row][col];
            }
        }
    }
}
