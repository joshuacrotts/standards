package com.revivedstandards.test;

import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardGameObject;

public class PlaySoundCommand extends Command {

    public StandardGameObject player;
    public StandardAudioController sac;

    public PlaySoundCommand( StandardGameObject sgo, StandardAudioController sac )
    {
        this.player = sgo;
        this.sac = sac;
    }

    @Override
    public void pressed( float delta )
    {
        this.sac.play( "src/res/audio/sfx/soma_hurt0.wav" );
    }
}
