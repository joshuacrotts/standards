package com.revivedstandards.controller;

import com.revivedstandards.model.StandardGameObject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StandardAnimatorController {

    public static final byte PRIORITY_1ST = 127;
    public static final byte PRIORITY_2ND = 63;
    public static final byte PRIORITY_3RD = 0;
    public static final byte PRIORITY_4TH = -64;
    public static final byte PRIORITY_5TH = -128;
    public ArrayList<BufferedImage> images;
    private byte priority;
    private long counter;
    private long delay;
    private long frame;
    private long time;
    private StandardGameObject object;
    private boolean animating;

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
        if ( this.object.activeAnimation != null && ( ( this.object.activeAnimation.priority > this.priority && this.object.activeAnimation.animating ) || !this.animating ) )
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
        if ( this.object.activeAnimation != null && ( ( this.object.activeAnimation.priority > this.priority && this.object.activeAnimation.animating ) || !this.animating ) )
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
