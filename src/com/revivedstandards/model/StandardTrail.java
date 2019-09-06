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
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class StandardTrail extends StandardGameObject {

    private float alpha = 1.0F;
    private float life;
    private boolean isImage = false;

    private Color color;
    private String shape;
    private StandardGameObject obj;

    private StandardHandler stdHandler;

    public StandardTrail( double x, double y, double width, double height, Color color, float life, StandardGameObject o, StandardHandler stdHandler, String shape, boolean isImage )
    {
        super( x, y, ( int ) width, ( int ) height );

        this.color = color;
        this.life = life;
        this.shape = shape;
        setId( StandardID.Trail );

        this.stdHandler = stdHandler;
        this.stdHandler.addEntity( this );

        this.isImage = isImage;
        this.obj = o;
        checkNullShape();
    }

    private AlphaComposite makeTransparent( float alpha )
    {
        int type = 3;
        return AlphaComposite.getInstance( type, alpha );
    }

    @Override
    public void tick()
    {
        if ( this.alpha > this.life )
        {
            this.alpha -= this.life - 0.001F;
        } else if ( this.alpha > this.life )
        {
            this.alpha += this.life - 0.001F;
        } else
        {

            this.stdHandler.removeEntity( this );
        }
    }

    @Override
    public void render( Graphics2D g2 )
    {
        g2.setComposite( makeTransparent( this.alpha ) );
        if ( !this.isImage && this.shape != null )
        {
            g2.setColor( this.color );
            if ( this.shape.equalsIgnoreCase( "Circle" ) )
            {
                g2.fillOval( ( int ) getX(), ( int ) getY(), getWidth(), getHeight() );
            } else
            {
                g2.fillRect( ( int ) getX(), ( int ) getY(), getWidth(), getHeight() );
            }
        } else
        {
            g2.drawImage( this.obj.getCurrentSprite(), ( int ) getX(), ( int ) getY(), null );
        }
        g2.setComposite( makeTransparent( 1.0F ) );
    }

    private void checkNullShape()
    {
        if ( this.shape == null && !this.isImage )
        {
            System.err.println( "Shape is NULL in a Trail" );
            this.shape = "Square";
        }
    }

    public float getAlpha()
    {
        return this.alpha;
    }

    public void setAlpha( float alpha )
    {
        this.alpha = alpha;
    }

    public float getLife()
    {
        return this.life;
    }

    public void setLife( float life )
    {
        this.life = life;
    }

    public Color getColor()
    {
        return this.color;
    }

    public void setColor( Color color )
    {
        this.color = color;
    }

    public String getShape()
    {
        return this.shape;
    }

    public void setShape( String shape )
    {
        this.shape = shape;
    }

    public boolean isImage()
    {
        return this.isImage;
    }

    public void setImage( boolean isImage )
    {
        this.isImage = isImage;
    }
}
