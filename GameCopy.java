import java.util.HashMap;
import java.util.Map;

public class GameCopy {
    private GameBoard board;
    private StateChecker stateChecker;
    private Map<Integer, GameBoard> boardHistory;
    private int boardHistoryIndex;

    public GameCopy() {
        this.stateChecker = new StateChecker();
        this.board = new GameBoard();
        this.board.placeNewValue();
        this.board.placeNewValue();
        this.boardHistory = new HashMap<>();
        this.boardHistoryIndex = -1;

        addBoardToHistory(this.board);
    }

    public int[][] getBoard() {
        return this.board.getValues();
    }

    public void move(char keyPressed) {
        GameBoard newBoard = new GameBoard(this.board);
        switch (keyPressed) {
            case 'w':
                newBoard = newBoard.moveGameBoard("up");
                break;
            case 'a':
                newBoard = newBoard.moveGameBoard("left");
                break;
            case 's':
                newBoard = newBoard.moveGameBoard("down");
                break;
            case 'd':
                newBoard = newBoard.moveGameBoard("right");
                break;
        }

        if (!newBoard.isBoardFull() && !newBoard.equals(this.board)) {
            newBoard.placeNewValue();
        }

        addBoardToHistory(newBoard);
        this.board = newBoard;
    }

    public void redo() {
        if (this.boardHistoryIndex >= 0) {
            this.board = this.undoMove(this.boardHistory);
        }
    }

    public void undo() {
        if (this.boardHistoryIndex < this.boardHistory.size() - 1) {
            this.board = this.redoMove(this.boardHistory);
        }
    }

    public String getState() {
        return stateChecker.getState(this.board);
    }

    public void reset() {
        this.board = new GameBoard();
        this.boardHistory.clear();
        this.boardHistoryIndex = 0;
    }

    public void setTestCondition() {
        this.board.getValues()[0][0] = 2048;
    }

    private void addBoardToHistory(GameBoard newBoard) {
        int index = this.boardHistory.size() - 1;
        while (this.boardHistoryIndex < index) {
            this.boardHistory.remove(index);
            index--;
        }
        this.boardHistoryIndex++;
        this.boardHistory.put(this.boardHistoryIndex, newBoard);
    }

    private GameBoard undoMove(Map<Integer, GameBoard> boardHistory) {
        this.boardHistoryIndex--;
        if (this.boardHistoryIndex <= 0) {
            this.boardHistoryIndex = 0;
        }
        return boardHistory.get(this.boardHistoryIndex);
    }

    private GameBoard redoMove(Map<Integer, GameBoard> boardHistory) {
        this.boardHistoryIndex++;
        if (this.boardHistoryIndex >= boardHistory.size() - 1) {
            this.boardHistoryIndex = boardHistory.size() - 1;
        }
        return boardHistory.get(this.boardHistoryIndex);
    }
}
