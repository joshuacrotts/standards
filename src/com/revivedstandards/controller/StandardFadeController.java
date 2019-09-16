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
package com.revivedstandards.controller;

import com.revivedstandards.util.StdOps;
import java.awt.Color;

/**
 * Instantiates a controller that continuously changes colors, ranging from
 * color1 to color2, with a translation time of alpha.
 */
public class StandardFadeController
{

    private double time;
    private boolean firstColor;

    private final double ALPHA;
    private final Color COLOR_ONE;
    private final Color COLOR_TWO;

    public StandardFadeController ( Color c1, Color c2, double alpha )
    {
        this.time = 0.0F;
        this.firstColor = true;

        this.COLOR_ONE = c1;
        this.COLOR_TWO = c2;

        this.ALPHA = alpha;
    }

    public Color combine ()
    {
        if ( this.time <= 1.0F && this.firstColor )
        {
            this.time = ( float ) ( this.time + this.ALPHA );
        }
        else
        {

            this.firstColor = false;
        }
        if ( this.time >= 0.0F && !this.firstColor )
        {
            this.time = ( float ) ( this.time - this.ALPHA );
        }
        else
        {
            this.firstColor = true;
        }

        int r = ( int ) ( this.time * this.COLOR_TWO.getRed() + ( 1.0F - this.time ) * this.COLOR_ONE.getRed() );
        int g = ( int ) ( this.time * this.COLOR_TWO.getGreen() + ( 1.0F - this.time ) * this.COLOR_ONE.getGreen() );
        int b = ( int ) ( this.time * this.COLOR_TWO.getBlue() + ( 1.0F - this.time ) * this.COLOR_ONE.getBlue() );

        r = StdOps.clamp( r, 0, 255 );
        g = StdOps.clamp( g, 0, 255 );
        b = StdOps.clamp( b, 0, 255 );

        return new Color( r, g, b );
    }
}
