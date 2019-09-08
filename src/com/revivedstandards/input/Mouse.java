package com.revivedstandards.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Write a description of class Mouse here.
 *
 * @author (Andrew Matzureff)
 * @version (10/3/2016)
 */
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
    public int getMouseX()
    {
        return this.x;
    }
    
    /**
     * Returns the vertical y position of the event relative to the frame.
     * @return 
     */
    public int getMouseY()
    {
        return this.y;
    }
    
    /**
     * Returns the absolute horizontal x position of the mouse.
     * 
     * @return 
     */
    public int getAbsoluteMouseX()
    {
        return this.ax;
    }
    
    /**
     * Returns the absolute vertical y position of the mouse.
     * @return 
     */
    public int getAbsoluteMouseY()
    {
        return this.ay;
    }
}
