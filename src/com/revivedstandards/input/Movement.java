package com.revivedstandards.input;

import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.model.StandardGameObject;

public class Movement extends Command {

    public StandardGameObject player;
    public StandardAnimatorController animator;
    public float deltaX;
    public float deltaY;

    public Movement( StandardGameObject sgo, StandardAnimatorController sa, float deltax, float deltay )
    {
        this.player = sgo;
        this.animator = sa;
        this.deltaX = deltax;
        this.deltaY = deltay;
    }

    public void execute()
    {
        if ( this.animator != null )
        {
            this.animator.animate();
        }
        this.player.setVelX( this.player.getVelX() + this.deltaX );
        this.player.setVelY( this.player.getVelY() + this.deltaY );
    }
}
