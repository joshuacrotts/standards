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

import com.revivedstandards.model.StandardGameObject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StandardAnimatorController {

    //
    //  List of available priorities for the animations
    //
    public static final byte PRIORITY_1ST = 127;
    public static final byte PRIORITY_2ND = 63;
    public static final byte PRIORITY_3RD = 0;
    public static final byte PRIORITY_4TH = -64;
    public static final byte PRIORITY_5TH = -128;
    
    //
    //  ArrayList of images
    //  
    public ArrayList<BufferedImage> images;
    
    //
    //  Debugging and time-stamp information for the relevant information
    //  
    private byte priority;
    private long counter;
    private long delay;
    private long frame;
    private long time;
    private boolean animating;

    //
    //  The StandardGameObject the animation is tied to
    //  
    private StandardGameObject object;

    public StandardAnimatorController( ArrayList<BufferedImage> images, double delay, StandardGameObject o, int priority )
    {
        this.priority = 0;
        this.counter = 0L;
        this.delay = 1L;
        this.frame = 0L;
        this.time = 0L;

        this.animating = true;

        this.images = images;
        this.delay = ( long ) ( delay * 1.0E9D );
        this.object = o;
    }

    public boolean animate()
    {
        if ( this.object.getAnimation() != null && ( ( this.object.getAnimation().priority > this.priority && this.object.getAnimation().animating ) || !this.animating ) )
        {
            return ( this.frame == ( this.images.size() - 1 ) );
        }
        long current = System.nanoTime();
        long interval = current - this.time;
        this.time = current;
        if ( interval - this.delay > 0L )
        {
            this.counter = 0L;
        } else
        {
            this.counter += interval;
        }
        this.frame = this.counter / this.delay % this.images.size();
        this.object.setCurrentSprite( ( BufferedImage ) this.images.get( ( int ) this.frame ) );
        return ( this.frame == ( this.images.size() - 1 ) );
    }

    public boolean animate( int x )
    {
        if ( this.object.getAnimation() != null && ( ( this.object.getAnimation().priority > this.priority && this.object.getAnimation().animating ) || !this.animating ) )
        {
            return ( this.frame == ( this.images.size() - 1 ) );
        }
        long current = System.nanoTime();
        long interval = current - this.time;
        this.time = current;
        if ( interval - this.delay > 0L )
        {
            this.counter = 0L;
        } else
        {
            this.counter += interval;
        }
        this.frame = this.counter / this.delay % this.images.size();
        this.object.setCurrentSprite( ( BufferedImage ) this.images.get( ( int ) this.frame ) );
        return ( this.frame == x );
    }

    public ArrayList<BufferedImage> getImages()
    {
        return this.images;
    }

    public void setImages( ArrayList<BufferedImage> images )
    {
        this.images = images;
    }

    public void reset()
    {
        this.counter = 0L;
        this.frame = 0L;
        this.time = System.nanoTime();
    }

    public long getDelay()
    {
        return this.delay;
    }

    public void setDelay( double delay )
    {
        this.delay = ( long ) ( delay * 1.0E9D );
    }

    public long getFrame()
    {
        return this.frame;
    }

    public void setFrame( int frame )
    {
        this.counter = this.delay * frame;
        this.frame = frame;
        this.time = System.nanoTime();
    }

    public boolean isAnimating()
    {
        return this.animating;
    }

    public void setAnimating( boolean animating )
    {
        this.animating = animating;
    }
}
