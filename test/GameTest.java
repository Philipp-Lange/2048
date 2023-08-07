package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import main.Game;
import main.State;

public class GameTest {

    @Test
    public void testGetBoard() {
        Game game = new Game();
        assertEquals(game.getBoard()[0][0], 0);
    }

    @Test
    public void testGetScore() {
        Game game = new Game();
        assertEquals(game.getScore(), 0);
    }

    @Test
    public void testGetState() {
        Game game = new Game();
        assertEquals(game.getState(), State.PLAYING);
    }

    @Test
    public void testMove() {
        Game game = new Game();
        game.getBoard()[0][0] = 2;
        game.getBoard()[0][1] = 2;
        game.move('a');
        assertEquals(game.getBoard()[0][0], 4);
    }

    @Test
    public void testRedo() {
        Game game = new Game();
        int[][] initialBoard = new int[4][4];
        deepCopyArray(game.getBoard(), initialBoard);
        game.move('w');
        int[][] afterOneMove = new int[4][4];
        deepCopyArray(game.getBoard(), afterOneMove);
        game.move('a');
        game.undo();
        game.undo();
        game.redo();
        int[][] afterRedo = new int[4][4];
        deepCopyArray(game.getBoard(), afterRedo);
        assertArrayEquals(afterOneMove, afterRedo);
        assertFalse(Arrays.deepEquals(initialBoard, afterRedo));
    }

    @Test
    public void testReset() {
        Game game = new Game();
        game.move('w');
        game.move('a');
        game.move('s');
        game.move('d');
        game.reset();
        int[][] board = game.getBoard();
        int count = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 2 || board[row][col] == 4) {
                    count++;
                }
            }
        }
        assertEquals(count, 2);
    }

    @Test
    public void testUndo() {
        Game game = new Game();
        int[][] initialBoard = new int[4][4];
        deepCopyArray(game.getBoard(), initialBoard);
        game.move('w');
        int[][] afterOneMove = new int[4][4];
        deepCopyArray(game.getBoard(), afterOneMove);
        game.move('a');
        int[][] afterTwoMoves = new int[4][4];
        deepCopyArray(game.getBoard(), afterTwoMoves);

        game.undo();
        int[][] afterOneUndo = new int[4][4];
        deepCopyArray(game.getBoard(), afterOneUndo);
        assertTrue(Arrays.deepEquals(afterOneMove, afterOneUndo));

        game.undo();
        int[][] afterTwoUndos = new int[4][4];
        deepCopyArray(game.getBoard(), afterTwoUndos);
        assertTrue(Arrays.deepEquals(initialBoard, afterTwoUndos));

        assertFalse(Arrays.deepEquals(initialBoard, afterTwoMoves));
    }

    private void deepCopyArray(int[][] original, int[][] copy) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                copy[row][col] = original[row][col];
            }
        }
    }

}
