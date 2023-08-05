package main;

public class StateChecker {
    private State state;

    public StateChecker() {
    }

    public State getState(GameBoard gameBoard) {
        if (this.gameWon(gameBoard)) {
            state = State.WON;
        } else if (this.gameOver(gameBoard)) {
            state = State.GAMEOVER;
        } else {
            state = State.PLAYING;
        }
        return state;
    }

    private boolean gameWon(GameBoard gameBoard) {
        int[][] values = gameBoard.getValues();
        for (int row = 0; row < values.length; row++) {
            for (int column = 0; column < values.length; column++) {
                if (values[row][column] >= 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean gameOver(GameBoard gameBoard) {
        GameBoard newBoard = new GameBoard(gameBoard);
        if (!newBoard.isBoardFull()) {
            return false;
        }
        newBoard.moveGameBoard("up");
        newBoard.moveGameBoard("down");
        newBoard.moveGameBoard("left");
        newBoard.moveGameBoard("right");
        if (newBoard.equals(gameBoard)) {
            return true;
        }
        return false;
    }
}
