package com.revivedstandards.test;

import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardDragParticle;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;

/**
 * RainController will spawn different rain (blue) particles if it is raining in
 * the location provided by the user.
 */
public class RainController implements Renderable, Updatable {

	private final StandardParticleHandler sph;
	private final StandardGame sg;
	private final StandardCamera sc;

	private static final int X_BORDER = 600;
	private static final int Y_BORDER = 400;

	public RainController(StandardGame sg, StandardCamera sc) {
		this.sg = sg;
		this.sc = sc;
		this.sph = new StandardParticleHandler(5000);
	}

	@Override
	public void tick() {

		// Generates the min/max points for the rain to spawn
		int xGenMin = (int) (this.sc.getX() - RainController.X_BORDER);
		int xGenMax = (int) (this.sc.getX() + RainController.X_BORDER);
		int yGenMin = (int) (this.sc.getY() - RainController.Y_BORDER * 2);
		int yGenMax = (int) (this.sc.getY() - RainController.Y_BORDER);

		int xPos = StdOps.randomInt(xGenMin, xGenMax);
		int yPos = StdOps.randomInt(yGenMin, yGenMax);

		this.sph.addEntity(new StandardDragParticle(xPos, yPos, 10f, sph, StandardDraw.BLUE));

		this.sph.tick();
	}

	@Override
	public void render(Graphics2D g2) {
		this.sph.render(g2);
	}

}
