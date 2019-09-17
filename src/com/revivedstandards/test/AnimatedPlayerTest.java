package com.revivedstandards.test;

import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.test.objects.AnimatedPlayerGameObject;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.util.StdOps;

public class AnimatedPlayerTest extends StandardGame
{
    private final AnimatedPlayerGameObject player;
    private final StandardHandler sh;

    public AnimatedPlayerTest ()
    {
        super( 1280, 720, "Alucard Movement Test" );
        this.player = new AnimatedPlayerGameObject( this, null, 200, 200 );
        this.sh = new StandardHandler();
        this.sh.addEntity( this.player );

        for ( int i = 0, y = 100 ; i < 100 ; i++, y += 50 )
        {
            this.sh.addEntity( new AnimatedPlayerGameObject( this, null, 200, y ) );
        }

        this.StartGame();
    }

    @Override
    public void tick ()
    {
        this.sh.tick();
    }

    @Override
    public void render ()
    {
        this.sh.stdRender( StandardDraw.Renderer );

        StandardDraw.text( "Press D to move right", this.getGameWidth() / 2 - 180, 40, StdOps.initFont( "src/res/fonts/chargen.ttf", 0f ), 36f, StandardDraw.YELLOW );
    }

    public static void main ( String[] args )
    {
        AnimatedPlayerTest game = new AnimatedPlayerTest();
    }
}
