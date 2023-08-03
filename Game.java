import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private GameBoard board;
    private StateChecker stateChecker;
    private Map<Integer, GameBoard> history;
    private List<Integer> scoreHistory;
    private Map<Character, String> keyBinds;
    private int historyIndex;
    private int scoreHistoryIndex;
    private int score;

    public Game() {
        this.stateChecker = new StateChecker();
        this.board = new GameBoard();
        this.board.placeNewValue();
        this.board.placeNewValue();

        this.history = new HashMap<>();
        this.historyIndex = -1;
        this.scoreHistory = new ArrayList<>();
        this.scoreHistoryIndex = -1;

        this.keyBinds = new HashMap<>();
        this.setKeyBinds();

        this.score = 0;
        this.addToBoardHistory(this.board);
        this.addToScoreHistory(this.score);
    }

    public int[][] getBoard() {
        return this.board.getValues();
    }

    public void move(char keyPressed) {
        GameBoard newBoard = new GameBoard(this.board);
        this.score += newBoard.moveGameBoard(this.keyBinds.get(keyPressed));

        // Note sure if this should be nested if statements or seperate.
        // I feel like seperate is more readable.

        if (!newBoard.equals(this.board)) {
            addToBoardHistory(newBoard);
            addToScoreHistory(this.score);
        }

        if (!newBoard.isBoardFull() && !newBoard.equals(this.board)) {
            newBoard.placeNewValue();
        }

        this.board = newBoard;
    }

    public void undo() {
        this.board = this.undoMove(this.history);
        this.score = this.undoScore(this.scoreHistory);
        System.out.println("Board Index: " + this.historyIndex);
        System.out.println("Score Index: " + this.scoreHistoryIndex);
    }

    public void redo() {
        this.board = this.redoMove(this.history);
        this.score = this.redoScore(this.scoreHistory);
        System.out.println("Board Index: " + this.historyIndex);
        System.out.println("Score Index: " + this.scoreHistoryIndex);
    }

    public State getState() {
        return stateChecker.getState(this.board);
    }

    public int getScore() {
        return this.score;
    }

    public void reset() {
        this.board = new GameBoard();
        this.board.placeNewValue();
        this.board.placeNewValue();
        this.history.clear();
        this.scoreHistory.clear();
        this.score = 0;
        this.historyIndex = 0;
        this.scoreHistoryIndex = 0;
    }

    public void setTestCondition() {
        int[][] testBoard = { { 4, 8, 4, 2 }, { 16, 64, 128, 8 }, { 2, 16, 64, 16 }, { 2, 4, 8, 2 } };
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                this.board.getValues()[row][column] = testBoard[row][column];
            }
        }
    }

    private void addToBoardHistory(GameBoard newBoard) {
        int historySize = this.history.size() - 1;
        while (this.historyIndex < historySize) {
            this.history.remove(historySize);
            historySize--;
        }
        this.historyIndex++;
        this.history.put(this.historyIndex, newBoard);
    }

    private void addToScoreHistory(int score) {
        int historySize = this.scoreHistory.size() - 1;
        while (this.scoreHistoryIndex < historySize) {
            this.scoreHistory.remove(historySize);
            historySize--;
        }
        this.scoreHistoryIndex++;
        this.scoreHistory.add(score);
    }

    private GameBoard undoMove(Map<Integer, GameBoard> history) {
        if (this.historyIndex > 0) {
            this.historyIndex--;
            return history.get(this.historyIndex);
        }
        return this.board;
    }

    private GameBoard redoMove(Map<Integer, GameBoard> history) {
        if (this.historyIndex < history.size() - 1) {
            this.historyIndex++;
            return history.get(this.historyIndex);
        }
        return this.board;
    }

    private int undoScore(List<Integer> history) {
        if (this.scoreHistoryIndex > 0) {
            this.scoreHistoryIndex--;
            return history.get(this.scoreHistoryIndex);
        }
        return this.score;
    }

    private int redoScore(List<Integer> history) {
        if (this.scoreHistoryIndex < scoreHistory.size() - 1) {
            this.scoreHistoryIndex++;
            return history.get(this.scoreHistoryIndex);
        }
        return this.score;
    }

    private void setKeyBinds() {
        // in future should allow custom key bindings
        this.keyBinds.put('w', "up");
        this.keyBinds.put('a', "left");
        this.keyBinds.put('s', "down");
        this.keyBinds.put('d', "right");
    }
}
