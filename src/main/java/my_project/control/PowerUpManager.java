package my_project.control;

import my_project.model.Player;
import my_project.model.PowerUp;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PowerUpManager {

    private final ArrayList<PowerUp> powerUps;
    private final Player player;
    private final Random random = new Random();

    private double timer = 0;
    private final double spawnInterval = 5;

    public PowerUpManager(Player player) {
        this.player = player;
        this.powerUps = new ArrayList<>();
    }

    public void update(double dt) {
        timer -= dt;
        if (timer <= 0) {
            spawnPowerUp();
            timer = spawnInterval;
        }

        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            if (p.collidesWith(player)) {
                applyPowerUp(p.getType());
                it.remove();
            }
        }
    }

    public void draw(Graphics g) {
        for (PowerUp p : powerUps) {
            p.draw(g);
        }
    }

    private void spawnPowerUp() {
        double x = random.nextInt(800);
        double y = random.nextInt(600);
        int type = random.nextInt(2) + 1;
        powerUps.add(new PowerUp(x, y, type));
    }

    private void applyPowerUp(int type) {
        switch (type) {
            case 1 -> player.heal(100);
            case 2 -> player.activateRapidFire(5);
        }
    }
}