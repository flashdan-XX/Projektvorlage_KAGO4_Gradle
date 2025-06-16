package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.Player;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ProgramController {

    private boolean gameStarted = false;
    private boolean gameOver = false;

    private Player player;
    private EnemyManager enemyManager;
    private PowerUpManager powerUpManager;

    private Image background;
    private Font font;

    private ViewController viewController;

    public ProgramController() {
        // Manuelle Instanziierung der Controller
        viewController = new ViewController(); // Manuell

        // Ressourcen (ohne Sound und TTF)
        this.background = Toolkit.getDefaultToolkit().getImage("src/main/resources/graphic/hintergrund.jpeg");
        this.font = new Font("SansSerif", Font.BOLD, 60);  // Einfache Schriftart ohne TTF

        // Spielobjekte instanziieren
        this.player = new Player(200, 200);
        this.enemyManager = new EnemyManager(player);
        this.powerUpManager = new PowerUpManager(player);

        viewController.register(player);
    }

    public void updateProgramLogic(double dt) {
        if (!gameStarted && viewController.getKeyStates()[KeyEvent.VK_SPACE]) {
            gameStarted = true;
        }

        if (gameStarted && !gameOver) {
            player.update(dt);
            enemyManager.update(dt);
            powerUpManager.update(dt);

            if (player.getLeben() <= 0) {
                gameOver = true;
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
}
