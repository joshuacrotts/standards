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
 * and we use John Carmack's fast inverse square root function.
 * ===========================================================================
 */
package com.revivedstandards.test;

import com.revivedstandards.test.objects.BrickGameObject;
import com.revivedstandards.test.objects.TriangleGameObject;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.model.StandardLevel;

public class FollowTheMouseGameTest extends StandardGame
{

    private final TriangleGameObject tri;       //Player
    private final StandardCamera sc;            //Camera
    private final StandardCollisionHandler sch; //Collision handler
    private final StandardLevel level;          //Level
    private final UserInterface userInterface;  //User interface screen
    private GameState gameState;                //Current Game State (running/paused)

    public FollowTheMouseGameTest ()
    {
        super( 1280, 720, "Game Test" );

        //  Initializes the audio control buffer
        StandardAudioController.init( 16 );

        //  Create a new collision handler
        this.sch = new StandardCollisionHandler( null );

        //  Instantiates a new TGO (the player)
        this.tri = new TriangleGameObject( this, this.sch, 200, 200, StandardID.Player );

        //  Instantiate the camera
        this.sc = new StandardCamera( this, this.tri, 1, this.getGameWidth(), this.getGameHeight() );

        //  Sets the player's camera to the global camera
        this.tri.setCamera( this.sc );
        //  Sets the collision handler's camera to the global camera
        this.sch.setCamera( this.sc );

        //  Add the player to the collision handler
        this.sch.addEntity( this.tri );

        //  Instantiates the level
        this.level = new SpaceLevel( tri );

        //  Instantiates the UI
        this.userInterface = new UserInterface( this, this.tri, this.sc );

        //  Spawn bricks
        this.spawnBricks( 10, 64 );

        for ( int i = 0 ; i < 10 ; i++ )
        {
            StandardAudioController.load( "src/res/audio/sfx/laser_sfx.wav" );
            StandardAudioController.load( "src/res/audio/sfx/damage_0.wav" );
            StandardAudioController.load( "src/res/audio/sfx/damage_1.wav" );
            StandardAudioController.load( "src/res/audio/sfx/damage_2.wav" );
        }

        this.gameState = GameState.RUNNING;
    }

    @Override
    public void tick ()
    {
        // If the game is running (ie not paused), we can update
        // the logic.
        if ( this.gameState == GameState.RUNNING )
        {
            StandardHandler.Object( this.sc );
            this.level.tick();
            this.userInterface.tick();
            StandardHandler.Handler( this.sch );
        }
    }

    @Override
    public void render ()
    {
        // Render the level background first
        this.level.render( StandardDraw.Renderer );
        // Then render the camera
        StandardDraw.Object( this.sc );
        // Then render all game objects
        StandardDraw.Handler( this.sch );
        // Lastly, render UI elements so they are in front of everything else
        this.userInterface.render( StandardDraw.Renderer );
    }

    /**
     * Spawns n bricks at dimension dim (square bricks)
     *
     * @param n
     * @param dim
     */
    private void spawnBricks ( int n, int dim )
    {
        //Horizontal top & bottom rows
        for ( int x = 100 ; x <= n * 64 ; x += dim )
        {
            this.sch.addEntity( new BrickGameObject( this, this.sch, x, 64, dim, StandardDraw.ORANGE ) );
            this.sch.addEntity( new BrickGameObject( this, this.sch, x, n * 64, dim, StandardDraw.ORANGE ) );
        }

        //Vertical left & right row
        for ( int y = 64 ; y <= n * 64 ; y += dim )
        {
            this.sch.addEntity( new BrickGameObject( this, this.sch, 100, y, dim, StandardDraw.ORANGE ) );
            this.sch.addEntity( new BrickGameObject( this, this.sch, n * 64, y, dim, StandardDraw.ORANGE ) );
        }
    }

    public GameState getGameState ()
    {
        return this.gameState;
    }

    public void setGameState ( GameState gs )
    {
        this.gameState = gs;
    }

    public static void main ( String[] args )
    {
        FollowTheMouseGameTest gameTest = new FollowTheMouseGameTest();
        gameTest.startGame();
    }
}
