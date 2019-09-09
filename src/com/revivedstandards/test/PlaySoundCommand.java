package com.revivedstandards.test;

import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardGameObject;

public class PlaySoundCommand extends Command {

    public StandardGameObject player;

    public PlaySoundCommand( StandardGameObject sgo )
    {
        this.player = sgo;
    }

    @Override
    public void pressed( float delta )
    {
        StandardAudioController.play( "src/res/audio/sfx/soma_hurt0.wav" );
    }
}
