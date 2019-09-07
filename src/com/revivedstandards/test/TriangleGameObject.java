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

    public TriangleGameObject ( StandardGame sg, int x, int y )
    {
        super( x, y, "src/res/img/triangle.png" );
        this.sg = sg;

        this.setWidth( this.getCurrentSprite().getWidth() );
        this.setHeight( this.getCurrentSprite().getHeight() );
    }

    @Override
    public void tick ()
    {
        double mx = this.sg.getMouse().x;
        double my = this.sg.getMouse().y;
        
        float xSign = ( float ) FastMath.signum( mx - this.getX() );
        
        double dx = FastMath.abs( mx - this.getX() );
        double dy = FastMath.abs( my - this.getY() );
 
        this.angle = ( float ) ( ( xSign ) * ( FastMath.atan( ( dx ) / ( dy ) ) ) );
        
        // If we're in Q1 (+x, -+y) or in Q2 (-x, +y)
        if ( ( mx > this.getX() && my > this.getY() ) ||
             ( mx < this.getX() && my > this.getY() ) )
        {
            this.angle = ( float ) ( ( FastMath.PI * 0.5 ) + ( FastMath.PI * 0.5 - angle ) );
        }
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        AffineTransform backup = g2.getTransform();
        AffineTransform rotation = new AffineTransform();

        rotation.rotate( 0, 0, 0 );

        g2.rotate( this.angle, this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2 );
        g2.drawImage( this.getCurrentSprite(), ( int ) this.getX(), ( int ) this.getY(), null );
        g2.setTransform( backup );
    }
}
