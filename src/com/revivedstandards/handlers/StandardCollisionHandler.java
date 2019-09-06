package com.revivedstandards.handlers;

import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import org.apache.commons.math3.util.FastMath;

public class StandardCollisionHandler
        extends StandardHandler {

    public StandardCollisionHandler( StandardCamera c )
    {
        this.stdCamera = c;
    }

    public void tick()
    {
        double[] norm = new double[2];
        int vpo = 300;
        Rectangle cam = new Rectangle( ( int ) ( this.stdCamera.x - vpo - this.stdCamera.vpw ), ( int ) ( this.stdCamera.y - this.stdCamera.vph ), this.stdCamera.vpw * 2 + vpo * 2, this.stdCamera.vph * 2 );
        for ( int i = 0; i < this.entities.size(); i++ )
        {

            StandardGameObject obj1 = ( StandardGameObject ) this.entities.get( i );
            if ( obj1.getBounds().intersects( cam ) )
            {

                if ( ( ( StandardGameObject ) this.entities.get( i ) ).getId() == StandardID.Player || ( ( StandardGameObject ) this.entities.get( i ) ).getId() == StandardID.Enemy || ( ( StandardGameObject ) this.entities.get( i ) ).getId() == StandardID.Projectile || ( ( StandardGameObject ) this.entities.get( i ) ).getId() == StandardID.Powerup )
                {
                    for ( int j = 0; j < this.entities.size(); j++ )
                    {

                        StandardGameObject obj2 = ( StandardGameObject ) this.entities.get( j );
                        norm[0] = 0.0D;
                        norm[1] = 0.0D;

                        if ( obj1 != obj2 && obj1.getId() != obj2.ignore && obj1.alive && obj2.alive )
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
                                double dot = obj1.velX * norm[0] + obj1.velY * norm[1];
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
                        continue;
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

        double w1 = r1.width;
        double h1 = r1.height;
        double w2 = r2.width;
        double h2 = r2.height;

        Rectangle2D.Double b1 = new Rectangle2D.Double( x1, y1, w1, h1 );
        Rectangle2D.Double b2 = new Rectangle2D.Double( x2, y2, w2, h2 );
        Rectangle2D.Double bx = new Rectangle2D.Double( x1, y1, w1, h1 );

        if ( !b1.intersects( b2 ) )
        {

            norm[0] = 0.0D;
            norm[1] = 0.0D;
            return;
        }
        bx.x -= r1.velX;
        b1.y -= r1.velY;
        if ( !bx.intersects( b2 ) )
        {

            norm[0] = ( double ) ( ( bx.x < b2.x ) ? -1 : true );
            norm[1] = 0.0D;
            if ( !b1.intersects( b2 ) )
            {
                norm[1] = ( double ) ( ( b1.y < b2.y ) ? -1 : true );
            }
            return;
        }
        if ( !b1.intersects( b2 ) )
        {

            norm[0] = 0.0D;
            norm[1] = ( double ) ( ( b1.y < b2.y ) ? -1 : true );
            return;
        }
    }

    public static void Handler( StandardCollisionHandler sh )
    {
        sh.tick();
    }
}
