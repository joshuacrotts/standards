package com.revivedstandards;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class will be similar to the Math class in terms of operations. Used for
 * arithmetic, but primarily as of now (06-07-2019), it's good for:
 *
 * - Random Numbers
 * - Determining Mouse Location (if the mouse coordinates are
 * over a rectangle (area) 
 * - Added the ability to load in specific fonts at a
 *   specific size. Pass in the String and the size, and it will be returned.
 * - Clamping a value to a specific range
 */
public abstract class StdOps
{
    /**
     * Returns a random integer between min and max.
     * 
     * @param min
     * @param max
     * @return random integer
     */
    public static int rand( int min, int max )
    {
        if( min >= max )
        {
            throw new IllegalArgumentException( " Max must be smaller than min ");
        }
        return ThreadLocalRandom.current().nextInt( min, max + 1 );
    }

    /**
     * Returns a random double between min and max.
     * @param min
     * @param max
     * @return 
     */
    public static double rand( double min, double max )
    {
        if( min >= max )
        {
            throw new IllegalArgumentException( " Max must be smaller than min ");
        }
        
        return ThreadLocalRandom.current().nextDouble( min, max + 1 );
    }
    
    /**
     * Returns true if the mouse coordinates are within a specified bounds/rectangle,
     * false otherwise,
     * @param mx - mouse x coordinate
     * @param my - mouse y coordinate
     * @param x  - x position of rectangle
     * @param y  - y position of rectangle
     * @param width - width of rectangle
     * @param height - height of rectangle
     * @return 
     */
    public static boolean mouseOver( int mx, int my, int x, int y, int width, int height )
    {
        return ( ( mx > x ) && ( mx < x + width ) ) && ( ( my > y ) && ( my < y + height ) );
    }
    
    /**
     * Clamps num between min and max.
     * 
     * @param num
     * @param min
     * @param max 
     */
    public static void clamp( int num, int min, int max )
    {
        if( num < min )
        {
            num = min;
        } 
        else if( num > max )
        {
            num = max;
        }
    }

    public static Font initFont( String path, float size )
    {
        Font f = null;

        try
        {
            f = Font.createFont( Font.TRUETYPE_FONT, new File( path ) ).deriveFont( size );
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont( Font.createFont( Font.TRUETYPE_FONT, new File( path ) ) );
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
        return f;
    }
}
