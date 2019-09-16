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
package com.revivedstandards.model;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * StandardAudio is a defined object that has an audio clip. These objects
 * should never be directly instantiated by the programmer; they should only be
 * instantiated by the controller, via load().
 */
public final class StandardAudio
{

    private Clip audioClip;
    private String fileName;

    public static int INFINITELY = Clip.LOOP_CONTINUOUSLY;

    public StandardAudio ( String fileLocation )
    {
        this.load( fileLocation );
    }

    /**
     * Loads an audio track from the system into the audio buffer and prepares
     * it for play.
     *
     * If the user attempts to load in a clip when the audio buffer is full, an
     * IllegalStateException is thrown.
     *
     * @param fileName
     */
    public void load ( String fileName )
    {
        this.fileName = fileName;

        try
        {
            //  Find the file and load it into an Audio Input Stream
            File audioFile = new File( this.fileName );
            AudioInputStream audioStream = AudioSystem.getAudioInputStream( audioFile );
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info( Clip.class, format );
            this.audioClip = ( Clip ) AudioSystem.getLine( info );

            //  Open the lip and load samples from the AIS
            this.audioClip.open( audioStream );
        }
        catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Starts the clip.
     */
    public void start ()
    {
        if ( !this.audioClip.isRunning() )
        {
            this.audioClip.start();
        }
    }

    /**
     * Stops the current audio track.
     */
    public void stop ()
    {
        if ( this.audioClip.isRunning() )
        {
            this.audioClip.stop();
        }
    }

    /**
     * Loops a clip n times. If n is StandardAudio.INFINITELY, the file is
     * looped forever.
     *
     * @param n
     */
    public void loop ( int n )
    {
        if ( n < 0 )
        {
            throw new IllegalArgumentException( "Cannot loop for less than 0 times." );
        }
        else if ( n >= 0 && n < StandardAudio.INFINITELY )
        {
            this.audioClip.loop( n );
        }
        else
        {
            this.audioClip.loop( Clip.LOOP_CONTINUOUSLY );
        }
    }

    /**
     * Sets the frame position of the current audio clip to 0.
     */
    public void resetFramePosition ()
    {
        this.audioClip.setFramePosition( 0 );
    }

    /**
     * Sets the frame position of the current audio clip to x.
     *
     * @param x
     */
    public void setFramePosition ( int x )
    {
        this.audioClip.setFramePosition( x );
    }

    public boolean isPlaying ()
    {
        return this.audioClip.isRunning();
    }

    public String getFileName ()
    {
        return this.fileName;
    }

}
