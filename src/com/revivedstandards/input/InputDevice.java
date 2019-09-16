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

import com.revivedstandards.commands.Command;

public abstract class InputDevice
{

    private final BitArray keys;
    private final String name;
    private final int id;
    private short alert;

    public InputDevice ( String name, int size )
    {
        keys = new BitArray( size );
        alert = 0;
        id = Command.register( this );
        this.name = name;
    }

    public boolean alerted ()
    {
        return alert != 0;
    }

    public int alerts ()
    {
        return alert & 0xffff;
    }

    protected void alert ( int k, boolean v )
    {
        if ( keys.get( k ) != v )
        {
            if ( v )
            {
                alert++;
            }
            else
            {
                alert--;
            }
            keys.set( k, v );
        }
    }

    @Override
    public String toString ()
    {
        return "InputDevice[" + id + "]: " + ( name == null ? "Unknown" : name );
    }

    public boolean get ( int key )
    {
        if ( key >= 0 && key < keys.size() )
        {
            return keys.get( key );
        }
        return false;
    }

    protected void set ( int k, boolean v )
    {
        if ( k >= 0 && k < keys.size() )
        {
            alert( k, v );
        }
    }
}
