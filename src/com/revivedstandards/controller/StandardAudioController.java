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
package com.revivedstandards.controller;

import com.revivedstandards.model.StandardAudio;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * The StandardAudioController acts as an audio buffer to play audio tracks
 * without having to have a ton of StandardAudio objects in random places.
 *
 * It also alleviates the problem of cutting already-playing tracks off when
 * it's necessary to start another (shooting bullets for instance).
 */
public class StandardAudioController
{

    private static HashMap<Integer, LinkedList<StandardAudio>> audioBuffer;

    /**
     * Initializes the Audio buffer. This cannot be called twice.
     *
     * @param buffers
     */
    public static void init ( int buffers )
    {
        if ( buffers <= 0 )
        {
            throw new IllegalArgumentException( "The amount of buffers cannot be less than or equal to 0." );
        }
        else if ( StandardAudioController.audioBuffer != null )
        {
            throw new IllegalStateException( "The audio buffer already exists!" );
        }

        StandardAudioController.audioBuffer = new HashMap<>( buffers );
    }

    /**
     * Attempts to add the fileName if the key exists in the hashmap.
     *
     * @param fileName
     * @param n - amount of times to buffer the file
     */
    public static void load ( String fileName, int n )
    {
        int fileHash = fileName.hashCode();

        if ( !StandardAudioController.audioBuffer.containsKey( fileHash ) )
        {
            StandardAudioController.audioBuffer.put( fileName.hashCode(), new LinkedList<>() );
        }

        LinkedList<StandardAudio> audioList = StandardAudioController.audioBuffer.get( fileHash );

        for ( int i = 0 ; i < n ; i++ )
        {
            audioList.add( new StandardAudio( fileName ) );
        }
    }

    /**
     * Attempts to add one instance of the fileName to the buffer.
     *
     * @param fileName
     */
    public static void load ( String fileName )
    {
        StandardAudioController.load( fileName, 1 );
    }

    /**
     * Plays the file at the supplied path. If the file is not in the hashmap,
     * one copy is initialized into it.
     *
     * @param fileName
     */
    public static void play ( String fileName )
    {
        int fileHash = fileName.hashCode();

        LinkedList<StandardAudio> audioList;

        if ( StandardAudioController.audioBuffer.get( fileHash ) == null )
        {
            StandardAudioController.load( fileName );
        }

        audioList = StandardAudioController.audioBuffer.get( fileHash );

        for ( int i = 0 ; i < audioList.size() ; i++ )
        {
            StandardAudio audio = audioList.get( i );
            if ( !audio.isPlaying() )
            {
                audio.resetFramePosition();
                audio.start();
                return;
            }
        }
    }
}
