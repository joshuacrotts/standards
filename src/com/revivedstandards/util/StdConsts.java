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
package com.revivedstandards.util;

import java.io.File;

/**
 * This class contains several bitmask constants that are referenced a lot in
 * Command, InputDevice, Keyboard, Mouse, and other miscellaneous file parsing
 * locations.
 */
public class StdConsts
{

    public static final int MOUSE_MASK = -2147483648;
    public static final int KEYBOARD_MASK = 0;
    public static final int COMMAND_MASK = 254;
    public static final int STATE_MASK = 1;
    public static final int TOGGLE_MASK = 3;
    public static final long RELEASE_MASK = -4L;
    public static final int CHAR_MASK = 131071;
    public static final long NANO = 1000000000L;
    public static final int END_OF_FILE = -1;
    public static final int NULL = 0;
    public static final int STATE = 1;
    public static final int COMMAND = 2;
    public static final int BOTH = 0;
    public static final int UNDEFINED = -1;
    public static final int TOGGLE = 1;
    public static final int CONTINUOUS = 0;
    public static final int MAX_COMMANDS = 128;
    public static final int ESCAPE = 27;
    public static final int DELETE = 127;
    public static final int MAX_SCAN = 1024;
    public static final int BITS_INT = 32;
    public static final int BITS_LONG = 64;
    public static final int MAX_CHARS = 65536;
    public static final char NULL_CHAR = '\000';
    public static final String TXT = "txt";
    public static final String TX8 = "tx8";
    public static final String T16 = "t16";
    public static final String B16 = "b16";
    public static final String FILE_SEPARATOR = File.separator;
}
