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
 * and we use John Carmack's fast inverse square root function. Lastly, for
 * StandardAudio, we use the javax.sound (Trail's Sound) Oracle API.
 * ===========================================================================
 */
package com.revivedstandards.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse extends InputDevice implements MouseListener, MouseMotionListener, MouseWheelListener
{

    private static final int MAX_CHARS = 0x10;
    private int x = -1;
    private int y = -1;
    private int ax = -1;
    private int ay = -1;

    public Mouse ()
    {
        super( "Default Mouse", MAX_CHARS );
    }

    private void update ( MouseEvent e )//would probably be inherited from Analogue Interface
    {
        x = e.getX();
        y = e.getY();
        ax = e.getXOnScreen();
        ay = e.getYOnScreen();
    }

    @Override
    public void mousePressed ( MouseEvent e )
    {
        update( e );
        int code = e.getButton();
        set( code, true );
    }

    @Override
    public void mouseReleased ( MouseEvent e )
    {
        update( e );
        int code = e.getButton();
        set( code, false );
    }

    @Override
    public void mouseClicked ( MouseEvent e )
    {
        update( e );
    }

    @Override
    public void mouseEntered ( MouseEvent e )
    {
        update( e );
    }

    @Override
    public void mouseExited ( MouseEvent e )
    {
        update( e );
    }

    @Override
    public void mouseMoved ( MouseEvent e )
    {
        update( e );
    }

    @Override
    public void mouseDragged ( MouseEvent e )
    {
        update( e );
    }

    @Override
    public void mouseWheelMoved ( MouseWheelEvent e )
    {
        // TODO Auto-generated method stub
    }

    /**
     * Returns the horizontal x position of the event relative to the frame.
     *
     * @return
     */
    public int getMouseX ()
    {
        return this.x;
    }

    /**
     * Returns the vertical y position of the event relative to the frame.
     *
     * @return
     */
    public int getMouseY ()
    {
        return this.y;
    }

    /**
     * Returns the absolute horizontal x position of the mouse.
     *
     * @return
     */
    public int getAbsoluteMouseX ()
    {
        return this.ax;
    }

    /**
     * Returns the absolute vertical y position of the mouse.
     *
     * @return
     */
    public int getAbsoluteMouseY ()
    {
        return this.ay;
    }
}
