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

import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.input.Mouse;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import java.awt.Color;
import java.awt.event.MouseMotionListener;

public class GameTest extends StandardGame
{
    private TriangleGameObject tri;
    
    public GameTest()
    {
        super( 800, 600, "Game Test" );
        
        Mouse mouse = new Mouse();
        this.setMouse( mouse );
        this.addListener( (MouseMotionListener) mouse );
        
        tri = new TriangleGameObject( this, 200, 200 );
    }

    @Override
    public void tick ()
    {
        StandardHandler.Object( tri );
    }

    @Override
    public void render ()
    {
        StandardDraw.Renderer.setColor( Color.RED );
        StandardDraw.Renderer.drawString( "Fuck!", 30, 30 );
        StandardDraw.Object( tri );
    }
    
    public static void main ( String[] args )
    {
        GameTest gameTest = new GameTest();
        gameTest.StartGame();
    }
}
