// Player.java
package my_project.model;

import KAGO_framework.model.GraphicalObject;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends GraphicalObject {

    private double speed = 200;
    private int leben = 300;
    private int score = 0;

    private boolean firePressed = true;
    private double fireCooldown = 0.07;
    private double fireTimer = 0;
    private double rapidFireTimer = 0;

    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;
    }

    @Override
    public void update(double dt) {
        if (isKeyPressed(KeyEvent.VK_W)) y -= speed * dt;
        if (isKeyPressed(KeyEvent.VK_S)) y += speed * dt;
        if (isKeyPressed(KeyEvent.VK_A)) x -= speed * dt;
        if (isKeyPressed(KeyEvent.VK_D)) x += speed * dt;

        if (rapidFireTimer > 0) {
            fireCooldown = 0.03;
            rapidFireTimer -= dt;
        } else {
            fireCooldown = 0.07;
        }

        fireTimer -= dt;
        if (firePressed && fireTimer <= 0) {
            shoot();
            fireTimer = fireCooldown;
        }

        for (Projectile p : projectiles) {
            p.update(dt);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        g.setColor(Color.WHITE);
        for (Projectile p : projectiles) {
            g.fillRect((int)p.getX(), (int)p.getY(), (int)p.getWidth(), (int)p.getHeight());
        }
    }

    public void shoot() {
        projectiles.add(new Projectile(x + width / 2, y));
    }

    public int getScore() { return score; }
    public int getLeben() { return leben; }
    public void takeDamage(int dmg) { leben -= dmg; }
    public void heal(int amount) { leben = Math.min(leben + amount, 300); }
    public void activateRapidFire(double sekunden) { rapidFireTimer = sekunden; }
    public ArrayList<Projectile> getProjectiles() { return projectiles; }
    public void addScore(int value) { score += value; }

    private boolean isKeyPressed(int keyCode) {
        return KAGO_framework.control.ViewController.getInstance().getKeyStates()[keyCode];
    }
}
