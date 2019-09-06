/*
===========================================================================
                   Standards Java Game Library Source Code
           Copyright (C) 2017-2019 Joshua Crotts & Andrew Matzureff 
Standards is free software: you can redistribute it and/or modify it under 
the terms of the GNU General Public License as published by the Free Software 
Foundation, either version 3 of the License, or (at your option) any later 
version.

Standards Source Code is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Standards Source Code. If not, see <http://www.gnu.org/licenses/>.

Standards is the long-overdue update to the everlasting Standards 2.0 library
Andrew Matzureff and I created two years ago. I am including it in this project
to simplify the rendering and logic pipeline, but with a focus on the MVC
paradigm.

We connect to the Apache FastMath API for some of our trigonometric functions,
and we use John Carmack's fast inverse square root function.
===========================================================================
 */

package com.revivedstandards.main;

import java.io.File;
import java.io.IOException;
import javafx.scene.media.AudioClip;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class StandardAudio
{
    private Clip audioClip;
    private String fileName;
    private AudioClip sound;
    
    private boolean sfx;

    public StandardAudio ( String fileName )
    {
        this.fileName = fileName;
        try
        {
            File audioFile = new File( fileName );
            AudioInputStream audioStream = AudioSystem.getAudioInputStream( audioFile );
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info( Clip.class, format );
            this.audioClip = ( Clip ) AudioSystem.getLine( info );
            this.audioClip.open( audioStream );
        }
        catch ( IOException | LineUnavailableException | UnsupportedAudioFileException e )
        {

            e.printStackTrace();
        }
    }

    public StandardAudio ( String fileName, boolean sfx )
    {
        this.sfx = sfx;

        try
        {
            this.sound = new AudioClip( ( new File( fileName ) ).toURI().toString() );
        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }

        this.sound.setVolume( 1.0D );
    }

    public void rAndP ()
    {
        this.audioClip.setFramePosition( 0 );
        this.audioClip.start();
    }

    public void J2DPlay ()
    {
        this.audioClip.start();
    }

    public void J2DStop ()
    {
        this.audioClip.stop();
    }

    public void J2DClose ()
    {
        this.audioClip.close();
    }

    public boolean isPlaying ()
    {
        return this.audioClip.isRunning();
    }

    public void FXPlay ()
    {
        if ( this.sound.isPlaying() )
        {
            return;
        }
        this.sound.play();
        if ( !this.sfx )
        {
            FXLoop();
        }
    }

    public void adjustFXVolume ( double val )
    {
        this.sound.setVolume( this.sound.getVolume() + val );
    }

    public double getFXVolume ()
    {
        return this.sound.getVolume();
    }

    public void FXStop ()
    {
        this.sound.stop();
    }

    public String getFileName ()
    {
        return this.fileName;
    }

    public void FXLoop ()
    {
        this.sound.setCycleCount( -1 );
    }

    public void FXResetVolume ()
    {
        this.sound.setVolume( 1.0D );
    }

    public boolean isFXPlaying ()
    {
        return this.sound.isPlaying();
    }
}
