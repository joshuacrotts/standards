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
package com.revivedstandards.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard extends InputDevice implements KeyListener
{

    public static final int MAX_CHARS = 0x10000;

    public Keyboard ()
    {
        super( "Default Keyboard", MAX_CHARS );
    }

    @Override
    public void keyPressed ( KeyEvent e )
    {
        int code = e.getKeyCode();
        set( code, true );
    }

    @Override
    public void keyReleased ( KeyEvent e )
    {
        int code = e.getKeyCode();
        set( code, false );
    }

    @Override
    public void keyTyped ( KeyEvent e )
    {
        //add key to log
    }
}
