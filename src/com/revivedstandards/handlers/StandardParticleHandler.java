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

import com.revivedstandards.model.StandardGameObject;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * StandardParticleHandler is, as the name suggests, a handler for
 * StandardParticle objects. There is a max amount of particles that a SPH can
 * expel (to avoid performance bottlenecks).
 *
 * Furthermore, to prevent having to dynamically shrink and resize the ArrayList
 * when particles die and respawn, SPH uses a custom algorithm to detect which
 * particles are dead/are oldest, and assigns new particles to these spots in
 * the ArrayList, as opposed to simply adding a new index, or removing one.
 */
public class StandardParticleHandler extends StandardHandler
{

    private final int MAX_PARTICLES;
    private int dead;
    private int oldest;
    private int replace;

    public StandardParticleHandler ( int max )
    {
        this.MAX_PARTICLES = max;
        this.dead = this.MAX_PARTICLES;
        this.oldest = 0;
        this.replace = this.MAX_PARTICLES;

        this.setEntities( new ArrayList( this.MAX_PARTICLES + 1 ) );
        for ( int i = 0 ; i < this.MAX_PARTICLES ; i++ )
        {
            this.getEntities().add( null );
        }
    }

    @Override
    public void tick ()
    {
        if ( this.dead < this.MAX_PARTICLES )
        {
            for ( int i = this.dead ; i < this.MAX_PARTICLES ; i++ )
            {
                StandardGameObject particle = ( StandardGameObject ) this.getEntities().get( i );

                if ( particle.isAlive() )
                {
                    particle.tick();
                }
                else
                {
                    StandardGameObject swap = ( StandardGameObject ) this.getEntities().get( this.dead );
                    this.getEntities().set( i, swap );
                    this.getEntities().set( this.dead++, null );
                }
            }
        }
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        for ( int i = 0 ; i < this.getEntities().size() ; i++ )
        {
            StandardGameObject particle = ( StandardGameObject ) this.getEntities().get( i );

            if ( particle != null && particle.isAlive() && this.getCamera().SGOInBounds( particle ) )
            {
                particle.render( g2 );
            }
        }
    }

    /**
     * Adds a particle to the Standard Particle Handler. This is a custom
     * algorithm for placing a particle in a spot that houses an already-dead
     * particle, to prevent having add and remove spots in the arraylist. It
     * speeds up the process tremendously.
     *
     * If you need to iterate over the SPH, start from MAX-PARTICLES - 1, to 0.
     * The first entity is inserted at the back of the list, and it works it way
     * to the front (all particles are marked as dead upon instantiation of the
     * handler).
     *
     * @param particle
     */
    @Override
    public void addEntity ( StandardGameObject particle )
    {
        if ( this.dead == 0 )
        {
            if ( this.replace == 0 )
            {
                this.replace = this.MAX_PARTICLES;
            }
            this.getEntities().set( --this.replace, particle );
            return;
        }
        this.getEntities().set( --this.dead, particle );
    }

    @Override
    public void removeEntity ( StandardGameObject obj )
    {
        super.removeEntity( obj );
    }

    @Override
    public int size ()
    {
        return this.MAX_PARTICLES - this.dead;
    }

    public int getOldestIndex ()
    {
        return this.oldest;
    }

    public int getDeadIndex ()
    {
        return this.dead;
    }

    public int getReplaceIndex ()
    {
        return this.replace;
    }

    public int getMaxParticles ()
    {
        return this.MAX_PARTICLES;
    }
}
