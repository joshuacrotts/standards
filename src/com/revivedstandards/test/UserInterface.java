package com.revivedstandards.test;

import com.revivedstandards.test.objects.TriangleGameObject;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.test.commands.PauseCommand;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * This class shows how to render text and other elements on the screen.
 *
 * @author Joshua
 */
public class UserInterface implements Renderable, Updatable {

	//
	// Global instance variables
	//
	private final FollowTheMouseGameTest sg;
	private final StandardCamera sc;
	private final TriangleGameObject obj;

	//
	// Points that serve as a reference to draw
	// UI elements
	//
	private int renderX = 0;
	private int renderY = 0;

	//
	// Command for pausing and unpausing the game
	//
	private final PauseCommand pauseCommand;

	//
	// Font used for elements
	//
	private final Font gameFont;

	public UserInterface(FollowTheMouseGameTest sg, TriangleGameObject obj, StandardCamera sc) {
		this.sg = sg;
		this.sc = sc;
		this.obj = obj;

		this.gameFont = StdOps.initFont("src/res/fonts/chargen.ttf", 0f);

		this.pauseCommand = new PauseCommand(sg);
		this.pauseCommand.bind(this.sg.getKeyboard(), KeyEvent.VK_P);

	}

	@Override
	public void tick() {
		this.renderX = (int) (this.sc.getX() - this.sg.getGameWidth() / 2);
		this.renderY = (int) (this.sc.getY() - this.sg.getGameHeight() / 2);
	}

	@Override
	public void render(Graphics2D g2) {
		// Debug information (y = 50 is the V_offset)
		StandardDraw.text("GAME", this.renderX + this.sg.getGameWidth() / 2, this.renderY + 50, this.gameFont, 36f,
				Color.yellow);
		StandardDraw.text("FPS: " + this.sg.getFPS(), this.renderX + 50, this.renderY + 50, this.gameFont, 20f,
				Color.yellow);
		StandardDraw.text("Beta Testing Purposes ONLY", this.renderX + this.sg.getGameWidth() - 380, this.renderY + 50,
				this.gameFont, 20f, Color.yellow);

		// Actual Player info
		StandardDraw.text("Ammo: " + this.obj.getBulletCount(), this.renderX + 50, this.renderY + 75, this.gameFont,
				20f, Color.yellow);

		// If the game is paused, we can draw a black square over the screen saying it's
		// paused
		if (this.sg.getGameState() == GameState.PAUSED) {
			this.renderPauseScreen(g2);
		}
	}

	private void renderPauseScreen(Graphics2D g2) {
		Color c = StandardDraw.Renderer.getColor();
		StandardDraw.Renderer.setColor(new Color(0, 0, 0, 0.5f));
		StandardDraw.Renderer.fillRect((int) -2000, -2000, 50000, 10000);// Just draws a HUGE rectangle over hopefully
																			// affected areas
		StandardDraw.text("PAUSED", (int) this.renderX + this.sg.getGameWidth() / 2,
				this.renderY + this.sg.getGameHeight() / 2, this.gameFont, 24f, Color.white);
		StandardDraw.Renderer.setColor(c);
	}
}
