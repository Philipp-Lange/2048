import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame implements KeyListener {
    private Game game;
    private boolean gameIsOver;
    private Map<Integer, ImageIcon> sprites;
    private JLabel[][] tiles;
    private JLabel endScreen;
    private ImageIcon gameOverImage;
    private ImageIcon gameWonImage;
    private ImageIcon hiddenImage;

    public GameFrame(Game game) {
        this.game = game;
        this.tiles = new JLabel[4][4];
        this.sprites = new HashMap<Integer, ImageIcon>();
        this.gameIsOver = false;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(495, 520);
        this.setLayout(null);
        this.setTitle("Scuffed 2048");
        this.addKeyListener(this);

        this.endScreen = new JLabel();
        this.endScreen.setBounds(90, 90, 300, 300);
        this.add(this.endScreen);

        initializeBoard();
        intializeSprites();
        this.setVisible(true);

        while (true) {
            while (!gameIsOver) {
                renderBoard();
                if (this.game.isGameOver() || this.game.isGameWon()) {
                    gameIsOver = true;
                    break;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                this.game.moveUp();
                break;
            case 'a':
                this.game.moveLeft();
                break;
            case 's':
                this.game.moveDown();
                break;
            case 'd':
                this.game.moveRight();
                break;
            case 'p':
                this.game.setTestCondition();
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 32:
                if (!this.gameIsOver) {
                    break;
                }
                this.game.reset();
                this.gameIsOver = false;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void initializeBoard() {
        for (int row = 0; row < this.tiles.length; row++) {
            for (int column = 0; column < this.tiles.length; column++) {
                this.tiles[row][column] = new JLabel();
                int xBound = 25 + row * 110;
                int yBound = 25 + column * 110;
                this.tiles[row][column].setBounds(xBound, yBound, 100, 100);
                this.add(this.tiles[row][column]);
            }
        }
    }

    private void renderBoard() {
        int[][] board = this.game.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                this.tiles[column][row].setIcon(this.sprites.get(board[row][column]));
                // no idea why I need [column][row] and then [row][column]
            }
        }
        this.endScreen.setIcon(this.hiddenImage);
        if (this.game.isGameOver()) {
            this.endScreen.setIcon(this.gameOverImage);
        }
        if (this.game.isGameWon()) {
            this.endScreen.setIcon(this.gameWonImage);
        }
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
