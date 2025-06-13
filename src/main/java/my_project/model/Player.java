package my_project.model;

import KAGO_framework.model.GraphicalObject;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends GraphicalObject {

    private double speed = 200;
    private int leben = 300;
    private int score = 0;

    private boolean firePressed = false;
    private double fireCooldown = 0.07;
    private double fireTimer = 0;
    private double rapidFireTimer = 0;

    private ArrayList<Projectile> projectiles;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;
        this.projectiles = new ArrayList<>();
    }

    @Override
    public void update(double dt) {
        // Bewegung
        if (isKeyPressed(KeyEvent.VK_W)) y -= speed * dt;
        if (isKeyPressed(KeyEvent.VK_S)) y += speed * dt;
        if (isKeyPressed(KeyEvent.VK_A)) x -= speed * dt;
        if (isKeyPressed(KeyEvent.VK_D)) x += speed * dt;

        // Power-Up Effekt
        if (rapidFireTimer > 0) {
            fireCooldown = 0.03;
            rapidFireTimer -= dt;
        } else {
            fireCooldown = 0.07;
        }

        // Schießen
        fireTimer -= dt;
        if (firePressed && fireTimer <= 0) {
            shoot();
            fireTimer = fireCooldown;
        }

        // Projektile aktualisieren
        for (Projectile p : projectiles) {
            p.update(dt);
        }
    }

    public void shoot() {
        projectiles.add(new Projectile(x + width / 2, y));
    }

    public void setFirePressed(boolean pressed) {
        this.firePressed = pressed;
    }

    public int getScore() {
        return score;
    }

    public int getLeben() {
        return leben;
    }

    public void takeDamage(int dmg) {
        leben -= dmg;
    }

    public void heal(int amount) {
        leben += amount;
        if (leben > 300) leben = 300;
    }

    public void activateRapidFire(double sekunden) {
        rapidFireTimer = sekunden;
    }

    private boolean isKeyPressed(int keyCode) {
        return KAGO_framework.control.ViewController.getInstance().getKeyStates()[keyCode];
    }
}