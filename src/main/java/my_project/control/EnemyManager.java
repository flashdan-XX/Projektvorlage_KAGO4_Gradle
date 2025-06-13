package my_project.control;

import my_project.model.Enemy;
import my_project.model.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.Graphics;

public class EnemyManager {

    private final ArrayList<Enemy> enemies;
    private double spawnTimer = 0;
    private final Player player;
    private final Random random = new Random();

    public EnemyManager(Player player) {
        this.enemies = new ArrayList<>();
        this.player = player;
    }

    public void update(double dt) {
        spawnTimer -= dt;
        if (spawnTimer <= 0) {
            spawnEnemy();
            spawnTimer = 1 + random.nextDouble();
        }

        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            e.update(dt);

            if (e.isOffScreen()) {
                iterator.remove();
                continue;
            }

            double dx = e.getX() - player.getX();
            double dy = e.getY() - player.getY();
            if (Math.hypot(dx, dy) < 30) {
                player.takeDamage(50);
                iterator.remove();
            }
        }
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            e.draw(g);
        }
    }

    private void spawnEnemy() {
        double x = random.nextInt(800);
        enemies.add(new Enemy(x, -30, random.nextInt(3) + 1));
    }
}