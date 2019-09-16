package com.revivedstandards.test.commands;

import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.test.FollowTheMouseGameTest;
import com.revivedstandards.test.GameState;
import com.revivedstandards.test.objects.BulletGameObject;
import com.revivedstandards.test.objects.TriangleGameObject;

/**
 * This class shows how to create a command to spawn an entity.
 *
 * It also shows how to handle different events based on the current game state,
 * or the entity's state. All events regarding the bullet should be delegated
 * here. The SGO or game should have no idea about this class, but the bullet
 * SHOULD know about the external world, so it can decide what it should do
 * (either fire or not) based on the external world.
 *
 * @author Joshua
 */
public class BulletCommand extends Command
{

    private final FollowTheMouseGameTest sg;
    private final TriangleGameObject obj;
    private final StandardCollisionHandler globalHandler;

    public BulletCommand ( FollowTheMouseGameTest sg, TriangleGameObject obj, StandardCollisionHandler gh )
    {
        this.sg = sg;
        this.obj = obj;
        this.globalHandler = gh;
    }

    @Override
    public void pressed ( float dt )
    {
        if ( this.obj.getBulletCount() == 0 || this.sg.getGameState() == GameState.PAUSED )
        {
            return;
        }

        this.globalHandler.addEntity( new BulletGameObject( this.sg, this.globalHandler, this.obj.getCamera(), this.obj,
                ( int ) this.obj.getX() + this.obj.getWidth() / 2,
                ( int ) this.obj.getY() + this.obj.getHeight() / 2 ) );

        StandardAudioController.play( "src/res/audio/sfx/laser_sfx.wav" );

        this.obj.decrementBulletCount();
    }

}
