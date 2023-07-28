public class GameState {
    public boolean canMoveRight;
    public boolean canMoveLeft;
    public boolean canMoveDown;
    public boolean canMoveUp;

    public GameState() {
        this.canMoveRight = true;
        this.canMoveLeft = true;
        this.canMoveDown = true;
        this.canMoveUp = true;
    }

    public boolean isGameOver() {
        if (this.canMoveLeft || this.canMoveRight || this.canMoveDown || this.canMoveUp) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isGameWon(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public void reset() {
        this.canMoveRight = true;
        this.canMoveLeft = true;
        this.canMoveDown = true;
        this.canMoveUp = true;
    }
}
