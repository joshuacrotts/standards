/*
===========================================================================
                   Standards Java Game Library Source Code
           Copyright (C) 2017-2019 Joshua Crotts & Andrew Matzureff 
Standards is free software: you can redistribute it and/or modify it under 
the terms of the GNU General Public License as published by the Free Software 
Foundation, either version 3 of the License, or (at your option) any later 
version.

Standards Source Code is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Standards Source Code. If not, see <http://www.gnu.org/licenses/>.

Standards is the long-overdue update to the everlasting Standards 2.0 library
Andrew Matzureff and I created two years ago. I am including it in this project
to simplify the rendering and logic pipeline, but with a focus on the MVC
paradigm.

We connect to the Apache FastMath API for some of our trigonometric functions,
and we use John Carmack's fast inverse square root function. Lastly, for 
StandardAudio, we use the javax.sound (Trail's Sound) Oracle API.
===========================================================================
 */

package com.revivedstandards.handlers;

import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import org.apache.commons.math3.util.FastMath;

/**
 * StandardCollisionHandler uses preset StandardIDs to determine what should
 * happen when a collision between two ID's occurs. Some StandardIDs are 
 * reserved for motionless/immovable objects, so when anything collides into
 * them, the colliding object stops moving. Otherwise, it functions as a 
 * normal StandardHandler.
 */

public class StandardCollisionHandler extends StandardHandler {

    public StandardCollisionHandler( StandardCamera c )
    {
        this.setCamera( c );
    }

    @Override
    public void tick()
    {
        double[] norm = new double[2];
        int vpo = 300;
        Rectangle cam = new Rectangle( ( int ) ( this.getCamera().getX() - vpo - this.getCamera().getVpw() ), 
                                       ( int ) ( this.getCamera().getY() - this.getCamera().getVph() ), 
                                        this.getCamera().getVpw() * 2 + vpo * 2, this.getCamera().getVph() * 2 );
        for ( int i = 0; i < this.getEntities().size(); i++ )
        {

            StandardGameObject obj1 = ( StandardGameObject ) this.getEntities().get( i );
            if ( obj1.getBounds().intersects( cam ) )
            {

                if ( ( ( StandardGameObject ) this.getEntities().get( i ) ).getId() == StandardID.Player || ( ( StandardGameObject ) this.getEntities().get( i ) ).getId() == StandardID.Enemy || ( ( StandardGameObject ) this.getEntities().get( i ) ).getId() == StandardID.Projectile || ( ( StandardGameObject ) this.getEntities().get( i ) ).getId() == StandardID.Powerup )
                {
                    for ( int j = 0; j < this.getEntities().size(); j++ )
                    {

                        StandardGameObject obj2 = ( StandardGameObject ) this.getEntities().get( j );
                        norm[0] = 0.0D;
                        norm[1] = 0.0D;

                        if ( obj1 != obj2 && obj1.getId() != StandardID.Ignore && obj1.isAlive() && obj2.isAlive() )
                        {
                            if ( ( obj2.getId() == StandardID.Block || obj2.getId() == StandardID.Brick
                                    || obj2.getId() == StandardID.Obstacle || obj2.getId() == StandardID.NPC
                                    || obj2.getId() == StandardID.Enemy || ( obj2.getId() == StandardID.Player
                                    && obj2.getId() != StandardID.Camera ) ) && ( obj1.getId() != StandardID.Projectile || obj2.getId() != StandardID.Projectile ) )
                            {
                                intersection( obj1, obj2, norm );
                                if ( norm[1] == -1.0D )
                                {
                                    //obj1.standing = true;
                                } else if ( norm[1] == 1.0D )
                                {
                                    //obj1.falling = true;
                                } else
                                {
                                    //obj1.falling = true;
                                    if ( norm[0] == 0.0D )
                                    {
                                        continue;
                                    }
                                }
                                double res = obj2.getRestitution();
                                double dot = obj1.getVelX() * norm[0] + obj1.getVelY() * norm[1];
                                if ( dot > -1.2D )
                                {
                                    //obj1.standing = true;
                                }

                                obj1.setVelX( obj1.getVelX() - norm[0] * dot * res );
                                obj1.setVelY( obj1.getVelY() - norm[1] * dot * res );
                                obj2.collide( obj1 );
                                obj1.collide( obj2 );
                            }
                        }
                    }
                }
                
                obj1.tick();
            }
        }
    }

    public static void intersection( StandardGameObject r1, StandardGameObject r2, double[] norm )
    {
        double x1 = r1.getX() - FastMath.signum( r1.getVelX() );
        double y1 = r1.getY() - FastMath.signum( r1.getVelY() );
        double x2 = r2.getX();
        double y2 = r2.getY();

        double w1 = r1.getWidth();
        double h1 = r1.getHeight();
        double w2 = r2.getWidth();
        double h2 = r2.getHeight();

        Rectangle2D.Double b1 = new Rectangle2D.Double( x1, y1, w1, h1 );
        Rectangle2D.Double b2 = new Rectangle2D.Double( x2, y2, w2, h2 );
        Rectangle2D.Double bx = new Rectangle2D.Double( x1, y1, w1, h1 );

        if ( !b1.intersects( b2 ) )
        {

            norm[0] = 0.0D;
            norm[1] = 0.0D;
            return;
        }
        bx.x -= r1.getVelX();
        b1.y -= r1.getVelY();
        if ( !bx.intersects( b2 ) )
        {

            norm[0] = ( ( bx.x < b2.x ) ? -1 : 1 );
            norm[1] = 0.0D;
            if ( !b1.intersects( b2 ) )
            {
                norm[1] = ( ( b1.y < b2.y ) ? -1 : 1 );
            }
            return;
        }
        if ( !b1.intersects( b2 ) )
        {

            norm[0] = 0.0D;
            norm[1] = ( ( b1.y < b2.y ) ? -1 : 1 );
        }
    }

    public static void Handler( StandardCollisionHandler sh )
    {
        sh.tick();
    }
}
