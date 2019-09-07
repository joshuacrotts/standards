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

package com.revivedstandards.test;

import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.input.Mouse;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.model.StandardLevel;

public class FollowTheMouseGameTest extends StandardGame
{
    private final TriangleGameObject       tri; //Player    
    private final StandardCamera           sc;  //Camera
    private final StandardCollisionHandler sch; //Collision handler
    private final StandardLevel          level; //Level
    
    public FollowTheMouseGameTest()
    {
        super( 800, 600, "Game Test" );
        
        //  Instantiate the mouse and add it as a mouse listener to the game
        //  so we can track its location for the TriangleGameObject
        Mouse mouse = new Mouse();
        super.setMouse( mouse );
        super.addMouseMotionListener( mouse );
        
        //  Instantiates a new TGO (the player)
        this.tri = new TriangleGameObject( this, 200, 200, StandardID.Player );
        
        //  Create a new collision handler
        this.sch = new StandardCollisionHandler( null );
        
        //  Instantiate the camera
        this.sc = new StandardCamera( this.tri, 1, 800, 600 );
        this.sch.stdCamera = this.sc;
        
        //  Add the player to the collision handler
        this.sch.addEntity( this.tri );
        
        this.level = new SpaceLevel( tri );
        
        for ( int y = 64; y <= 640; y += 34 )
        {
            this.sch.addEntity( new BrickGameObject( this, 100, y ) );
        }
    }

    @Override
    public void tick ()
    {
        StandardHandler.Handler( sch );
        sc.tick();
    }

    @Override
    public void render ()
    {
        this.level.render( StandardDraw.Renderer );
        StandardDraw.Object( this.sc );
        StandardDraw.Handler( this.sch );

    }
    
    public static void main ( String[] args )
    {
        FollowTheMouseGameTest gameTest = new FollowTheMouseGameTest();
        gameTest.StartGame();
    }
}
