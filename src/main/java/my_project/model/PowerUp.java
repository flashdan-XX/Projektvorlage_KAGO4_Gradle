// PowerUp.java
package my_project.model;

import KAGO_framework.model.GraphicalObject;
import java.awt.*;

public class PowerUp extends GraphicalObject {
    private int type;
    private Color color;

    public PowerUp(double x, double y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.width = 20;
        this.height = 20;
        this.color = switch (type) {
            case 1 -> Color.GREEN;
            case 2 -> Color.BLUE;
            default -> Color.GRAY;
        };
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }

    public int getType() { return type; }

    public boolean collidesWith(Player p) {
        double dx = x - p.getX();
        double dy = y - p.getY();
        return Math.hypot(dx, dy) < 30;
    }
}
