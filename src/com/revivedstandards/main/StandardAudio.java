package com.revivedstandards.main;

import java.io.File;
import javafx.scene.media.AudioClip;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class StandardAudio {

    private Clip audioClip;
    private String fileName;
    private AudioClip sound;
    private boolean sfx;

    public StandardAudio( String fileName )
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
        } catch ( Exception e )
        {

            e.printStackTrace();
        }
    }

    public StandardAudio( String fileName, boolean sfx )
    {
        this.sfx = sfx;

        try
        {
            this.sound = new AudioClip( ( new File( fileName ) ).toURI().toString() );
        } catch ( Exception e )
        {

            e.printStackTrace();
        }

        this.sound.setVolume( 1.0D );
    }

    public void rAndP()
    {
        this.audioClip.setFramePosition( 0 );
        this.audioClip.start();
    }

    public void J2DPlay()
    {
        this.audioClip.start();
    }

    public void J2DStop()
    {
        this.audioClip.stop();
    }

    public void J2DClose()
    {
        this.audioClip.close();
    }

    public boolean isPlaying()
    {
        return this.audioClip.isRunning();
    }

    public void FXPlay()
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

    public void adjustFXVolume( double val )
    {
        this.sound.setVolume( this.sound.getVolume() + val );
    }

    public double getFXVolume()
    {
        return this.sound.getVolume();
    }

    public void FXStop()
    {
        this.sound.stop();
    }

    public String getFileName()
    {
        return this.fileName;
    }

    public void FXLoop()
    {
        this.sound.setCycleCount( -1 );
    }

    public void FXResetVolume()
    {
        this.sound.setVolume( 1.0D );
    }

    public boolean isFXPlaying()
    {
        return this.sound.isPlaying();
    }
}
