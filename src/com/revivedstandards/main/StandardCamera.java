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

package com.revivedstandards.main;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;

public class StandardCamera extends StandardGameObject {

    public StandardGameObject subject;
    public double snap = 1.0D;
    public int vpw = 0, vph = 0, maxX, maxY = this.maxX = Integer.MAX_VALUE, minX, minY = this.minX = Integer.MIN_VALUE;

    public StandardCamera( StandardGameObject sgo, double snap, int vpw, int vph )
    {
        super( sgo.getX(), sgo.getY(), StandardID.Camera );
        this.vpw = vpw >> 1;
        this.vph = vph >> 1;
        this.subject = sgo;
        this.snap = snap;
    }

    public void restrict( int maxx, int maxy, int minx, int miny )
    {
        this.maxX = maxx;
        this.maxY = maxy;
        this.minX = minx;
        this.minY = miny;
    }

    @Override
    public void tick()
    {
        this.tickX();
        this.tickY();
    }

    @Override
    public void render( Graphics2D g2 )
    {
        g2.translate( -this.getX() + this.vpw, -this.getY() + this.vph );
    }

    public void tickX()
    {
        double location = this.subject.getY();
        if ( location > this.maxX )
        {
            location = this.maxX;
        } else if ( location < this.minX )
        {
            location = this.minX;
        }
        this.setVelX( ( location - this.getX() ) * this.snap );
        this.setX( this.getX() + this.getVelX());
    }

    public void tickY()
    {
        double location = this.subject.getY();
        if ( location > this.maxY )
        {
            location = this.maxY;
        } else if ( location < this.minY )
        {
            location = this.minY;
        }
        this.setVelY( ( location - this.getY() ) * this.snap );
        this.setY( this.getY() + this.getVelY());
    }
}
