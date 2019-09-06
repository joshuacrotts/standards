package com.revivedstandards.model;

import com.revivedstandards.controller.StandardAnimatorController;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class StandardGameObject {

    public double x;
    public double y;
    public double velX;
    public double velY;
    public int width;
    public int height;
    public double health;

    private String fileLocation;

    public StandardID id;

    public BufferedImage currentSprite;

    public StandardAnimatorController activeAnimation;

    private boolean interactable = false;

    public long death = 0L;

    public boolean alive = true;
    
    public StandardID ignore = StandardID.Object;

    private Rectangle bounds;

    public StandardGameObject()
    {
    }

    public StandardGameObject( double x, double y, StandardID id )
    {
        this.x = x;
        this.y = y;

        this.id = id;
    }

    public StandardGameObject( double x, double y, StandardID id, boolean interactable )
    {
        this.x = x;
        this.y = y;

        this.id = id;
        this.interactable = interactable;
    }

    public StandardGameObject( double x, double y, int width, int height )
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public StandardGameObject( double x, double y, int width, int height, StandardID id )
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public StandardGameObject( double x, int y, int width, int height, StandardID id, boolean interactable )
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.interactable = interactable;
    }

    public StandardGameObject( double x, double y, String fileLocation )
    {
        this.x = x;
        this.y = y;

        this.fileLocation = fileLocation;

        try
        {
            this.currentSprite = ImageIO.read( new File( this.fileLocation ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

        this.width = this.currentSprite.getWidth();
        this.height = this.currentSprite.getHeight();
    }

    public StandardGameObject( double x, double y, String fileLocation, boolean interactable )
    {
        this.x = x;
        this.y = y;

        this.fileLocation = fileLocation;

        try
        {
            this.currentSprite = ImageIO.read( new File( this.fileLocation ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

        this.width = this.currentSprite.getWidth();
        this.height = this.currentSprite.getHeight();

        this.interactable = interactable;
    }

    public StandardGameObject( double x, double y, String fileLocation, StandardID id )
    {
        this.x = x;
        this.y = y;

        this.fileLocation = fileLocation;

        try
        {
            this.currentSprite = ImageIO.read( new File( this.fileLocation ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

        this.width = this.currentSprite.getWidth();
        this.height = this.currentSprite.getHeight();
        this.id = id;
    }

    public StandardGameObject( double x, double y, String fileLocation, StandardID id, boolean interactable )
    {
        this.x = x;
        this.y = y;

        this.fileLocation = fileLocation;

        try
        {
            this.currentSprite = ImageIO.read( new File( this.fileLocation ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

        this.width = this.currentSprite.getWidth();
        this.height = this.currentSprite.getHeight();
        this.id = id;

        this.interactable = interactable;
    }

    public StandardGameObject( double x, double y, BufferedImage image )
    {
        this.x = x;
        this.y = y;
        this.currentSprite = image;

        this.width = this.currentSprite.getWidth();
        this.height = this.currentSprite.getHeight();
    }

    public StandardGameObject( double x, double y, BufferedImage image, StandardID id )
    {
        this.x = x;
        this.y = y;
        this.currentSprite = image;

        this.width = this.currentSprite.getWidth();
        this.height = this.currentSprite.getHeight();
        this.id = id;
    }

    public StandardGameObject( double x, double y, BufferedImage image, StandardID id, boolean interactable )
    {
        this.x = x;
        this.y = y;
        this.currentSprite = image;

        this.width = this.currentSprite.getWidth();
        this.height = this.currentSprite.getHeight();
        this.id = id;

        this.interactable = interactable;
    }

    public abstract void tick();

    public abstract void render( Graphics2D paramGraphics2D );

    public void attack()
    {
    }

    public void collide( StandardGameObject sgo )
    {
        if ( sgo.getId() == StandardID.Projectile )
        {
            sgo.collide( this );
        }
    }

    public double getX()
    {
        return this.x;
    }

    public void setX( double x )
    {
        this.x = x;
    }

    public double getY()
    {
        return this.y;
    }

    public void setY( double y )
    {
        this.y = y;
    }

    public double getVelX()
    {
        return this.velX;
    }

    public void setVelX( double velX )
    {
        this.velX = velX;
    }

    public double getVelY()
    {
        return this.velY;
    }

    public void setVelY( double velY )
    {
        this.velY = velY;
    }

    public int getWidth()
    {
        return this.width;
    }

    public void setWidth( int width )
    {
        this.width = width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void setHeight( int height )
    {
        this.height = height;
    }

    public String getFileLocation()
    {
        return this.fileLocation;
    }

    public void setFileLocation( String fileLocation )
    {
        this.fileLocation = fileLocation;
    }

    public BufferedImage getCurrentSprite()
    {
        return this.currentSprite;
    }

    public void setCurrentSprite( BufferedImage currentSprite )
    {
        this.currentSprite = currentSprite;
    }

    public StandardID getId()
    {
        return this.id;
    }

    public void setId( StandardID id )
    {
        this.id = id;
    }

    public void setInteractable( boolean interactable )
    {
        this.interactable = interactable;
    }

    public boolean isInteractable()
    {
        return this.interactable;
    }

    public StandardAnimatorController getAnimation()
    {
        return null;
    }

    public void setAnimating( boolean bool )
    {
    }

    public Rectangle getBounds( int nX, int nY, int nW, int nH )
    {
        this.bounds = new Rectangle( ( int ) this.x + nX, ( int ) this.y + nY, this.width + nW, this.height + nH );
        return this.bounds;
    }

    public Rectangle getBounds()
    {
        this.bounds = new Rectangle( ( int ) this.x, ( int ) this.y, this.width, this.height );
        return this.bounds;
    }

    public Rectangle getLeftBounds()
    {
        return new Rectangle( ( int ) this.x, ( int ) this.y, 1, this.height );
    }

    public Rectangle getRightBounds()
    {
        return new Rectangle( ( int ) this.x + this.width, ( int ) this.y, 1, this.height );
    }

    public Rectangle getTopBounds()
    {
        return new Rectangle( ( int ) this.x, ( int ) this.y, this.width, 3 );
    }

    public Rectangle getBottomBounds()
    {
        return new Rectangle( ( int ) this.x, ( int ) this.y + this.height, this.width, 1 );
    }

    public boolean alive()
    {
        return this.alive;
    }

    public void setAlive( boolean alive )
    {
        this.alive = alive;
    }

    public long getDeath()
    {
        return this.death;
    }

    public void setDeath( long death )
    {
        this.death = death;
    }

    public double getRestitution()
    {
        return 1.0D;
    }

    public boolean isAlive()
    {
        return this.alive;
    }

    public double getHealth()
    {
        return this.health;
    }

    public void setHealth( int health )
    {
        this.health = health;
    }
}
