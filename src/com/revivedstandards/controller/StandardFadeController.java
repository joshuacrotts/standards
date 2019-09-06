package com.revivedstandards.controller;

import java.awt.Color;

public class StandardFadeController {

    private float time;
    private boolean firstColor;

    public StandardFadeController( Color c1, Color c2, double alpha )
    {
        this.time = 0.0F;
        this.firstColor = true;

        this.color1 = c1;
        this.color2 = c2;

        this.alpha = alpha;
    }
    private Color color1;
    private Color color2;
    private double alpha;

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

        short r = ( short ) ( int ) ( this.time * this.color2.getRed() + ( 1.0F - this.time ) * this.color1.getRed() );
        short g = ( short ) ( int ) ( this.time * this.color2.getGreen() + ( 1.0F - this.time ) * this.color1.getGreen() );
        short b = ( short ) ( int ) ( this.time * this.color2.getBlue() + ( 1.0F - this.time ) * this.color1.getBlue() );

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
