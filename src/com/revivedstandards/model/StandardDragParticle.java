/*
===========================================================================
                   Standards Java Game Library Source Code
           Copyright (C) 2017-2019 Joshua Crotts & Andrew Matzureff 
Standards is free software: you can redistribute it and/or modify it under 
the terms of the GNU General Public License as published by the Free Software 
Foundation, either version 3 of the License, or (at your option) any later 
version.

Standards Source Code is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Standards Source Code. If not, see <http://www.gnu.org/licenses/>.

Standards is the long-overdue update to the everlasting Standards 2.0 library
Andrew Matzureff and I created two years ago. I am including it in this project
to simplify the rendering and logic pipeline, but with a focus on the MVC
paradigm.

We connect to the Apache FastMath API for some of our trigonometric functions,
and we use John Carmack's fast inverse square root function.
===========================================================================
 */

package com.revivedstandards.model;

import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;
import org.apache.commons.math3.util.FastMath;

public class StandardDragParticle extends StandardParticle {

    public StandardDragParticle( double x, double y, double life )
    {
        super( x, y, life );

        double d = StdOps.rand( 0.0D, 6.283185307179586D );
        double h = StdOps.rand( 0.05D, 2.5D );

        this.setVelX( h * FastMath.sin( d ) );
        this.setVelY( h * FastMath.cos( d ) );

        this.color = new Color( 255, 255 - ( int ) this.getDeath() & 0x7F, 255 - ( int ) this.getDeath() & 0xFF, 255 );
    }

    public StandardDragParticle( double x, double y, double life, StandardHandler handler )
    {
        super( x, y, life, handler );

        double d = StdOps.rand( 0.0D, 6.283185307179586D );
        double h = StdOps.rand( 0.05D, 2.5D );

        this.setVelX( h * FastMath.sin( d ) );
        this.setVelY( h * FastMath.cos( d ) );
        
        this.color = new Color( 255, 255 - ( int ) this.getDeath() & 0x7F, 255 - ( int ) this.getDeath() & 0xFF, 255 );
    }

    public StandardDragParticle( double x, double y, double life, StandardHandler handler, Color c )
    {
        super( x, y, life, handler );

        double d = StdOps.rand( 0.0D, 6.283185307179586D );
        double h = StdOps.rand( 0.05D, 2.5D );

        this.setVelX( h * FastMath.sin( d ) );
        this.setVelY( h * FastMath.cos( d ) );

        this.color = c;
    }

    public void tick()
    {
        if ( this.isAlive() )
        {

            this.setWidth( ( int ) ( this.getVelX() * this.getVelX() * FastMath.signum( this.getVelX() ) ) + 1 );
            this.setHeight( ( int ) ( this.getVelY() * this.getVelY() * FastMath.signum( this.getVelY() ) ) + 1 );
            this.setX( this.getX() + this.getVelX() );
            this.setVelY( this.getVelY() + 0.05D );
            this.setY(  this.getY() + this.getVelY());
            this.setAlive( ( System.nanoTime() - this.getDeath() <= 0L ) );
        }
    }

    public void render( Graphics2D g2 )
    {
        if ( !this.isAlive() )
        {
            return;
        }
        int red = this.color.getRed();
        int green = this.color.getGreen();
        int blue = this.color.getBlue();
        int alpha = this.color.getAlpha();
        g2.setColor( new Color( red, green, blue, alpha ) );

        g2.drawLine( ( int ) this.getX(), ( int ) this.getY(), ( int ) ( this.getX() + this.getWidth() ), ( int ) ( this.getY() + this.getHeight() ) );
    }
}
