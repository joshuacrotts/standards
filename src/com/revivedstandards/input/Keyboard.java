package com.revivedstandards.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Write a description of class Keyboard here.
 * 
 * @author (Andrew Matzureff) 
 * @version (10/2/2016)
 */
public class Keyboard extends InputDevice implements KeyListener
{
    public static final int MAX_CHARS = 0x10000;
    
    public Keyboard()
    {
        super("Default Keyboard", MAX_CHARS);
    }
    public void keyPressed (KeyEvent e)
    {
        int code = e.getKeyCode();
        set(code, true);
    }
    public void keyReleased (KeyEvent e)
    {
        int code = e.getKeyCode();
        set(code, false);
    }
    public void keyTyped (KeyEvent e)
    {
    	//add key to log
    }
}