package com.revivedstandards.main;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;

/**
 * A camera will follow an object in the game. When using this, make absolute sure
 * you call the render() method BEFORE rendering the handlers.
 * 
 * Moreover, make sure you tick the camera AFTER ticking the handlers. So,
 * 
 * 1. Render() camera first
 * 2. Tick() camera second.
 * 
 * These are very strict contingencies, unfortunately.
 */
public class StandardCamera extends StandardGameObject
{
    private StandardGameObject subject;
    private double snap = 1.0D;
    private int vpw = 0, vph = 0, maxX, maxY = this.maxX = Integer.MAX_VALUE, minX, minY = this.minX = Integer.MIN_VALUE;

    public StandardCamera ( StandardGameObject sgo, double snap, int vpw, int vph )
    {
        super( sgo.getX(), sgo.getY(), StandardID.Camera );
        this.vpw = vpw >> 1;
        this.vph = vph >> 1;
        this.subject = sgo;
        this.snap = snap;
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
