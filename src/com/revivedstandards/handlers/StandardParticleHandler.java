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
and we use John Carmack's fast inverse square root function.
===========================================================================
 */

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

                if ( particle.isAlive() )
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

    @Override
    public void render( Graphics2D g2 )
    {
        for ( int i = 0; i < this.entities.size(); i++ )
        {

            StandardGameObject particle = ( StandardGameObject ) this.entities.get( i );

            if ( particle != null && particle.isAlive() )
            {
                particle.render( g2 );
            }
        }
    }

    @Override
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

    @Override
    public void removeEntity( StandardGameObject obj )
    {
        super.removeEntity( obj );
    }

    @Override
    public int size()
    {
        return this.MAX_PARTICLES - this.dead;
    }
}
