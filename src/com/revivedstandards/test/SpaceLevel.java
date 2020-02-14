package com.revivedstandards.test;

import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardLevel;
import com.revivedstandards.test.objects.TriangleGameObject;
import java.awt.Graphics2D;

/**
 * Demonstrates the concept of a very primitive level using the Standards API.
 */
public class SpaceLevel extends StandardLevel {

    //
    //  Player of the game
    //
    private final TriangleGameObject player;

    private int trackX;
    private int trackXX;

    public SpaceLevel(TriangleGameObject player) {
        super(null, "src/res/img/bg/space_bg.jpg", null);

        this.player = player;
        StandardAudioController.play("src/res/audio/music/level1.wav");
    }

    @Override
    public void loadLevelData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tick() {
        this.trackX -= (int) this.player.getVelX() * 0.25;
        this.trackXX -= (int) this.player.getVelX() * 0.50;
    }

    @Override
    public void render(Graphics2D g2) {
        if (this.getBgImage() != null) {

            if (this.trackX <= 0) {
                g2.drawImage(this.getBgImage(), this.trackX, 0, null);
            } else {
                g2.drawImage(this.getBgImage(), 0, 0, null);
            }

            if (this.trackXX <= 0) {
                g2.drawImage(this.getBgImage(), this.trackXX, 0, null);
            } else {
                g2.drawImage(this.getBgImage(), 0, 0, null);
            }

        }
    }

}
