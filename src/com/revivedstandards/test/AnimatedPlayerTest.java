package com.revivedstandards.test;

import com.revivedstandards.test.objects.AnimatedPlayerGameObject;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.util.StdOps;
import java.awt.Color;

public class AnimatedPlayerTest extends StandardGame {

    private AnimatedPlayerGameObject player;

    public AnimatedPlayerTest () {
        super(1280, 720, "Alucard Movement Test");

        this.player = new AnimatedPlayerGameObject(this, null, 200, 200);

        this.StartGame();
    }

    @Override
    public void tick () {
        this.player.tick();
    }

    @Override
    public void render () {
        this.player.render(StandardDraw.Renderer);
        StandardDraw.text("Press D to move right", this.getGameWidth() / 2 - 180, 40, StdOps.initFont("src/res/fonts/chargen.ttf", 0f), 36f, Color.yellow );
    }

    public static void main (String[] args) {
        AnimatedPlayerTest game = new AnimatedPlayerTest();

    }
}
