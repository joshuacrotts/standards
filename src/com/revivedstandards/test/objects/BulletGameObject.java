package com.revivedstandards.test.objects;

import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardBoxParticle;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.test.FollowTheMouseGameTest;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.ShapeType;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BulletGameObject extends StandardGameObject implements DeathListener
{

    //
    //  Miscellaneous variables
    //
    private final FollowTheMouseGameTest sg;
    private final StandardCollisionHandler sch;
    private final StandardCamera sc;

    //
    //  Static reference to the BufferedImages
    //
    private static final BufferedImage[] frames = new BufferedImage[ 4 ];

    //
    //  Handler for particle explosions after the bullet
    //  collides with a wall.
    //
    private StandardParticleHandler explosionHandler;

    //
    //  One-time variable for tracking the "alive" to "death state" transition
    //
    private boolean aliveFlag = true;

    public BulletGameObject ( FollowTheMouseGameTest sg, StandardCollisionHandler parentContainer, StandardCamera sc, TriangleGameObject parent, int x, int y )
    {
        super( x, y, StandardID.Projectile );
        this.sg = sg;
        this.sch = parentContainer;
        this.setAnimation( new StandardAnimatorController( new StandardAnimation( this, frames, 20 ) ) );
        this.setWidth( this.getWidth() );
        this.setHeight( this.getHeight() );
        this.setAlive( true );
        this.setVelX( parent.getVelX() * 5 );
        this.setVelY( parent.getVelY() * 5 );

        this.sch.flagAlive( this.getId() );
        this.sch.addCollider( this.getId() );

        this.sc = sc;
    }

    @Override
    public void tick ()
    {
        this.updatePosition();

        //As long as the object is alive, we can tick it.
        if ( this.isAlive() )
        {
            this.getAnimationController().tick();
        }
        else
        {
            // Do this only once.
            if ( this.aliveFlag )
            {
                this.uponDeath();
                this.aliveFlag = false;
            }

            // If the size of the exphandler (MAX_PARTICLES - dead ones) == 0,
            // we can set this entity to be dead, and remove it from the handler.
            if ( this.explosionHandler.size() == 0 )
            {
                this.setAlive( false );
            }

            StandardHandler.Handler( this.explosionHandler );
        }
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        // If they're alive, draw the frame that the bullet animation is on.
        // Otherwise, render the explosion handler
        if ( this.isAlive() )
        {
            this.getAnimationController().renderFrame( g2 );
        }
        else
        {
            StandardDraw.Handler( this.explosionHandler );
        }

    }

    @Override
    public void uponDeath ()
    {
        this.playRandomExplosionSFX( StdOps.rand( 0, 2 ) );
        this.explosionHandler = new StandardParticleHandler( 800 );
        this.explosionHandler.setCamera( this.sc );
        this.summonDeathParticles( 200 );
    }

    /**
     * Instantiates the buffered image array.
     *
     * @return
     */
    private static BufferedImage[] initImages ()
    {
        for ( int i = 0 ; i < BulletGameObject.frames.length ; i++ )
        {
            BulletGameObject.frames[ i ] = ( StdOps.loadImage( "src/res/img/bullet/bullet_colors/bullet_" + i + ".png" ) );
        }

        return BulletGameObject.frames;
    }

    /**
     * Summons n particles upon the collision of the particle. This can only be
     * called once.
     *
     * @param n
     */
    private void summonDeathParticles ( int n )
    {
        for ( int i = 0 ; i < n ; i++ )
        {
            this.explosionHandler.addEntity( new StandardBoxParticle( this.getX(), this.getY(), StdOps.rand( 0.5, 2.5 ),
                                                                      StdOps.randBounds( -5, -0.1, 0.1, 5 ),
                                                                      StdOps.randBounds( -5, -1, 1, 5 ),
                                                                      BulletGameObject.getRandomRGYB( StdOps.rand( 0, 3 ) ),
                                                                      4f, this.explosionHandler, 0.0, ShapeType.CIRCLE, true ) );
        }

        this.aliveFlag = false;
    }

    /**
     * Plays a random explosion sfx
     *
     * @param n
     */
    private void playRandomExplosionSFX ( int n )
    {
        StandardAudioController.play( "src/res/audio/sfx/damage_" + n + ".wav" );
    }

    /**
     * Generates a random Red, green, blue, or yellow color.
     *
     * @param n
     * @return
     */
    private static Color getRandomRGYB ( int n )
    {
        switch ( n )
        {
            case 0:
                return StandardDraw.RED;
            case 1:
                return StandardDraw.GREEN;
            case 2:
                return StandardDraw.YELLOW;
            case 3:
                return StandardDraw.BLUE;
        }

        return null;
    }

    //
    //  Initializes the bullet frames
    //
    static
    {
        BulletGameObject.initImages();
    }
}
