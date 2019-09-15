package com.revivedstandards.test.objects;

import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.input.Movement;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.test.commands.MovementCommand;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class AnimatedPlayerGameObject extends StandardGameObject {

    private final StandardGame sg;
    private final StandardCamera sc;

    private final MovementCommand rightMovement;

    private boolean moving = false;
    private final int spriteSize = 156;

    public AnimatedPlayerGameObject (StandardGame sg, StandardCamera sc, int x, int y) {
        super(x, y, StandardID.Player);

        this.sg = sg;
        this.sc = sc;

        this.setWidth(spriteSize);
        this.setHeight(spriteSize);

        this.setAnimation(new StandardAnimatorController(new StandardAnimation(this, this.getFrames(), 20, 16)));

        this.rightMovement = new MovementCommand(this, this.getAnimationController(), 2.0f, 0f);
        this.rightMovement.bind(this.sg.getKeyboard(), KeyEvent.VK_D);
    }

    @Override
    public void tick () {
        this.updatePosition();

        //As long as the object is alive, we can tick it.
        if (this.isAlive() && this.moving) {
            this.getAnimationController().tick();
        }
    }

    @Override
    public void render (Graphics2D g2) {
        if (this.isAlive()) {
            this.getAnimationController().renderFrame(g2);
        }
    }

    private BufferedImage[] getFrames () {
        BufferedImage[] frames = new BufferedImage[32];

        for (int i = 0 ; i < frames.length ; i++) {
            frames[i] = StdOps.loadImage("src/res/img/char_walk_r/walk_r_" + i + ".png");
        }

        return frames;
    }

    public void setMoving (boolean b) {
        this.moving = b;
    }

}
