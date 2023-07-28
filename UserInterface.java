public class UserInterface {
    private Game game;

    public UserInterface(Game game) {
        this.game = game;
    }

    public void start() {
        printBoard();
    }

    private void printBoard() {
        Tile[][] board = game.getBoard();
        for (int row = 0; row < board.length; row++) {
            System.out.print("+------+" + "+------+" + "+------+" + "+------+\n");
            System.out.print("|      |" + "|      |" + "|      |" + "|      |\n");
            System.out.print(
                    "|   " + board[row][0].toString() + "  |" +
                            "|   " + board[row][1] + "  |" +
                            "|   " + board[row][2] + "  |" +
                            "|   " + board[row][3] + "  |\n");
            System.out.print("|      |" + "|      |" + "|      |" + "|      |\n");
            System.out.print("+------+" + "+------+" + "+------+" + "+------+\n");
        }
    }
}
