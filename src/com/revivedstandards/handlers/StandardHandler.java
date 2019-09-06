package com.revivedstandards.handlers;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class StandardHandler {

    public ArrayList<StandardGameObject> entities;
    public StandardCamera stdCamera;

    public StandardHandler( StandardCamera stdCamera )
    {
        this.entities = new ArrayList();
        this.stdCamera = stdCamera;
    }

    public StandardHandler()
    {
        this.entities = new ArrayList();
    }

    public static void Object( StandardGameObject obj )
    {
        obj.tick();
    }

    public static void Handler( StandardHandler handler )
    {
        handler.tick();
    }

    public static void Add( StandardGameObject obj, StandardHandler stdHandler, int n )
    {
        for ( int i = 0; i < n; i++ )
        {
            stdHandler.addEntity( obj );
        }
    }

    public void tick()
    {
        for ( int i = 0; i < this.entities.size(); i++ )
        {
            ( ( StandardGameObject ) this.entities.get( i ) ).tick();
        }
    }

    public void render( Graphics2D g2 )
    {
        int vpo = 300;
        Rectangle cam = null;
        if ( this.stdCamera != null )
        {
            cam = new Rectangle( ( int ) ( this.stdCamera.x - vpo - this.stdCamera.vpw ), ( int ) ( this.stdCamera.y - this.stdCamera.vph ), this.stdCamera.vpw * 2 + vpo * 2, this.stdCamera.vph * 2 );
        }
        for ( int i = 0; i < this.entities.size(); i++ )
        {

            StandardGameObject o = ( StandardGameObject ) this.entities.get( i );

            if ( cam != null && ( o.getBounds().intersects( cam ) || this.stdCamera == null ) )
            {
                o.render( g2 );
            }
        }
    }

    public void stdRender( Graphics2D g2 )
    {
        for ( StandardGameObject entity : this.entities )
        {
            entity.render( g2 );
        }
    }

    public void addEntity( StandardGameObject obj )
    {
        this.entities.add( obj );
    }

    public void removeEntity( StandardGameObject obj )
    {
        this.entities.remove( obj );
    }

    public void clearEntities()
    {
        for ( int i = 0; i < this.entities.size(); i++ )
        {

            if ( ( ( StandardGameObject ) this.entities.get( i ) ).getId() != StandardID.Player )
            {
                this.entities.remove( i );
                i--;
            }
        }
    }

    public void clearAllEntities()
    {
        this.entities.clear();
    }

    public void sort()
    {
        for ( int i = 0; i < this.entities.size(); i++ )
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

    private boolean validCollison( StandardGameObject obj2 )
    {
        return ( ( obj2.getId() == StandardID.Block || obj2.getId() == StandardID.Brick
                || obj2.getId() == StandardID.Obstacle || obj2.getId() == StandardID.NPC
                || obj2.getId() == StandardID.Powerup ) && obj2.getId() != StandardID.Player
                && obj2.getId() != StandardID.Enemy );
    }

    public void checkCollisions()
    {
    }

    public int size()
    {
        return this.entities.size();
    }

    public StandardGameObject get( int i )
    {
        return ( StandardGameObject ) this.entities.get( i );
    }

    public ArrayList<StandardGameObject> getEntities()
    {
        return this.entities;
    }

    public void addCam( StandardCamera cam )
    {
        this.stdCamera = cam;
    }
}
