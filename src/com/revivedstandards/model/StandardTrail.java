package com.revivedstandards.model;

import com.revivedstandards.handlers.StandardHandler;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class StandardTrail extends StandardGameObject {

    private float alpha = 1.0F;

    private float life;

    private Color color;

    private String shape;

    private StandardGameObject obj;

    private boolean isImage = false;

    private StandardHandler stdHandler;

    public StandardTrail( double x, double y, double width, double height, Color color, float life, StandardGameObject o, StandardHandler stdHandler, String shape, boolean isImage )
    {
        super( x, y, ( int ) width, ( int ) height );

        this.color = color;
        this.life = life;
        this.shape = shape;
        setId( StandardID.Trail );

        this.stdHandler = stdHandler;
        this.stdHandler.addEntity( this );

        this.isImage = isImage;
        this.obj = o;
        checkNullShape();
    }

    private AlphaComposite makeTransparent( float alpha )
    {
        int type = 3;
        return AlphaComposite.getInstance( type, alpha );
    }

    public void tick()
    {
        if ( this.alpha > this.life )
        {
            this.alpha -= this.life - 0.001F;
        } else if ( this.alpha > this.life )
        {
            this.alpha += this.life - 0.001F;
        } else
        {

            this.stdHandler.removeEntity( this );
        }
    }

    public void render( Graphics2D g2 )
    {
        g2.setComposite( makeTransparent( this.alpha ) );
        if ( !this.isImage && this.shape != null )
        {
            g2.setColor( this.color );
            if ( this.shape.equalsIgnoreCase( "Circle" ) )
            {
                g2.fillOval( ( int ) getX(), ( int ) getY(), getWidth(), getHeight() );
            } else
            {
                g2.fillRect( ( int ) getX(), ( int ) getY(), getWidth(), getHeight() );
            }
        } else
        {
            g2.drawImage( this.obj.getCurrentSprite(), ( int ) getX(), ( int ) getY(), null );
        }
        g2.setComposite( makeTransparent( 1.0F ) );
    }

    private void checkNullShape()
    {
        if ( this.shape == null && !this.isImage )
        {
            System.err.println( "Shape is NULL in a Trail" );
            this.shape = "Square";
        }
    }

    public float getAlpha()
    {
        return this.alpha;
    }

    public void setAlpha( float alpha )
    {
        this.alpha = alpha;
    }

    public float getLife()
    {
        return this.life;
    }

    public void setLife( float life )
    {
        this.life = life;
    }

    public Color getColor()
    {
        return this.color;
    }

    public void setColor( Color color )
    {
        this.color = color;
    }

    public String getShape()
    {
        return this.shape;
    }

    public void setShape( String shape )
    {
        this.shape = shape;
    }

    public boolean isImage()
    {
        return this.isImage;
    }

    public void setImage( boolean isImage )
    {
        this.isImage = isImage;
    }
}
