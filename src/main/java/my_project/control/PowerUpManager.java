// PowerUpManager.java
package my_project.control;

import my_project.model.PowerUp;
import my_project.model.Player;
import java.awt.*;
import java.util.*;

public class PowerUpManager {
    private final ArrayList<PowerUp> powerUps = new ArrayList<>();
    private final Player player;
    private final Random random = new Random();
    private double timer = 0;

    public PowerUpManager(Player player) {
        this.player = player;
    }

    public void update(double dt) {
        timer -= dt;
        if (timer <= 0) {
            powerUps.add(new PowerUp(random.nextInt(800), random.nextInt(600), random.nextInt(2) + 1));
            timer = 5;
        }

        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            if (p.collidesWith(player)) {
                if (p.getType() == 1) player.heal(100);
                else if (p.getType() == 2) player.activateRapidFire(5);
                it.remove();
            }
        }
    }

    public void draw(Graphics g) {
        for (PowerUp p : powerUps) {
            p.draw(g);
        }
    }
}
