/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revivedstandards.test;

import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardGameObject;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author Joshua
 */
public class TriangleGameObject extends StandardGameObject
{
    private StandardGame sg;
    private float angle;
    
    public TriangleGameObject( StandardGame sg, int x, int y)
    {
        super(x, y, "src/res/img/triangle.png");
        this.sg = sg;
        
        this.setWidth( this.getCurrentSprite().getWidth() );
        this.setHeight ( this.getCurrentSprite().getHeight() );
    }
    
    public void tick()
    {
//        float sign = ( float ) FastMath.signum( this.sg.getMouse().x - this.getX() );
//        System.out.println( "Mouse x: " + this.sg.getMouse().x + "spritex:" + this.getX());
//        double dx = Math.abs( this.sg.getMouse().x - this.getX() );
//        double dy = Math.abs( this.sg.getMouse().y - this.getY() );
        
        //angle = ( float ) ( sign * FastMath.atan( dx / dy )) ;
        angle = ( float ) (Math.atan2( this.getY() - this.sg.getMouse().y, this.getX() - this.sg.getMouse().x ) );
    }
    
    public void render(Graphics2D g2)
    {
        AffineTransform backup = g2.getTransform();
        AffineTransform rotation = new AffineTransform();
        
        rotation.rotate( 0,0,0 );
        
        g2.rotate( this.angle, this.getX(), this.getY());
        g2.drawImage( this.getCurrentSprite(), ( int ) this.getX(),  ( int ) this.getY(), null );
        g2.setTransform( backup );
    }
}
