package com.revivedstandards.main;

import com.revivedstandards.commands.Command;
import com.revivedstandards.input.Keyboard;
import com.revivedstandards.input.Mouse;
import com.revivedstandards.view.StandardWindowView;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.util.EventListener;

public abstract class StandardGame extends Canvas implements Runnable {

    private static final long serialVersionUID = -7267473597604645224L;
    public Keyboard keyboard;
    public Mouse mouse;
    public StandardWindowView window;
    private Thread thread;
    private boolean running;
    private int currentFPS;
    public boolean consoleFPS;
    public boolean titleFPS;
    public static BufferStrategy bufferStrategy = null;

    public StandardGame( int width, int height, String title )
    {
        this.window = null;
        this.thread = null;
        this.running = false;
        this.currentFPS = 0;
        this.consoleFPS = true;
        this.titleFPS = true;
        this.window = new StandardWindowView( width, height, title, this );

        createBufferStrategy( 3 );

        bufferStrategy = getBufferStrategy();

        this.mouse = new Mouse();
        this.keyboard = new Keyboard();

        addMouseListener( this.mouse );
        addMouseMotionListener( this.mouse );
        addKeyListener( this.keyboard );

        Command.init();
    }

    public StandardGame( int width, String title )
    {
        this.window = null;
        this.thread = null;
        this.running = false;
        this.currentFPS = 0;
        this.consoleFPS = true;
        this.titleFPS = true;
        this.window = new StandardWindowView( width, ( width / 16 * 9 ), title, this );

        createBufferStrategy( 3 );

        bufferStrategy = getBufferStrategy();

        this.mouse = new Mouse();
        this.keyboard = new Keyboard();

        addMouseListener( this.mouse );
        addMouseMotionListener( this.mouse );
        addKeyListener( this.keyboard );

        Command.init();
    }

    public void StartGame()
    {
        if ( this.running )
        {
            return;
        }
        this.thread = new Thread( this );
        this.thread.start();
        this.running = true;
    }

    public void StopGame()
    {
        if ( !this.running )
        {
            return;
        }
        try
        {
            this.thread.join();
        } catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
        this.running = false;
        System.exit( 0 );
    }

    public void run()
    {
        requestFocus();
        long lastTime = System.nanoTime();
        double ns = 1.6666666666666666E7D;
        double delta = 0.0D;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;
        while ( this.running )
        {

            boolean renderable = false;

            long now = System.nanoTime();
            delta += ( now - lastTime ) / ns;
            lastTime = now;

            while ( delta >= 1.0D )
            {
                Command.tick( this );

                tick();

                delta--;
                updates++;

                renderable = true;
            }

            frames++;
            bufferStrategy = getBufferStrategy();
            StandardDraw.Renderer = ( Graphics2D ) bufferStrategy.getDrawGraphics();

            StandardDraw.Renderer.setColor( Color.BLACK );
            StandardDraw.Renderer.fillRect( 0, 0, width(), height() );

            render();

            StandardDraw.Renderer.dispose();
            bufferStrategy.show();

            if ( System.currentTimeMillis() - timer > 1000L )
            {
                timer += 1000L;
                if ( this.titleFPS )
                {
                    this.window.setTitle( String.valueOf( this.window.getTitle() ) + " | " + updates + " ups, " + frames + " fps" );
                }
                if ( this.consoleFPS )
                {
                    System.out.println( String.valueOf( this.window.getTitle() ) + " | " + updates + " ups, " + frames + " fps" );
                }
                updates = 0;
                frames = 0;
            }
        }

        StopGame();
    }

    public abstract void tick();

    public abstract void render();

    public StandardGame getGame()
    {
        return this;
    }

    public void addListener( EventListener listener )
    {
        if ( listener instanceof KeyListener )
        {
            addKeyListener( ( KeyListener ) listener );
        }
        if ( listener instanceof MouseListener )
        {
            addMouseListener( ( MouseListener ) listener );
        }
        if ( listener instanceof MouseMotionListener )
        {
            addMouseMotionListener( ( MouseMotionListener ) listener );
        }
        if ( listener instanceof MouseWheelListener )
        {
            addMouseWheelListener( ( MouseWheelListener ) listener );
        }
    }

    public int getFPS()
    {
        return this.currentFPS;
    }

    public int width()
    {
        return this.window.width();
    }

    public int height()
    {
        return this.window.height();
    }

    public void framesToConsole( boolean print )
    {
        this.consoleFPS = print;
    }

    public void framesToTitle( boolean print )
    {
        this.titleFPS = print;
    }

    public StandardWindowView getWindow()
    {
        return this.window;
    }

    public Keyboard getKeyboard()
    {
        return this.keyboard;
    }

    public void setKeyboard( Keyboard keyboard )
    {
        this.keyboard = keyboard;
    }

    public Mouse getMouse()
    {
        return this.mouse;
    }

    public void setMouse( Mouse mouse )
    {
        this.mouse = mouse;
    }
}
