package com.revivedstandards.test.objects;

import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.test.commands.MoveCommand;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class AnimatedPlayerGameObject extends StandardGameObject
{

    //
    //  Game and Camera variables
    //
    private final StandardGame sg;
    private final StandardCamera sc;

    //
    //  Movements
    //
    private final MoveCommand rightMovement;
    private final MoveCommand leftMovement;

    //
    //  BufferedImages shared across many references of this object.
    //
    private static final BufferedImage[] leftFrames;
    private static final BufferedImage[] rightFrames;

    private boolean moving = false;
    private final int spriteSize = 156;

    public AnimatedPlayerGameObject ( StandardGame sg, StandardCamera sc, int x, int y )
    {
        super( x, y, StandardID.Player );

        //  Assigns the default game and camera vars
        this.sg = sg;
        this.sc = sc;

        this.setWidth( spriteSize );
        this.setHeight( spriteSize );

        //  Creates two StandardAnimatorControllers
        StandardAnimatorController leftController  = new StandardAnimatorController( (
                                                     new StandardAnimation( this, leftFrames, StdOps.rand( 10, 30 ), 16 ) ) );
        StandardAnimatorController rightController = new StandardAnimatorController( (
                                                     new StandardAnimation( this, rightFrames, StdOps.rand( 10, 30 ), 16 ) ) );

        //  Defaults animation to the right controller
        this.setAnimation( rightController );

        //
        //  Initializes movement objects
        //  After initializing the left & right controllers, we delegate control
        //  over when they're set to the commands.
        //
        this.leftMovement = new MoveCommand( this, leftController, -2.0f, 0f );
        this.leftMovement.bind( this.sg.getKeyboard(), KeyEvent.VK_A );

        this.rightMovement = new MoveCommand( this, rightController, 2.0f, 0f );
        this.rightMovement.bind( this.sg.getKeyboard(), KeyEvent.VK_D );
    }

    @Override
    public void tick ()
    {
        this.updatePosition();

        //As long as the object is alive, we can tick it.
        if ( this.isAlive() && this.moving )
        {
            this.getAnimationController().tick();
        }
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        if ( this.isAlive() )
        {
            this.getAnimationController().renderFrame( g2 );
        }
    }

    private static BufferedImage[] initLeftFrames ()
    {
        BufferedImage[] frames = new BufferedImage[ 32 ];

        for ( int i = 0 ; i < frames.length ; i++ )
        {
            frames[ i ] = StdOps.loadImage( "src/res/img/char_walk_l/walk_l_" + i + ".png" );
        }

        return frames;
    }

    private static BufferedImage[] initRightFrames ()
    {
        BufferedImage[] frames = new BufferedImage[ 32 ];

        for ( int i = 0 ; i < frames.length ; i++ )
        {
            frames[ i ] = StdOps.loadImage( "src/res/img/char_walk_r/walk_r_" + i + ".png" );
        }

        return frames;
    }

    public void setMoving ( boolean b )
    {
        this.moving = b;
    }

    //
    //  Initializes the frame arrays
    //
    static
    {
        leftFrames = AnimatedPlayerGameObject.initLeftFrames();
        rightFrames = AnimatedPlayerGameObject.initRightFrames();
    }

}
