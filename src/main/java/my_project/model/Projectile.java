// Projectile.java
package my_project.model;

import KAGO_framework.model.GraphicalObject;

public class Projectile extends GraphicalObject {
    private double speed = 500;

    public Projectile(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = 4;
        this.height = 10;
    }

    @Override
    public void update(double dt) {
        y -= speed * dt;
    }
}
