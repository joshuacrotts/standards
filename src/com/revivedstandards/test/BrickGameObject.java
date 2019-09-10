package com.revivedstandards.test;

import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Color;
import java.awt.Graphics2D;

public class BrickGameObject extends StandardGameObject
{
    private final StandardGame sg;
    private final StandardCollisionHandler globalHandler;

    public BrickGameObject ( StandardGame sg, StandardCollisionHandler sch, int x, int y )
    {
        super( x, y, StandardID.Obstacle );
        this.sg = sg;
        this.globalHandler = sch;
        
        this.setWidth( 32 );
        this.setHeight( this.getWidth() );
        
        this.globalHandler.addCollider( StandardID.Obstacle );
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
        g2.setColor( Color.BLACK );
        g2.drawRect( ( int ) this.getX(), ( int ) this.getY(), ( int ) this.getWidth(), ( int ) this.getHeight() );
    }
}
