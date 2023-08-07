package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import main.Game;
import main.ScoreHandler;

public class ScoreHandlerTest {
    @Test
    public void testGetCurrentScore() {
        Game game = new Game();
        int[][] values = { { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 2, 2, 2, 2 } };
        for (int row = 0; row < values.length; row++) {
            for (int col = 0; col < values.length; col++) {
                game.getBoard()[row][col] = values[row][col];
            }
        }
        game.move('w');
        ScoreHandler scoreHandler = new ScoreHandler("test/mockfiles/TestHighScores.txt");
        int score = scoreHandler.getCurrentScore(game);
        assertEquals(score, 32);
    }

    @Test
    public void testGetHighScoreWhenFileScoreIsHighest() {
        Game game = new Game();
        int[][] values = { { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        for (int row = 0; row < values.length; row++) {
            for (int col = 0; col < values.length; col++) {
                game.getBoard()[row][col] = values[row][col];
            }
        }
        game.move('w');
        ScoreHandler scoreHandler = new ScoreHandler("resources/TestHighScores.txt");
        int highScore = scoreHandler.getHighScore(game);
        assertEquals(highScore, 28);
    }

    @Test
    public void testGetHighScoreWhenCurrentScoreIsHighest() {
        List<Integer> scores = Arrays.asList(new Integer[] { 28, 15, 14, 13, 12 });
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/TestHighScores.txt"));
            for (int score : scores) {
                writer.write(score + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing to score file: " + e.getMessage());
        }
        Game game = new Game();
        int[][] values = { { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 2, 2, 2, 2 } };
        for (int row = 0; row < values.length; row++) {
            for (int col = 0; col < values.length; col++) {
                game.getBoard()[row][col] = values[row][col];
            }
        }
        game.move('w');
        ScoreHandler scoreHandler = new ScoreHandler("resources/TestHighScores.txt");
        int highScore = scoreHandler.getHighScore(game);
        assertEquals(highScore, 32);
    }

    @Test
    public void testSaveScoresIfNotHighestScore() {
        List<Integer> initalScores = Arrays.asList(new Integer[] { 28, 15, 14, 13, 12 });
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/TestHighScores.txt"));
            for (int score : initalScores) {
                writer.write(score + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing to score file: " + e.getMessage());
        }

        Game game = new Game();
        int[][] values = { { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        for (int row = 0; row < values.length; row++) {
            for (int col = 0; col < values.length; col++) {
                game.getBoard()[row][col] = values[row][col];
            }
        }
        game.move('w');
        ScoreHandler scoreHandler = new ScoreHandler("resources/TestHighScores.txt");
        scoreHandler.saveScores(game);

        List<Integer> testScores = new ArrayList<Integer>();
        try {
            Scanner scanner = new Scanner(new File("resources/TestHighScores.txt"));
            while (scanner.hasNextLine()) {
                testScores.add(Integer.valueOf(scanner.nextLine()));
            }
        } catch (Exception e) {
            System.out.println("Error reading scores:" + e.getMessage());
        }
        List<Integer> comparisonScores = Arrays.asList(new Integer[] { 28, 16, 15, 14, 13 });
        assertEquals(testScores, comparisonScores);
    }

    @Test
    public void testSaveScoresIfHighestScore() {
        List<Integer> initalScores = Arrays.asList(new Integer[] { 28, 15, 14, 13, 12 });
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/TestHighScores.txt"));
            for (int score : initalScores) {
                writer.write(score + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing to score file: " + e.getMessage());
        }

        Game game = new Game();
        int[][] values = { { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 2, 2, 2, 2 } };
        for (int row = 0; row < values.length; row++) {
            for (int col = 0; col < values.length; col++) {
                game.getBoard()[row][col] = values[row][col];
            }
        }
        game.move('w');
        ScoreHandler scoreHandler = new ScoreHandler("resources/TestHighScores.txt");
        scoreHandler.saveScores(game);

        List<Integer> testScores = new ArrayList<Integer>();
        try {
            Scanner scanner = new Scanner(new File("resources/TestHighScores.txt"));
            while (scanner.hasNextLine()) {
                testScores.add(Integer.valueOf(scanner.nextLine()));
            }
        } catch (Exception e) {
            System.out.println("Error reading scores:" + e.getMessage());
        }
        List<Integer> comparisonScores = Arrays.asList(new Integer[] { 32, 28, 15, 14, 13 });
        assertEquals(testScores, comparisonScores);
    }

    @Test
    public void testSaveScoresIfNotInTopFive() {
        List<Integer> initalScores = Arrays.asList(new Integer[] { 28, 15, 14, 13, 12 });
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/TestHighScores.txt"));
            for (int score : initalScores) {
                writer.write(score + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing to score file: " + e.getMessage());
        }

        Game game = new Game();
        ScoreHandler scoreHandler = new ScoreHandler("resources/TestHighScores.txt");
        scoreHandler.saveScores(game);

        List<Integer> testScores = new ArrayList<Integer>();
        try {
            Scanner scanner = new Scanner(new File("resources/TestHighScores.txt"));
            while (scanner.hasNextLine()) {
                testScores.add(Integer.valueOf(scanner.nextLine()));
            }
        } catch (Exception e) {
            System.out.println("Error reading scores:" + e.getMessage());
        }
        assertEquals(testScores, initalScores);
    }

}
