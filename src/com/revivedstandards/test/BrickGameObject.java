/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revivedstandards.test;

import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Color;
import java.awt.Graphics2D;

public class BrickGameObject extends StandardGameObject
{

    private StandardGame sg;

    public BrickGameObject ( StandardGame sg, int x, int y )
    {
        super( x, y, StandardID.Obstacle );
        this.sg = sg;
        
        this.setWidth( 32 );
        this.setHeight( this.getWidth() );
    }

    @Override
    public void tick ()
    {

    }

    @Override
    public void render ( Graphics2D g2 )
    {
        g2.setColor( Color.BLUE );
        g2.fillRect( ( int ) this.getX(), ( int ) this.getY(), ( int ) this.getWidth(), ( int ) this.getHeight() );
    }
}
