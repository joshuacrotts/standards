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
package com.revivedstandards.platform;

import com.revivedstandards.commands.Command;
import com.revivedstandards.input.Keyboard;
import com.revivedstandards.input.Mouse;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.view.StandardWindowView;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

/**
 * This class is the barebones blueprint for a game in Java. It includes the
 * render-loop, frames/updates per second information, keyboard/mouse input, the
 * screen, and other information.
 *
 * To start, extend StandardGame, and call this.StartGame();
 *
 * To stop the game, call this.StopGame();
 *
 * To add listeners to your project, call
 * 'super.addMouseListener/MouseMotionListener
 * /addKeyListener/MouseWheelListener, etc. Any listeners that java.awt.Canvas
 * supports are supported by StandardGame.
 */
public abstract class StandardGame extends Canvas implements Runnable {

	/* Default screen size */
	private static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

	/* Window for the game (encapsulates a JFrame). */
	private StandardWindowView window;

	/* Input devices. */
	private Keyboard keyboard;
	private Mouse mouse;

	/* Game loop thread. */
	private Thread thread;

	/* Debugging variables. */
	private int currentFPS;
	private boolean running;
	private boolean consoleFPS;
	private boolean titleFPS;

	/* BufferStrategy for double-buffering the JFrame. */
	private static BufferStrategy bufferStrategy = null;

	/**
	 * Creates a StandardGame object with size width x height, and title.
	 *
	 * @param width
	 * @param height
	 * @param title
	 */
	public StandardGame(int width, int height, String title) {
		this.thread = null;
		this.running = false;
		this.currentFPS = 0;
		this.consoleFPS = true;
		this.titleFPS = true;
		this.window = new StandardWindowView(width, height, title, this);

		this.createBufferStrategy(3);

		StandardGame.bufferStrategy = this.getBufferStrategy();

		this.mouse = new Mouse();
		this.keyboard = new Keyboard();

		this.addMouseListener(this.mouse);
		this.addMouseMotionListener(this.mouse);
		this.addKeyListener(this.keyboard);
	}

	/**
	 * Creates a StandardGame object with aspect ration 16:9 via the supplied width.
	 *
	 * @param width
	 * @param title
	 */
	public StandardGame(int width, String title) {
		this.window = null;
		this.thread = null;
		this.running = false;
		this.currentFPS = 0;
		this.consoleFPS = true;
		this.titleFPS = true;
		this.window = new StandardWindowView(width, (width / 16 * 9), title, this);

		this.createBufferStrategy(3);

		StandardGame.bufferStrategy = this.getBufferStrategy();

		this.mouse = new Mouse();
		this.keyboard = new Keyboard();

		this.addMouseListener(this.mouse);
		this.addMouseMotionListener(this.mouse);
		this.addKeyListener(this.keyboard);
	}

	/**
	 * Generates a StandardGame object with a title, and forces the screen size to
	 * whatever the user's monitor dimensions are.
	 *
	 * @param title
	 */
	public StandardGame(String title) {
		this.thread = null;
		this.running = false;
		this.currentFPS = 0;
		this.consoleFPS = true;
		this.titleFPS = true;
		this.window = new StandardWindowView(this.getScreenWidth(), this.getScreenHeight(), title, this);
		this.createBufferStrategy(3);

		StandardGame.bufferStrategy = this.getBufferStrategy();

		this.mouse = new Mouse();
		this.keyboard = new Keyboard();

		this.addMouseListener(this.mouse);
		this.addMouseMotionListener(this.mouse);
		this.addKeyListener(this.keyboard);
	}

	/**
	 * Initializes the thread and starts the game loop.
	 */
	public void StartGame() {
		if (this.running) {
			return;
		}
		this.thread = new Thread(this);
		this.thread.start();
		this.running = true;
	}

	/**
	 * Halts the thread, stops the game.
	 */
	public void StopGame() {
		if (!this.running) {
			return;
		}
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.running = false;
		System.exit(0);
	}

	/**
	 * The game loop.
	 */
	@Override
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double ns = 1.6666666666666666E7D;
		double delta = 0.0D;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		while (this.running) {
			boolean renderable = false;

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1.0D) {
				Command.update((float) delta);

				this.tick();

				delta--;
				updates++;

				renderable = true;
			}

			if (renderable) {
				frames++;
				StandardGame.bufferStrategy = getBufferStrategy();
				StandardDraw.Renderer = (Graphics2D) StandardGame.bufferStrategy.getDrawGraphics();

				StandardDraw.Renderer.setColor(Color.BLACK);
				StandardDraw.Renderer.fillRect(0, 0, this.getGameWidth(), this.getGameHeight());

				this.render();

				StandardDraw.Renderer.dispose();
				StandardGame.bufferStrategy.show();
			}

			if (System.currentTimeMillis() - timer > 1000L) {
				timer += 1000L;

				this.currentFPS = frames;

				if (this.titleFPS) {
					this.window.setTitle(String.valueOf(this.window.getTitle()) + " | " + updates + " ups, " + frames + " fps");
				}
				if (this.consoleFPS) {
					System.out.println(String.valueOf(this.window.getTitle()) + " | " + updates + " ups, " + frames + " fps");
				}

				updates = 0;
				frames = 0;
			}
		}

		StopGame();
	}

	/**
	 * Handles all physics/game state/object state updates.
	 */
	public abstract void tick();

	/**
	 * Renders graphics, text, sprites, etc. to the StandardWindowView. To use this,
	 * call StandardDraw.Renderer to reference the G2D object.
	 */
	public abstract void render();

	public StandardGame getGame() {
		return this;
	}

	public int getFPS() {
		return this.currentFPS;
	}

	public int getGameWidth() {
		return this.window.width();
	}

	public int getGameHeight() {
		return this.window.height();
	}

	public void printFramesToConsole(boolean print) {
		this.consoleFPS = print;
	}

	public void printFramesToTitle(boolean print) {
		this.titleFPS = print;
	}

	public StandardWindowView getWindow() {
		return this.window;
	}

	public Keyboard getKeyboard() {
		return this.keyboard;
	}

	public void setKeyboard(Keyboard keyboard) {
		this.keyboard = keyboard;
	}

	public Mouse getMouse() {
		return this.mouse;
	}

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

	private int getScreenWidth() {
		return (int) StandardGame.SCREEN_DIMENSION.getWidth();
	}

	public int getScreenHeight() {
		return (int) StandardGame.SCREEN_DIMENSION.getHeight();
	}
}
