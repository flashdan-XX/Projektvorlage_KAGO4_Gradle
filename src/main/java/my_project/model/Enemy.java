package my_project.model;

import KAGO_framework.model.GraphicalObject;
import java.awt.*;

public class Enemy extends GraphicalObject {

    private double speed = 100;
    private int type;

    public Enemy(double x, double y, int type) {
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 30;
        this.type = type;
    }

    @Override
    public void update(double dt) {
        y += speed * dt;
    }

    @Override
    public void draw(Graphics g) {
        switch (type) {
            case 1 -> g.setColor(Color.RED);
            case 2 -> g.setColor(Color.ORANGE);
            case 3 -> g.setColor(Color.YELLOW);
            default -> g.setColor(Color.GRAY);
        }
        g.fillOval((int)x, (int)y, (int)width, (int)height);
    }

    public boolean isOffScreen() {
        return y > 1000;
    }

    public int getType() {
        return type;
    }
}