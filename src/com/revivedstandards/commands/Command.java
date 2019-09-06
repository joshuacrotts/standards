package com.revivedstandards.commands;

import com.revivedstandards.input.InputDevice;
import com.revivedstandards.input.Keyboard;
import com.revivedstandards.main.StandardGame;
import java.util.Arrays;

public abstract class Command {

    public static final int MAX_COMMANDS = 128;
    public static final int UNDEFINED = -1;
    public static final int ESC = 27;
    public static final int DEL = 127;
    public static final int STATE_MASK = 1;
    public static final int TOGGLE = 1;
    public static final int TOGGLE_MASK = 3;
    public static final int CONTINUOUS = 0;
    public static final Command NULL = null;
    protected static Command[] GAME_COMMANDS = new Command[128];
    public static int[] KEY_BINDS = new int[128];
    protected int _Style = 0;
    protected long _States = 0L;
    protected int _Bits = 0;

    protected boolean _On = false;

    public static void init()
    {
        Arrays.fill( KEY_BINDS, -1 );
        Arrays.fill( GAME_COMMANDS, NULL );
    }

    public static void execute( int key )
    {
        if ( key == -1 )
        {
            return;
        }
        int index = key >> 1;
        key &= 0x1;
        Command command = GAME_COMMANDS[index];
        command._Bits = command._Bits - ( int ) ( command._States >>> 63 ) + key;
        command._States = command._States << true | key;

        switch ( command._Style )
        {

            case 0:
                if ( key == 1 )
                {
                    command._On = true;
                    break;
                }
                command._On = false;
                break;
            case 1:
                if ( ( ( command._States ^ 0x3L ) & 0x3L ) == 1L )
                {
                    command._On = !command._On;
                }
                break;
        }
        if ( command._On )
        {
            command.execute();
        }
    }

    public abstract void execute();

    public boolean on()
    {
        return this._On;
    }

    public void bind( InputDevice id, int key )
    {
        switch ( key )
        {
            case 27:
                return;

            case -1:
                clear( id );
                return;
        }
        assign( id, key );
    }

    protected void assign( InputDevice id, int key )
    {
        for ( int i = 0; i < 128; i++ )
        {

            if ( KEY_BINDS[i] == key )
            {

                int commandIndex = id.get( KEY_BINDS[i], 2 ) >> 1;
                if ( GAME_COMMANDS[commandIndex] == this )
                {
                    return;
                }

                GAME_COMMANDS[commandIndex].clear( id );
                id.set( key, index( true ) << 1 );

                return;
            }
            if ( KEY_BINDS[i] == -1 )
            {

                KEY_BINDS[i] = key | id.getDeviceMask();

                id.set( key, index( true ) << 1 );
                return;
            }
        }
    }

    public void clear( InputDevice id )
    {
        for ( int i = 0; i < 128; i++ )
        {

            if ( KEY_BINDS[i] != -1 )
            {

                int commandIndex = id.get( KEY_BINDS[i], 2 ) >> 1;
                if ( GAME_COMMANDS[commandIndex] == this )
                {

                    id.set( KEY_BINDS[i], 0 );
                    KEY_BINDS[i] = -1;
                }
            }
        }
    }

    public int index( boolean insert )
    {
        for ( int i = 0; i < 128; i++ )
        {

            if ( GAME_COMMANDS[i] == this )
            {
                return i;
            }

            if ( GAME_COMMANDS[i] == NULL && insert )
            {

                GAME_COMMANDS[i] = this;
                return i;
            }
        }
        return -1;
    }

    public static void tick( StandardGame stdGame )
    {
        for ( int i = 0; i < 128; i++ )
        {
            Keyboard keyboard;
            int key = KEY_BINDS[i];

            if ( ( key & 0x80000000 ) == Integer.MIN_VALUE )
            {

                keyboard = ( Mouse ) stdGame.getMouse();
                key ^= Integer.MIN_VALUE;
            } else
            {

                keyboard = stdGame.getKeyboard();
            }
            key = keyboard.get( key, 0 );
            execute( key );
        }
    }
}
