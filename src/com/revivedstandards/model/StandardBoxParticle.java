package com.revivedstandards.model;

import com.revivedstandards.handlers.StandardParticleHandler;
import java.awt.Color;
import java.awt.Graphics2D;


public class StandardBoxParticle extends StandardParticle
{
    public StandardBoxParticle ( double x, double y, double dimension, 
                                 double velX, double velY, Color c, double life, 
                                 StandardParticleHandler sph, double rotationAngle )
    {
        super( x, y, life, sph, c, rotationAngle);

        super.setWidth(( int ) dimension);
        super.setHeight(( int ) dimension);
        super.setVelX( velX );
        super.setVelY( velY );
    }

    @Override
    public void tick ()
    {
        super.updatePosition();
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        g2.setColor( this.getColor() );
        
        g2.fillRect( ( int ) this.getX(), ( int ) this.getY(), 
                     ( int ) this.getWidth(), ( int ) this.getHeight() );
    }

}
