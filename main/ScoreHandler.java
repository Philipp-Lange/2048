package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ScoreHandler {
    private List<Integer> scores;
    private String filePath;

    public ScoreHandler(String filepath) {
        this.filePath = filepath;
        this.scores = readScores();
    }

    public int getHighScore(Game game) {
        return Math.max(this.scores.get(0), game.getScore());
    }

    public int getCurrentScore(Game game) {
        return game.getScore();
    }

    public void saveScores(Game game) {
        int currentScore = game.getScore();
        this.setScores(currentScore);
        this.writeScores(this.scores);
    }

    private void setScores(int currentScore) {
        this.scores.add(currentScore);
        List<Integer> list = this.scores.stream()
                .sorted((Comparator.reverseOrder()))
                .collect(Collectors.toList());
        list.remove(5);
        this.scores = list;
    }

    private void writeScores(List<Integer> scores) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (int score : scores) {
                writer.write(score + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing to score file: " + e.getMessage());
        }
    }

    private List<Integer> readScores() {
        List<Integer> scores = new ArrayList<Integer>();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                scores.add(Integer.valueOf(scanner.nextLine()));
            }
        } catch (Exception e) {
            System.out.println("Error reading scores:" + e.getMessage());
        }
        return scores;
    }

}
