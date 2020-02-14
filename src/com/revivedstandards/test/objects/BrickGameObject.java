package com.revivedstandards.test.objects;

import com.revivedstandards.controller.StandardFadeController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Color;
import java.awt.Graphics2D;

public class BrickGameObject extends StandardGameObject {

    private final StandardGame sg;
    private final StandardCollisionHandler globalHandler;
    private StandardFadeController fade;
    private Color color = Color.BLACK;

    public BrickGameObject(StandardGame sg, StandardCollisionHandler sch, int x, int y) {
        super(x, y, StandardID.Obstacle);
        this.sg = sg;
        this.globalHandler = sch;

        this.setWidth(32);
        this.setHeight(this.getWidth());

        this.globalHandler.addCollider(StandardID.Obstacle);
    }

    public BrickGameObject(StandardGame sg, StandardCollisionHandler sch, int x, int y, int dim) {
        super(x, y, StandardID.Obstacle);
        this.sg = sg;
        this.globalHandler = sch;

        this.setWidth(dim);
        this.setHeight(this.getWidth());

        this.globalHandler.addCollider(StandardID.Obstacle);
    }

    public BrickGameObject(StandardGame sg, StandardCollisionHandler sch, int x, int y, Color color) {
        this(sg, sch, x, y);

        this.color = color;
    }

    public BrickGameObject(StandardGame sg, StandardCollisionHandler sch, int x, int y, int dim, Color color) {
        this(sg, sch, x, y);

        this.setWidth(dim);
        this.setHeight(this.getWidth());
        this.color = color;
        this.fade = new StandardFadeController(color, Color.RED, 0.005f);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(this.fade.combine());
        g2.fillRect((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
        g2.setColor(Color.BLACK);
        g2.drawRect((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
