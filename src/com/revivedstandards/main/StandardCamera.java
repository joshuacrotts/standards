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
and we use John Carmack's fast inverse square root function. Lastly, for 
StandardAudio, we use the javax.sound (Trail's Sound) Oracle API.
===========================================================================
 */
package com.revivedstandards.main;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;

/**
 * A camera will follow an object in the game. When using this, make absolute
 * sure you call the render() method BEFORE rendering the handlers.
 *
 * Moreover, make sure you tick the camera AFTER ticking the handlers. So,
 *
 * 1. Render() camera first 2. Tick() camera second.
 *
 * These are very strict contingencies, unfortunately.
 */
public class StandardCamera extends StandardGameObject
{

    private StandardGameObject subject;
    private StandardGame sg;

    private double snap = 1.0D;
    private int vpw = 0, vph = 0, maxX, maxY = this.maxX = Integer.MAX_VALUE, minX, minY = this.minX = Integer.MIN_VALUE;
    private int objectMinXBounds, objectMaxXBounds;
    private int objectMinYBounds, objectMaxYBounds;

    public StandardCamera ( StandardGame sg, StandardGameObject sgo, double snap, int vpw, int vph )
    {
        super( sgo.getX(), sgo.getY(), StandardID.Camera );
        this.vpw = vpw >> 1;
        this.vph = vph >> 1;
        this.subject = sgo;
        this.snap = snap;
        this.sg = sg;

        this.setObjectBounds();
    }

    public void restrict ( int maxx, int maxy, int minx, int miny )
    {
        this.maxX = maxx;
        this.maxY = maxy;
        this.minX = minx;
        this.minY = miny;
    }

    @Override
    public void tick ()
    {
        this.tickX();
        this.tickY();
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        g2.translate( -this.getX() + this.vpw, -this.getY() + this.vph );
    }

    public void tickX ()
    {
        double location = this.subject.getX();
        if ( location > this.maxX )
        {
            location = this.maxX;
        }
        else if ( location < this.minX )
        {
            location = this.minX;
        }
        this.setVelX( ( location - this.getX() ) * this.snap );
        this.setX( this.getX() + this.getVelX() );
    }

    public void tickY ()
    {
        double location = this.subject.getY();
        if ( location > this.maxY )
        {
            location = this.maxY;
        }
        else if ( location < this.minY )
        {
            location = this.minY;
        }
        this.setVelY( ( location - this.getY() ) * this.snap );
        this.setY( this.getY() + this.getVelY() );

        this.setObjectBounds();
    }

    private void setObjectBounds ()
    {
        this.objectMinXBounds = ( int ) ( this.subject.getX() - this.sg.getGameWidth() );
        this.objectMaxXBounds = ( int ) ( this.subject.getX() + this.sg.getGameWidth() );
        this.objectMinYBounds = ( int ) ( this.subject.getY() - this.sg.getGameHeight() );
        this.objectMaxYBounds = ( int ) ( this.subject.getY() + this.sg.getGameHeight() );
    }

    public boolean SGOInBounds ( StandardGameObject other )
    {
        return ( ( other.getX() > this.objectMinXBounds ) && ( other.getX() < this.objectMaxXBounds ) )
                && ( ( other.getY() > this.objectMinYBounds ) && ( other.getY() < this.objectMaxYBounds ) );
    }

    public StandardGameObject getSubject ()
    {
        return subject;
    }

    public void setSubject ( StandardGameObject subject )
    {
        this.subject = subject;
    }

    public double getSnap ()
    {
        return snap;
    }

    public void setSnap ( double snap )
    {
        this.snap = snap;
    }

    public int getVpw ()
    {
        return vpw;
    }

    public void setVpw ( int vpw )
    {
        this.vpw = vpw;
    }

    public int getVph ()
    {
        return vph;
    }

    public void setVph ( int vph )
    {
        this.vph = vph;
    }

    public int getMaxX ()
    {
        return maxX;
    }

    public void setMaxX ( int maxX )
    {
        this.maxX = maxX;
    }

    public int getMaxY ()
    {
        return maxY;
    }

    public void setMaxY ( int maxY )
    {
        this.maxY = maxY;
    }

    public int getMinX ()
    {
        return minX;
    }

    public void setMinX ( int minX )
    {
        this.minX = minX;
    }

    public int getMinY ()
    {
        return minY;
    }

    public void setMinY ( int minY )
    {
        this.minY = minY;
    }

}
