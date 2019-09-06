package com.revivedstandards.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener, InputDevice {

    public int x = 0;
    public int y = 0;

    public final byte[] _Buttons = new byte[16];

    public int getDeviceMask()
    {
        return Integer.MIN_VALUE;
    }

    public byte[] getBytes()
    {
        return this._Buttons;
    }

    public void set( int k, int v )
    {
        if ( k >= 0 && k < this._Buttons.length )
        {
            this._Buttons[k] = ( byte ) v;
        }
    }

    public int get( int code, int type )
    {
        if ( code >= 0 && code < this._Buttons.length )
        {

            switch ( type )
            {

                case 1:
                    return this._Buttons[code] & true;

                case 2:
                    return this._Buttons[code] & 0xFE;
            }
            return this._Buttons[code];
        }

        return -1;
    }

    public boolean stateOn( int code )
    {
        if ( code >= 0 && code < this._Buttons.length )
        {
            return ( ( this._Buttons[code] & true ) != 0 );
        }
        return false;
    }

    public void update( MouseEvent e )
    {
        this.x = e.getX();
        this.y = e.getY();
    }

    public void mousePressed( MouseEvent e )
    {
        update( e );
        int code = e.getButton();
        if ( code >= 0 && code < this._Buttons.length )
        {
            this._Buttons[code] = ( byte ) ( this._Buttons[code] | true );
        }
    }

    public void mouseReleased( MouseEvent e )
    {
        update( e );
        int code = e.getButton();
        if ( code >= 0 && code < this._Buttons.length )
        {
            this._Buttons[code] = ( byte ) ( this._Buttons[code] & 0xFFFFFFFE );
        }
    }

    public void mouseClicked( MouseEvent e )
    {
        update( e );
    }

    public void mouseEntered( MouseEvent e )
    {
        update( e );
    }

    public void mouseExited( MouseEvent e )
    {
        update( e );
    }

    public void mouseMoved( MouseEvent e )
    {
        update( e );
    }

    public void mouseDragged( MouseEvent e )
    {
        update( e );
    }
}
