package test;

import static org.junit.Assert.*;
import org.junit.Test;

import main.GameBoard;
import main.State;
import main.StateChecker;

public class StateCheckerTest {
    @Test
    public void testGetStateWon() {
        GameBoard board = new GameBoard();
        board.getValues()[0][0] = 2048;
        StateChecker stateChecker = new StateChecker();
        State state = stateChecker.getState(board);
        assertEquals(state, State.WON);
    }

    @Test
    public void testGetStateLost() {
        GameBoard board = new GameBoard();
        int[][] values = { { 4, 8, 4, 2 }, { 16, 64, 128, 8 }, { 4, 16, 64, 16 }, { 2, 4, 8, 2 } };
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                board.getValues()[row][col] = values[row][col];
            }
        }
        StateChecker stateChecker = new StateChecker();
        State state = stateChecker.getState(board);
        assertEquals(state, State.GAMEOVER);
    }

    @Test
    public void testGetStatePlaying() {
        GameBoard board = new GameBoard();
        StateChecker stateChecker = new StateChecker();
        State state = stateChecker.getState(board);
        assertEquals(state, State.PLAYING);
    }
}
