package main;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame implements KeyListener {
    private Game game;
    private Map<Integer, ImageIcon> sprites;
    private JLabel[][] tiles;
    private JLabel endScreen;
    private JLabel highScore;
    private JLabel currentScore;
    private ImageIcon gameOverImage;
    private ImageIcon gameWonImage;
    private ImageIcon hiddenImage;
    private ScoreHandler scoreHandler;

    public GameFrame(Game game) {
        this.game = game;
        this.tiles = new JLabel[4][4];
        this.sprites = new HashMap<Integer, ImageIcon>();
        this.scoreHandler = new ScoreHandler("main/HighScores.txt");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(495, 520);
        this.setLayout(null);
        this.setTitle("Scuffed 2048");
        this.addKeyListener(this);

        this.endScreen = new JLabel();
        this.endScreen.setBounds(90, 90, 300, 300);
        this.add(this.endScreen);

        this.highScore = new JLabel("High Score: " + this.scoreHandler.getHighScore(this.game));
        this.highScore.setBounds(25, 5, 150, 15);
        this.add(this.highScore);

        this.currentScore = new JLabel("Current Score: 0", SwingConstants.RIGHT);
        this.currentScore.setBounds(305, 5, 150, 15);
        this.add(this.currentScore);

        initializeBoard();
        intializeSprites();
        this.setVisible(true);

        while (true) {
            renderBoard();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        if ("wasd".indexOf(key) != -1) {
            this.game.move(key);
        } else if (key == 'o') {
            this.game.undo();
        } else if (key == 'p') {
            this.game.redo();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 32) {
            if (this.game.getState() == State.PLAYING) {
                return;
            }
            this.scoreHandler.saveScores(this.game);
            this.game.reset();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void initializeBoard() {
        for (int row = 0; row < this.tiles.length; row++) {
            for (int column = 0; column < this.tiles.length; column++) {
                this.tiles[row][column] = new JLabel();
                int yBound = 25 + row * 110;
                int xBound = 25 + column * 110;
                this.tiles[row][column].setBounds(xBound, yBound, 100, 100);
                this.add(this.tiles[row][column]);
            }
        }
    }

    private void renderBoard() {
        int[][] board = this.game.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                this.tiles[row][column].setIcon(this.sprites.get(board[row][column]));
            }
        }
        switch (this.game.getState()) {
            case PLAYING:
                this.endScreen.setIcon(this.hiddenImage);
                break;
            case GAMEOVER:
                this.endScreen.setIcon(this.gameOverImage);
                break;
            case WON:
                this.endScreen.setIcon(this.gameWonImage);
                break;
        }
        this.currentScore.setText("Current Score: " + this.scoreHandler.getCurrentScore(this.game));
        this.highScore.setText("High Score: " + this.scoreHandler.getHighScore(this.game));
    }

    private void intializeSprites() {
        this.sprites.put(0, new ImageIcon("GameImages/empty.png"));
        this.sprites.put(2, new ImageIcon("GameImages/2.png"));
        this.sprites.put(4, new ImageIcon("GameImages/4.png"));
        this.sprites.put(8, new ImageIcon("GameImages/8.png"));
        this.sprites.put(16, new ImageIcon("GameImages/16.png"));
        this.sprites.put(32, new ImageIcon("GameImages/32.png"));
        this.sprites.put(64, new ImageIcon("GameImages/64.png"));
        this.sprites.put(128, new ImageIcon("GameImages/128.png"));
        this.sprites.put(256, new ImageIcon("GameImages/256.png"));
        this.sprites.put(512, new ImageIcon("GameImages/512.png"));
        this.sprites.put(1024, new ImageIcon("GameImages/1024.png"));
        this.sprites.put(2048, new ImageIcon("GameImages/2048.png"));
        this.gameOverImage = new ImageIcon("GameImages/game-over.png");
        this.gameWonImage = new ImageIcon("GameImages/game-won.png");
        this.hiddenImage = new ImageIcon("GameImages/hidden.png");
    }

}
