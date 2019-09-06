package com.revivedstandards.model;

import com.revivedstandards.handlers.StandardHandler;
import java.awt.Color;

public abstract class StandardParticle extends StandardGameObject {

    public Color color = Color.red;
    
    protected StandardHandler handler;

    public StandardParticle( double x, double y, double life )
    {
        super( ( int ) x, ( int ) y, StandardID.Particle );

        life *= 1.0E9D;

        this.death = System.nanoTime() + ( long ) life;
    }

    public StandardParticle( double x, double y, StandardHandler handler )
    {
        super( ( int ) x, ( int ) y, StandardID.Particle );

        this.handler = handler;

        this.handler.addEntity( this );
    }

    public StandardParticle( double x, double y, double life, StandardHandler handler )
    {
        this( x, y, handler );
        life *= 1.0E9D;
        this.death = System.nanoTime() + ( long ) life;
    }

    public StandardParticle( double x, double y, double life, StandardHandler handler, Color c )
    {
        this( x, y, life, handler );
        this.color = c;
    }
}
