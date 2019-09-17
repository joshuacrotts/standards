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
package com.revivedstandards.handlers;

import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * The StandardHandler class houses all entities/objects within the game. As the
 * name implies, it "handles" all events, renders, and updates for every SGO
 * inside the handler. The SGO (or subclass of SGO) must have tick() and
 * render(G2D) implemented, or this will not work.
 */
public class StandardHandler implements Renderable, Updatable
{

    //
    //  ArrayList of StandardGameObjects
    //
    private ArrayList<StandardGameObject> entities;

    //
    //  StandardCamera for the StandardHandler
    //
    private StandardCamera                stdCamera;

    public StandardHandler ( StandardCamera stdCamera )
    {
        this.entities  = new ArrayList();
        this.stdCamera = stdCamera;
    }

    public StandardHandler ()
    {
        this.entities  = new ArrayList();
    }

    /**
     * Calls tick on the respective object.
     *
     * The purpose of this is to only update one object.
     *
     * @param obj
     */
    public static void Object ( StandardGameObject obj )
    {
        obj.tick();
    }

    /**
     * Calls tick() on the supplied handler.
     *
     * @param handler
     */
    public static void Handler ( StandardHandler handler )
    {
        handler.tick();
    }

    /**
     * Adds n StandardGameObject obj's to the StandardHandler stdHandler
     *
     * @param obj
     * @param stdHandler
     * @param n
     */
    public static void Add ( StandardGameObject obj, StandardHandler stdHandler, int n )
    {
        for ( int i = 0 ; i < n ; i++ )
        {
            stdHandler.addEntity( obj );
        }
    }

    @Override
    public void tick ()
    {
        for ( int i = 0 ; i < this.entities.size() ; i++ )
        {
            ( ( StandardGameObject ) this.entities.get( i ) ).tick();
        }
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        int vpo       = 300;
        Rectangle cam = null;

        if ( this.stdCamera != null )
        {
            cam = new Rectangle( ( int ) ( this.stdCamera.getX() - vpo - this.stdCamera.getVpw() ),
                                 ( int ) ( this.stdCamera.getY() - this.stdCamera.getVph() ),
                                           this.stdCamera.getVpw() * 2 + vpo * 2, this.stdCamera.getVph() * 2 );
        }

        for ( int i = 0 ; i < this.entities.size() ; i++ )
        {
            StandardGameObject o = ( StandardGameObject ) this.entities.get( i );

            if ( ( cam != null && ( o.getBounds().intersects( cam ) || this.stdCamera == null ) ) )
            {
                o.render( g2 );
            }
        }
    }

    /**
     * Performs a standard render on the SGOs in the handler without worrying
     * about translations for the camera.
     *
     * @param g2
     */
    public void stdRender ( Graphics2D g2 )
    {
        this.entities.forEach( ( entity ) ->
        {
            entity.render( g2 );
        } );
    }

    public void setEntities ( ArrayList<StandardGameObject> entities )
    {
        this.entities = entities;
    }

    public void addEntity ( StandardGameObject obj )
    {
        this.entities.add( obj );
    }

    public void removeEntity ( StandardGameObject obj )
    {
        this.entities.remove( obj );
    }

    public void clearEntities ()
    {
        for ( int i = 0 ; i < this.entities.size() ; i++ )
        {
            if ( ( ( StandardGameObject ) this.entities.get( i ) ).getId() != StandardID.Player )
            {
                this.entities.remove( i );
                i--;
            }
        }
    }

    public void clearAllEntities ()
    {
        this.entities.clear();
    }

    public void sort ()
    {
        for ( int i = 0 ; i < this.entities.size() ; i++ )
        {
            if ( ( ( StandardGameObject ) this.entities.get( i ) ).getId() == StandardID.Player )
            {
                this.entities.add( 0, ( StandardGameObject ) this.entities.get( i ) );
                removeEntity( ( StandardGameObject ) this.entities.get( i ) );
            }

            if ( ( ( StandardGameObject ) this.entities.get( i ) ).getId() == StandardID.Enemy )
            {
                this.entities.add( 1, ( StandardGameObject ) this.entities.get( i ) );
                removeEntity( ( StandardGameObject ) this.entities.get( i ) );
            }
        }
    }

    private boolean validCollison ( StandardGameObject obj2 )
    {
        return ( ( obj2.getId() == StandardID.Block || obj2.getId() == StandardID.Brick
                || obj2.getId() == StandardID.Obstacle || obj2.getId() == StandardID.NPC
                || obj2.getId() == StandardID.Powerup ) && obj2.getId() != StandardID.Player
                && obj2.getId() != StandardID.Enemy );
    }

    public void checkCollisions ()
    {
    }

    public int size ()
    {
        return this.entities.size();
    }

    public StandardGameObject get ( int i )
    {
        return ( StandardGameObject ) this.entities.get( i );
    }

    public ArrayList<StandardGameObject> getEntities ()
    {
        return this.entities;
    }

    public void setCamera ( StandardCamera cam )
    {
        this.stdCamera = cam;
    }

    public StandardCamera getCamera ()
    {
        return this.stdCamera;
    }
}
