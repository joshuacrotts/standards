package com.revivedstandards.test.commands;

import com.revivedstandards.commands.Command;
import com.revivedstandards.test.FollowTheMouseGameTest;
import com.revivedstandards.test.GameState;

public class PauseCommand extends Command
{

    private final FollowTheMouseGameTest sg;
    private boolean paused = false;

    public PauseCommand ( FollowTheMouseGameTest sg )
    {
        this.sg = sg;
    }

    @Override
    public void pressed ( float dt )
    {
        this.paused = !this.paused;

        this.sg.setGameState( this.paused ? GameState.PAUSED : GameState.RUNNING );
    }

}
