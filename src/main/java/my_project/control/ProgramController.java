package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.control.SoundController;
import my_project.model.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class ProgramController {

    private boolean gameStarted = false;
    private boolean gameOver = false;

    private Player player;
    private EnemyManager enemyManager;
    private PowerUpManager powerUpManager;

    private Image background;
    private Font font;

    public void start() {
        ViewController viewController = ViewController.getInstance();

        background = Toolkit.getDefaultToolkit().getImage("src/main/resources/graphic/hintergrund.jpeg");

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/Kanit-SemiBold.ttf")).deriveFont(60f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (Exception e) {
            e.printStackTrace();
            font = new Font("SansSerif", Font.BOLD, 60); // Fallback
        }

        player = new Player(200, 200);
        enemyManager = new EnemyManager(player);
        powerUpManager = new PowerUpManager(player);

        viewController.register(player);
        SoundController.getInstance().playSound("src/main/resources/sound/startSound.mp3", false);
    }

    public void updateProgramLogic(double dt) {
        if (!gameStarted && ViewController.getInstance().getKeyStates()[KeyEvent.VK_SPACE]) {
            startGame();
        }

        if (gameStarted && !gameOver) {
            player.update(dt);
            enemyManager.update(dt);
            powerUpManager.update(dt);

            if (player.getLeben() <= 0) {
                gameOver = true;
                SoundController.getInstance().stopAllSounds();
                SoundController.getInstance().playSound("src/main/resources/sound/gameOverSound.mp3", false);
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.setFont(font);

        if (!gameStarted) {
            g.setColor(Color.RED);
            g.drawString("START THE GAME", 300, 400);
        } else if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 350, 400);
        } else {
            g.setColor(Color.WHITE);
            g.drawString("Leben: " + player.getLeben(), 30, 60);
            g.drawString("Score: " + player.getScore(), 30, 120);

            enemyManager.draw(g);
            powerUpManager.draw(g);
        }
    }

    private void startGame() {
        gameStarted = true;
        SoundController.getInstance().playSound("src/main/resources/sound/gameSound.mp3", true);
    }
}