package com.revivedstandards;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * This is the main class encompassing the window, render loop, 
 * physics/tick loop, etc.
 * 
 */
public class StandardGame extends Canvas implements Runnable
{
    //
    //  Default window object
    //
    private JFrame frame    = null;
    
    //
    //  Determines if the thread is currently 
    //  running.
    //
    private boolean running = false;
    
    public StandardGame( int width, int height, String title )
    {
        //  Initialize window
        this.frame = new JFrame( title );
        this.frame.setMaximumSize( new Dimension( width, height ) );
        this.frame.setMinimumSize( new Dimension( width, height ) );
        this.frame.setPreferredSize( new Dimension( width, height ) );
        
        this.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.frame.setLocationRelativeTo( null );
        this.frame.add( this );
        this.frame.setVisible( true );
        
        this.start();
    }
    
    /**
     * 
     */
    public synchronized void start()
    {
        if( this.running )
        {
            return;
        }
        
        this.running = true;
        this.run();
    }
    
    /**
     * 
     */
    public synchronized void stop() 
    {
        if( !this.running )
        {
            return;
        }
        this.running = false;
    }
    
    /**
     * The update() function updates all game logic and physics
     * within the world. Rendering will take place in a separate
     * thread.
     */
    public void update()
    {
        
    }
    
    /**
     * 
     */
    @Override
    public void run()
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args)
    {
        StandardGame game = new StandardGame( 640, 480, "Test" );
    }
    
}
