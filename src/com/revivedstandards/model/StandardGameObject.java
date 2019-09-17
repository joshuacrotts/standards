/*
 * ===========================================================================
 * Standards Java Game Library Source Code
 * Copyright (C) 2017-2019 Joshua Crotts & Andrew Matzureff
 * Standards is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Standards Source Code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Standards Source Code. If not, see <http://www.gnu.org/licenses/>.
 *
 * Standards is the long-overdue update to the everlasting Standards 2.0 library
 * Andrew Matzureff and I created two years ago. I am including it in this project
 * to simplify the rendering and logic pipeline, but with a focus on the MVC
 * paradigm.
 *
 * We connect to the Apache FastMath API for some of our trigonometric functions,
 * and we use John Carmack's fast inverse square root function. Lastly, for
 * StandardAudio, we use the javax.sound (Trail's Sound) Oracle API.
 * ===========================================================================
 */
package com.revivedstandards.model;

import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class StandardGameObject implements Renderable, Updatable
{

    //
    //  X and Y positions
    //
    private double x = 0;
    private double y = 0;

    //
    //  X Velocity and Y Velocity
    //
    private double velX;
    private double velY;

    //
    //  Object dimensions - these can either be preset by the user,
    //  or the library can automatically set these to the size of the
    //  sprite the user chooses.
    //
    private int width;
    private int height;

    //
    //  States for the object; they can either be alive or dead, and they
    //  can have a timer to determine when they should be removed from
    //  the handler.
    //
    private boolean alive = true;
    private boolean interactable = false;
    private long death = 0L;

    //
    //  ID to determine what type of object this is - mostly used in collision
    //  handler.
    //
    private StandardID id;

    //
    //  Animation controller for the SGO - this can be null if the user is
    //  only using G2 objects.
    //
    private StandardAnimatorController activeAnimation;

    //
    //  Image information - the file location, as well as the current sprite
    //  for the object.
    //
    private String fileLocation;
    private BufferedImage currentSprite;

    //
    //  Collision bounds for the object.
    //
    private Rectangle bounds;

    public StandardGameObject ()
    {
    }

    public StandardGameObject ( double x, double y, StandardID id )
    {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public StandardGameObject ( double x, double y, StandardID id, boolean interactable )
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.interactable = interactable;
    }

    public StandardGameObject ( double x, double y, int width, int height )
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public StandardGameObject ( double x, double y, int width, int height, StandardID id )
    {
        this( x, y, width, height );
        this.id = id;
    }

    public StandardGameObject ( double x, int y, int width, int height, StandardID id, boolean interactable )
    {
        this( x, y, width, height, id );
        this.interactable = interactable;
    }

    public StandardGameObject ( double x, double y, String fileLocation )
    {
        this.x = x;
        this.y = y;
        this.fileLocation = fileLocation;
        this.currentSprite = StdOps.loadImage( this.fileLocation );
        this.width = this.currentSprite.getWidth();
        this.height = this.currentSprite.getHeight();
    }

    public StandardGameObject ( double x, double y, String fileLocation, boolean interactable )
    {
        this( x, y, fileLocation );

        this.interactable = interactable;
    }

    public StandardGameObject ( double x, double y, String fileLocation, StandardID id )
    {
        this( x, y, fileLocation );
        this.id = id;
    }

    public StandardGameObject ( double x, double y, String fileLocation, StandardID id, boolean interactable )
    {
        this( x, y, fileLocation, id );

        this.interactable = interactable;
    }

    public StandardGameObject ( double x, double y, BufferedImage image )
    {
        this.x = x;
        this.y = y;
        this.currentSprite = image;
        this.width = this.currentSprite.getWidth();
        this.height = this.currentSprite.getHeight();
    }

    public StandardGameObject ( double x, double y, BufferedImage image, StandardID id )
    {
        this( x, y, image );

        this.id = id;
    }

    public StandardGameObject ( double x, double y, BufferedImage image, StandardID id, boolean interactable )
    {
        this( x, y, image, id );

        this.interactable = interactable;
    }

    /**
     * Updates the position, state, actions, etc. of the StandardGameObject. Any
     * logic or physics should be done within the tick() method. Do not put
     * drawing functions in here; it will mess up the physics loop.
     *
     * This method must be implemented in any subclass of SGO.
     */
    @Override
    public abstract void tick ();

    /**
     * Draws the StandardGameObject to the screen however the user defines it
     * as. Do not update any game/object logic in here.
     *
     * This method must be implemented in any subclass of SGO.
     *
     * @param paramGraphics2D
     */
    @Override
    public abstract void render ( Graphics2D paramGraphics2D );

    /**
     * Short-hand way of typing x += velX; y += velY
     */
    public void updatePosition ()
    {
        this.setX( this.getX() + this.getVelX() );
        this.setY( this.getY() + this.getVelY() );
    }

    public StandardAnimatorController getAnimationController ()
    {
        return this.activeAnimation;
    }

    public void setAnimation ( StandardAnimatorController animation )
    {
        this.activeAnimation = animation;
    }

    public double getX ()
    {
        return this.x;
    }

    public void setX ( double x )
    {
        this.x = x;
    }

    public double getY ()
    {
        return this.y;
    }

    public void setY ( double y )
    {
        this.y = y;
    }

    public double getVelX ()
    {
        return this.velX;
    }

    public void setVelX ( double velX )
    {
        this.velX = velX;
    }

    public double getVelY ()
    {
        return this.velY;
    }

    public void setVelY ( double velY )
    {
        this.velY = velY;
    }

    public int getWidth ()
    {
        return this.width;
    }

    public void setWidth ( int width )
    {
        this.width = width;
    }

    public int getHeight ()
    {
        return this.height;
    }

    public void setHeight ( int height )
    {
        this.height = height;
    }

    public String getFileLocation ()
    {
        return this.fileLocation;
    }

    public void setFileLocation ( String fileLocation )
    {
        this.fileLocation = fileLocation;
    }

    public BufferedImage getCurrentSprite ()
    {
        return this.currentSprite;
    }

    public void setCurrentSprite ( BufferedImage currentSprite )
    {
        this.currentSprite = currentSprite;
    }

    public StandardID getId ()
    {
        return this.id;
    }

    public void setId ( StandardID id )
    {
        this.id = id;
    }

    public void setInteractable ( boolean interactable )
    {
        this.interactable = interactable;
    }

    public boolean isInteractable ()
    {
        return this.interactable;
    }

    public void setAlive ( boolean alive )
    {
        this.alive = alive;
    }

    public long getDeath ()
    {
        return this.death;
    }

    public void setDeath ( long death )
    {
        this.death = death;
    }

    public double getRestitution ()
    {
        return 1.0D;
    }

    public boolean isAlive ()
    {
        return this.alive;
    }

    public Rectangle getBounds ( int nX, int nY, int nW, int nH )
    {
        this.bounds = new Rectangle( ( int ) this.x + nX, ( int ) this.y + nY, this.width + nW, this.height + nH );
        return this.bounds;
    }

    /**
     * Returns a 2D rectangle around the StandardGameObject representing
     * its collision boundaries. Good for debugging purposes.
     * 
     * @return
     */
    public Rectangle getBounds ()
    {
        this.bounds = new Rectangle( ( int ) this.x, ( int ) this.y, this.width, this.height );
        return this.bounds;
    }

    public Rectangle getLeftBounds ()
    {
        return new Rectangle( ( int ) this.x, ( int ) this.y, 1, this.height );
    }

    public Rectangle getRightBounds ()
    {
        return new Rectangle( ( int ) this.x + this.width, ( int ) this.y, 1, this.height );
    }

    public Rectangle getTopBounds ()
    {
        return new Rectangle( ( int ) this.x, ( int ) this.y, this.width, 3 );
    }

    public Rectangle getBottomBounds ()
    {
        return new Rectangle( ( int ) this.x, ( int ) this.y + this.height, this.width, 1 );
    }
}
