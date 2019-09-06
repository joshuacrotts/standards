package com.revivedstandards.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener, InputDevice {

    public final byte[] _Keys = new byte[65536];

    public int getDeviceMask()
    {
        return 0;
    }

    public byte[] getBytes()
    {
        return this._Keys;
    }

    public void set( int k, int v )
    {
        if ( k >= 0 && k < this._Keys.length )
        {
            this._Keys[k] = ( byte ) v;
        }
    }

    public int get( int code, int type )
    {
        if ( code >= 0 && code < this._Keys.length )
        {

            switch ( type )
            {

                case 1:
                    return this._Keys[code] & true;

                case 2:
                    return this._Keys[code] & 0xFE;
            }
            return this._Keys[code];
        }

        return -1;
    }

    public boolean stateOn( int code )
    {
        if ( code >= 0 && code < this._Keys.length )
        {
            return ( ( this._Keys[code] & true ) != 0 );
        }
        return false;
    }

    public void keyPressed( KeyEvent e )
    {
        int code = e.getKeyCode();
        if ( code >= 0 && code < this._Keys.length )
        {
            this._Keys[code] = ( byte ) ( this._Keys[code] | true );
        }
    }

    public void keyReleased( KeyEvent e )
    {
        int code = e.getKeyCode();
        if ( code >= 0 && code < this._Keys.length )
        {
            this._Keys[code] = ( byte ) ( this._Keys[code] & 0xFFFFFFFE );
        }
    }

    public void keyTyped( KeyEvent e )
    {
    }
}
