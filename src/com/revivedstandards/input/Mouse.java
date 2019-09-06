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
    public static final int MAX_CHARS = 0x10;
    public int x = -1;
    public int y = -1;
    public int ax = -1;
    public int ay = -1;
    public Mouse()
    {
    	super("Default Mouse", MAX_CHARS);
    }
    private void update(MouseEvent e)//would probably be inherited from Analogue Interface
    {
        x = e.getX();
        y = e.getY();
        ax = e.getXOnScreen();
        ay = e.getYOnScreen();
    }
    public void mousePressed (MouseEvent e)
    {
        update(e);
        int code = e.getButton();
        set(code, true);
    }
    public void mouseReleased (MouseEvent e)
    {
        update(e);
        int code = e.getButton();
        set(code, false);
    }
    public void mouseClicked (MouseEvent e)
    {
        update(e);
    }
    public void mouseEntered (MouseEvent e)
    {
        update(e);
    }
    public void mouseExited (MouseEvent e)
    {
        update(e);
    }
    public void mouseMoved (MouseEvent e)
    {
        update(e);
    }
    public void mouseDragged (MouseEvent e)
    {
        update(e);
    }
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}
}