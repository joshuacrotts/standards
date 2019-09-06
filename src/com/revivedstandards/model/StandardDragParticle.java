package com.revivedstandards.model;

import com.revivedstandards.StdOps;
import com.revivedstandards.handlers.StandardHandler;
import java.awt.Color;
import java.awt.Graphics2D;

public class StandardDragParticle extends StandardParticle {

    public StandardDragParticle( double x, double y, double life )
    {
        super( x, y, life );

        double d = StdOps.rand( 0.0D, 6.283185307179586D );
        double h = StdOps.rand( 0.05D, 2.5D );

        this.velX = h * Math.sin( d );
        this.velY = h * Math.cos( d );

        this.color = new Color( 255, 255 - ( int ) this.death & 0x7F, 255 - ( int ) this.death & 0xFF, 255 );
    }

    public StandardDragParticle( double x, double y, double life, StandardHandler handler )
    {
        super( x, y, life, handler );

        double d = StdOps.rand( 0.0D, 6.283185307179586D );
        double h = StdOps.rand( 0.05D, 2.5D );

        this.velX = h * Math.sin( d );
        this.velY = h * Math.cos( d );

        this.color = new Color( 255, 255 - ( int ) this.death & 0x7F, 255 - ( int ) this.death & 0xFF, 255 );
    }

    public StandardDragParticle( double x, double y, double life, StandardHandler handler, Color c )
    {
        super( x, y, life, handler );

        double d = StdOps.rand( 0.0D, 6.283185307179586D );
        double h = StdOps.rand( 0.05D, 2.5D );

        this.velX = h * Math.sin( d );
        this.velY = h * Math.cos( d );

        this.color = c;
    }

    public void tick()
    {
        if ( this.alive )
        {

            this.width = ( int ) ( this.velX * this.velX * Math.signum( this.velX ) ) + 1;
            this.height = ( int ) ( this.velY * this.velY * Math.signum( this.velY ) ) + 1;
            this.x += this.velX;
            this.y += this.velY += 0.05D;
            this.alive = ( System.nanoTime() - this.death <= 0L );
            return;
        }
    }

    public void render( Graphics2D g2 )
    {
        if ( !this.alive )
        {
            return;
        }
        int red = this.color.getRed();
        int green = this.color.getGreen();
        int blue = this.color.getBlue();
        int alpha = this.color.getAlpha();
        g2.setColor( new Color( red, green, blue, alpha ) );

        g2.drawLine( ( int ) this.x, ( int ) this.y, ( int ) ( this.x + this.width ), ( int ) ( this.y + this.height ) );
    }
}
