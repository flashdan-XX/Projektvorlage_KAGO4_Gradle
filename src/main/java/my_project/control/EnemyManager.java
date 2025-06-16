// EnemyManager.java
package my_project.control;

import my_project.model.Enemy;
import my_project.model.Player;
import my_project.model.Projectile;
import java.awt.*;
import java.util.*;

public class EnemyManager {
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final Player player;
    private final Random random = new Random();
    private double spawnTimer = 0;

    public EnemyManager(Player player) {
        this.player = player;
    }

    public void update(double dt) {
        spawnTimer -= dt;
        if (spawnTimer <= 0) {
            enemies.add(new Enemy(random.nextInt(800), -30, random.nextInt(3) + 1));
            spawnTimer = 1 + random.nextDouble();
        }

        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy e = it.next();
            e.update(dt);

            if (e.isOffScreen()) {
                it.remove();
                continue;
            }

            for (Projectile proj : new ArrayList<>(player.getProjectiles())) {
                double dx = e.getX() - proj.getX();
                double dy = e.getY() - proj.getY();
                if (Math.hypot(dx, dy) < 20) {
                    player.addScore(100);
                    it.remove();
                    break;
                }
            }

            double dx = e.getX() - player.getX();
            double dy = e.getY() - player.getY();
            if (Math.hypot(dx, dy) < 30) {
                player.takeDamage(50);
                it.remove();
            }
        }
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            e.draw(g);
        }
    }
}