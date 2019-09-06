package com.revivedstandards.main;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;

public class StandardCamera
        extends StandardGameObject {

    public StandardGameObject subject;
    public double snap = 1.0D;
    public int vpw = 0, vph = 0, maxX, maxY = this.maxX = Integer.MAX_VALUE, minX, minY = this.minX = Integer.MIN_VALUE;

    public StandardCamera( StandardGameObject sgo, double snap, int vpw, int vph )
    {
        super( sgo.x, sgo.y, StandardID.Camera );
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

    public void tick()
    {
        tickX();
        tickY();
    }

    public void render( Graphics2D g2 )
    {
        g2.translate( -this.x + this.vpw, -this.y + this.vph );
    }

    public void tickX()
    {
        double location = this.subject.x;
        if ( location > this.maxX )
        {
            location = this.maxX;
        } else if ( location < this.minX )
        {
            location = this.minX;
        }
        this.velX = ( location - this.x ) * this.snap;
        this.x += this.velX;
    }

    public void tickY()
    {
        double location = this.subject.y;
        if ( location > this.maxY )
        {
            location = this.maxY;
        } else if ( location < this.minY )
        {
            location = this.minY;
        }
        this.velY = ( location - this.y ) * this.snap;
        this.y += this.velY;
    }
}
