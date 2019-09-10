package com.revivedstandards.test;

import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardDragParticle;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BulletGameObject extends StandardGameObject implements DeathListener
{

    private final StandardGame sg;
    private final StandardCollisionHandler sch;
    private StandardParticleHandler explosionHandler;
    private boolean aliveFlag = true;

    public BulletGameObject ( StandardGame sg, StandardCollisionHandler parentContainer, TriangleGameObject parent, int x, int y )
    {
        super( x, y, StandardID.Projectile );
        this.sg = sg;
        this.sch = parentContainer;
        this.setAnimation( new StandardAnimatorController( new StandardAnimation( this, this.initImages(), 20 ) ) );
        this.setWidth( this.getWidth() );
        this.setHeight( this.getHeight() );
        this.setAlive( true );
        this.setVelX( parent.getVelX() * 5 );
        this.setVelY( parent.getVelY() * 5 );
        this.getAnimationController().getStandardAnimation().setRotation( parent.getAngle() );
        
        this.sch.flagAlive( this.getId() );
        this.sch.addCollider( this.getId() );
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
                this.sch.removeEntity( this );
            }

            StandardHandler.Handler( this.explosionHandler );
        }
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        if ( this.isAlive() )
        {
            this.getAnimationController().renderFrame( g2 );
        }
        else
        {
            StandardDraw.Handler( this.explosionHandler );
        }

    }

    private BufferedImage[] initImages ()
    {
        BufferedImage[] images = new BufferedImage[ 3 ];

        for ( int i = 0 ; i < images.length ; i++ )
        {
            images[ i ] = ( StdOps.loadImage( "src/res/img/bullet/bullet_colors/bullet_" + i + ".png" ) );
        }

        return images;
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
            this.explosionHandler.addEntity( new StandardDragParticle( this.getX(), this.getY(),
                    4f, this.explosionHandler, this.getRandomRGYB( StdOps.rand( 0, 3 ) ), this.getAnimationController().getStandardAnimation().getRotation() ) );
        }
        this.aliveFlag = false;
    }
    
    @Override
    public void uponDeath()
    {
        this.playRandomExplosionSFX( StdOps.rand( 0, 2 ) );
        this.explosionHandler = new StandardParticleHandler( 50 );
        this.summonDeathParticles( 50 );
    }
    
    /**
     * Plays a random explosion sfx
     * @param n 
     */
    private void playRandomExplosionSFX( int n )
    {
        StandardAudioController.play( "src/res/audio/sfx/damage_" + n + ".wav" );
    }
    
    /**
     * Generates a random Red, green, blue, or yellow color.
     * @param n
     * @return 
     */
    private Color getRandomRGYB( int n )
    {
        switch( n )
        {
            case 0: return StandardDraw.RED;
            case 1: return StandardDraw.GREEN;
            case 2: return StandardDraw.YELLOW;
            case 3: return StandardDraw.BLUE;
        }
        
        return null;
    }

}
