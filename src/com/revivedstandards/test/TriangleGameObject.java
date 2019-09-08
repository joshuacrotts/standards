package com.revivedstandards.test;

import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardBoxParticle;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.model.StandardParticle;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import org.apache.commons.math3.util.FastMath;

/**
 * This class represents a subclass of the StandardGameObject, demonstrating its
 * capabilities, and moreover, how to use it properly. We load in a sprite, pass
 * it to the superclass, and override the tick() and render() methods.
 */
public class TriangleGameObject extends StandardGameObject
{
    private StandardGame sg;
    private StandardCamera sc;
    private StandardParticleHandler sph;

    private float angle;
    private static final float APPROACH_VEL = -3.0f;

    public TriangleGameObject ( StandardGame sg, int x, int y, StandardID id )
    {
        super( x, y, "src/res/img/spaceship.png", id );
        this.sg = sg;
        this.sph = new StandardParticleHandler( 500 );
        this.setVelX( 15.0 );
        this.setVelY( 15.0 );
    }

    @Override
    public void tick ()
    {
        this.updatePosition();

        //*******************************************************************//
        //      Causes the arrow to follow the cursor wherever on the screen //
        //*******************************************************************//
        // Save the mouse position
        double mx = this.sc.getX() + this.sg.getMouse().getMouseX() - this.sc.getVpw();
        double my = this.sc.getY() + this.sg.getMouse().getMouseY() - this.sc.getVph();

        // Calculate the distance between the sprite and the mouse
        double diffX = this.getX() - mx - 8;
        double diffY = this.getY() - my - 8;

        // Use the pythagorean theorem to solve for the hypotenuse distance
        double distance = ( double ) FastMath.sqrt( ( ( this.getX() - mx ) * ( this.getX() - mx ) )
                + ( ( this.getY() - my ) * ( this.getY() - my ) ) );

        // Sets the velocity according to how far away the sprite is from the cursor
        this.setVelX( ( APPROACH_VEL / distance ) * diffX );
        this.setVelY( ( APPROACH_VEL / distance ) * diffY );

        //*****************************************************************//
        //      Calculates the angle the ship needs to be in to face the   //
        //      cursor                                                     //
        //*****************************************************************//
        float xSign = ( float ) FastMath.signum( mx - this.getX() );
        double dx = FastMath.abs( mx - this.getX() );
        double dy = FastMath.abs( my - this.getY() );

        this.angle = ( float ) ( ( xSign ) * ( FastMath.atan( ( dx ) / ( dy ) ) ) );

        // If we're in Q1 (+x, -+y) or in Q2 (-x, +y)
        if ( ( mx > this.getX() && my > this.getY() )
                || ( mx < this.getX() && my > this.getY() ) )
        {
            this.angle = ( float ) ( ( FastMath.PI * 0.5 ) + ( FastMath.PI * 0.5 - angle ) );
        }

        //  Adds random particles to the end of the ship to simulate fuel burning
        this.sph.addEntity( new StandardBoxParticle( this.getX() + this.getWidth() * 0.5,
                this.getY() + ( this.getHeight() * 0.5 ) + 20, 6, StdOps.rand( -3.5, 3.5 ),
                StdOps.rand( 5.0, 10.0 ), new Color( 0xFF, StdOps.rand( 0, 0xFF ), 0 ),
                20.0, this.sph, this.angle ) );
        
        StandardHandler.Handler( this.sph );
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        // Save the old transform and instantiate a new one 
        AffineTransform backup = g2.getTransform();
        AffineTransform rotation = new AffineTransform();
        rotation.rotate( 0, 0, 0 );

        // Iterates through the particle handler, finds any existing particle
        // and translates it to ITS specific angle. This is to prevent every
        // single preexisting angle from suddenly changing angles when it
        // shouldn't.
        for ( int i = this.sph.getMaxParticles() - 1; i >= 0; i-- )
        {
            StandardParticle particle = ( StandardParticle ) this.sph.get( i );
            
            //  If we reach a wall, then we stop the loop.
            if ( particle == null )
            {
                break;
            }
            
            g2.rotate( particle.getAngle(), this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2 );
            particle.render( g2 );
            
            //  Do NOT forget this (delete it and try it out. I dare ya.)
            g2.setTransform( backup );
        }
        
        //  Rotate the sprite to its current angle, and draw it.
        g2.rotate( this.angle, this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2 );
        StandardDraw.image( this.getCurrentSprite(), ( int ) this.getX(), ( int ) this.getY() );

        // Reset the old translation
        g2.setTransform( backup );
    }

    public void setCamera ( StandardCamera sc )
    {
        this.sc = sc;
    }
}
