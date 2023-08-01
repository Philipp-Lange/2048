public class StateChecker {

    public StateChecker() {
    }

    public String getState(GameBoard gameBoard) {
        if (this.gameWon(gameBoard)) {
            return "won";
        } else if (this.gameOver(gameBoard)) {
            return "gameover";
        } else {
            return "playing";
        }
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
        GameBoard newBoard = new GameBoard();
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
