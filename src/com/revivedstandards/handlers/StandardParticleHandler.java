package com.revivedstandards.handlers;

import com.revivedstandards.model.StandardGameObject;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class StandardParticleHandler extends StandardHandler {

    public final int MAX_PARTICLES;
    public int dead;
    public int oldest;
    public int replace;

    public StandardParticleHandler( int max )
    {
        this.MAX_PARTICLES = max;
        this.dead = this.MAX_PARTICLES;
        this.oldest = 0;
        this.replace = this.MAX_PARTICLES;

        this.entities = new ArrayList( this.MAX_PARTICLES + 1 );
        for ( int i = 0; i < this.MAX_PARTICLES; i++ )
        {
            this.entities.add( null );
        }
    }

    public void tick()
    {
        long death = -1L;

        if ( this.dead < this.MAX_PARTICLES )
        {
            for ( int i = this.dead; i < this.MAX_PARTICLES; i++ )
            {

                StandardGameObject particle = ( StandardGameObject ) this.entities.get( i );

                if ( particle.alive() )
                {

                    particle.tick();
//                    if ( ( ( particle.death < death ) ? 1 : 0 ) ^ ( ( particle.death < 0L ) ? 1 : 0 ) ^ ( ( death < 0L ) ? 1 : 0 ) )
//                    {
//                        this.oldest = i;
//                        death = particle.death;
//                       
//                    }

                } else
                {

                    StandardGameObject swap = ( StandardGameObject ) this.entities.get( this.dead );
                    this.entities.set( i, swap );
                    this.entities.set( this.dead++, null );
                }
            }
        }
    }

    public void render( Graphics2D g2 )
    {
        for ( int i = 0; i < this.entities.size(); i++ )
        {

            StandardGameObject particle = ( StandardGameObject ) this.entities.get( i );

            if ( particle != null && particle.alive() )
            {
                particle.render( g2 );
            }
        }
    }

    public void addEntity( StandardGameObject particle )
    {
        if ( this.dead == 0 )
        {

            if ( this.replace == 0 )
            {
                this.replace = this.MAX_PARTICLES;
            }
            this.entities.set( --this.replace, particle );

            return;
        }
        this.entities.set( --this.dead, particle );
    }

    public void removeEntity( StandardGameObject obj )
    {
    }

    public int size()
    {
        return this.MAX_PARTICLES - this.dead;
    }
}
