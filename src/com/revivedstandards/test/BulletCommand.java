package com.revivedstandards.test;

import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardGame;

public class BulletCommand extends Command
{
    private final StandardGame sg;
    private final TriangleGameObject obj;
    private final StandardCollisionHandler globalHandler;
    
    public BulletCommand( StandardGame sg, TriangleGameObject obj, StandardCollisionHandler gh )
    {
        this.sg = sg;
        this.obj = obj;
        this.globalHandler  = gh;
    }
    
    @Override
    public void pressed( float dt )
    {
        this.globalHandler.addEntity( new BulletGameObject( this.sg, this.globalHandler, this.obj.getCamera(), this.obj, 
                                                          ( int ) this.obj.getX() + this.obj.getWidth() / 2, 
                                                          ( int ) this.obj.getY() + this.obj.getHeight() / 2 ) );
        
        StandardAudioController.play( "src/res/audio/sfx/laser_sfx.wav" );
    }
    
}
