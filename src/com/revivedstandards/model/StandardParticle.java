/*
 * ===========================================================================
 * Standards Java Game Library Source Code
 * Copyright (C) 2017-2019 Joshua Crotts & Andrew Matzureff
 * Standards is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Standards Source Code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Standards Source Code. If not, see <http://www.gnu.org/licenses/>.
 *
 * Standards is the long-overdue update to the everlasting Standards 2.0 library
 * Andrew Matzureff and I created two years ago. I am including it in this project
 * to simplify the rendering and logic pipeline, but with a focus on the MVC
 * paradigm.
 *
 * We connect to the Apache FastMath API for some of our trigonometric functions,
 * and we use John Carmack's fast inverse square root function. Lastly, for
 * StandardAudio, we use the javax.sound (Trail's Sound) Oracle API.
 * ===========================================================================
 */
package com.revivedstandards.model;

import com.revivedstandards.handlers.StandardHandler;
import java.awt.Color;

public abstract class StandardParticle extends StandardGameObject
{
    private Color color = Color.BLACK;
    private StandardHandler handler;
    private double rotationAngle;

    public StandardParticle ( double x, double y, double life )
    {
        super( ( int ) x, ( int ) y, StandardID.Particle );
        life *= 1.0E9D;
        this.setDeath( System.nanoTime() + ( long ) life );
    }

    public StandardParticle ( double x, double y, StandardHandler handler )
    {
        super( ( int ) x, ( int ) y, StandardID.Particle );
        this.handler = handler;
    }

    public StandardParticle ( double x, double y, double life, StandardHandler handler )
    {
        this( x, y, handler );
        life *= 1.0E9D;
        this.setDeath( System.nanoTime() + ( long ) life );
    }

    public StandardParticle ( double x, double y, double life, StandardHandler handler, Color c )
    {
        this( x, y, life, handler );
        this.color = c;
    }

    public StandardParticle ( double x, double y, double life, StandardHandler handler, Color c, double rotationAngle )
    {
        this( x, y, life, handler, c );
        this.rotationAngle = rotationAngle;
    }

    public double getAngle ()
    {
        return this.rotationAngle;
    }

    public StandardHandler getHandler ()
    {
        return this.handler;
    }

    public void setColor ( Color c )
    {
        this.color = c;
    }

    public Color getColor ()
    {
        return this.color;
    }
}
