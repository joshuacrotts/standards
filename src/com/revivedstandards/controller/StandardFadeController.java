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

package com.revivedstandards.controller;

import java.awt.Color;

/**
 * Instantiates a controller that continuously changes colors, ranging from
 * color1 to color2, with a translation time of alpha.
 */
public class StandardFadeController {

    private double time;
    private boolean firstColor;
    
    private final double alpha;
    private final Color color1;
    private final Color color2;

    public StandardFadeController( Color c1, Color c2, double alpha )
    {
        this.time = 0.0F;
        this.firstColor = true;

        this.color1 = c1;
        this.color2 = c2;

        this.alpha = alpha;
    }


    public Color combine()
    {
        if ( this.time <= 1.0F && this.firstColor )
        {
            this.time = ( float ) ( this.time + this.alpha );
        } else
        {

            this.firstColor = false;
        }
        if ( this.time >= 0.0F && !this.firstColor )
        {
            this.time = ( float ) ( this.time - this.alpha );
        } else
        {
            this.firstColor = true;
        }

        short r = ( short ) ( int ) ( this.time * this.color2.getRed()   + ( 1.0F - this.time ) * this.color1.getRed()   );
        short g = ( short ) ( int ) ( this.time * this.color2.getGreen() + ( 1.0F - this.time ) * this.color1.getGreen() );
        short b = ( short ) ( int ) ( this.time * this.color2.getBlue()  + ( 1.0F - this.time ) * this.color1.getBlue()  );

        if ( r > 255 )
        {
            r = 255;
        }
        if ( g > 255 )
        {
            g = 255;
        }
        if ( b > 255 )
        {
            b = 255;
        }

        if ( r < 0 )
        {
            r = 0;
        }
        if ( g < 0 )
        {
            g = 0;
        }
        if ( b < 0 )
        {
            b = 0;
        }

        return new Color( r, g, b );
    }
}
