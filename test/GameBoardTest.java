package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import main.GameBoard;

public class GameBoardTest {

    @Test
    public void testEquals() {
        GameBoard board1 = new GameBoard();
        GameBoard board2 = new GameBoard();
        assertTrue(board1.equals(board2));
    }

    @Test
    public void testGetValues() {
        GameBoard board1 = new GameBoard();
        int[][] values = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        assertArrayEquals(values, board1.getValues());
    }

    @Test
    public void testIsBoardFull() {
        GameBoard board = new GameBoard();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                board.getValues()[row][col] = 2;
            }
        }
        assertTrue(board.isBoardFull());
    }

    @Test
    public void testMoveGameBoardUp() {
        GameBoard board = new GameBoard();
        int[][] startValues = { { 2, 4, 8, 16 }, { 2, 4, 8, 16 }, { 2, 4, 8, 16 }, { 2, 4, 8, 16 } };
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                board.getValues()[row][col] = startValues[row][col];
            }
        }
        board.moveGameBoard("up");
        int[][] endValues = { { 4, 8, 16, 32 }, { 4, 8, 16, 32 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        assertArrayEquals(endValues, board.getValues());
    }

    @Test
    public void testMoveGameBoardLeft() {
        GameBoard board = new GameBoard();
        int[][] startValues = { { 16, 8, 8, 8 }, { 32, 16, 16, 16 }, { 128, 64, 64, 64 }, { 8, 4, 4, 4 } };
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                board.getValues()[row][col] = startValues[row][col];
            }
        }
        board.moveGameBoard("left");
        int[][] endValues = { { 16, 16, 8, 0 }, { 32, 32, 16, 0 }, { 128, 128, 64, 0 }, { 8, 8, 4, 0 } };
        assertArrayEquals(endValues, board.getValues());
    }

    @Test
    public void testMoveGameBoardDown() {
        GameBoard board = new GameBoard();
        int[][] startValues = { { 2, 4, 8, 16 }, { 2, 4, 8, 16 }, { 2, 4, 8, 16 }, { 4, 8, 16, 32 } };
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                board.getValues()[row][col] = startValues[row][col];
            }
        }
        board.moveGameBoard("down");
        int[][] endValues = { { 0, 0, 0, 0 }, { 2, 4, 8, 16 }, { 4, 8, 16, 32 }, { 4, 8, 16, 32 } };
        assertArrayEquals(endValues, board.getValues());
    }

    @Test
    public void testMoveGameBoardRight() {
        GameBoard board = new GameBoard();
        int[][] startValues = { { 8, 8, 8, 16 }, { 16, 16, 16, 32 }, { 64, 64, 64, 128 }, { 4, 4, 4, 8 } };
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                board.getValues()[row][col] = startValues[row][col];
            }
        }
        board.moveGameBoard("right");
        int[][] endValues = { { 0, 8, 16, 16 }, { 0, 16, 32, 32 }, { 0, 64, 128, 128 }, { 0, 4, 8, 8 } };
        assertArrayEquals(endValues, board.getValues());
    }

    @Test
    public void testPlaceNewValue() {
        GameBoard board = new GameBoard();
        board.placeNewValue();
        board.placeNewValue();
        board.placeNewValue();
        int count = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                count += (board.getValues()[row][col] == 4 || board.getValues()[row][col] == 2) ? 1 : 0;
            }
        }
        assertEquals(count, 3);
    }

}
