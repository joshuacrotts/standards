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
package com.revivedstandards.model;

import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.ShapeType;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class represents a standard box particle.
 */
public class StandardBoxParticle extends StandardParticle {

    //
    //  Circle or rectangle
    //
    private final ShapeType shapeType;

    //
    //  Velocity factors that are continuously applied to the
    //  particle's dx and dy.
    //
    private double velXFactor = StdOps.randBounds(-0.3, 0.0, 0.0, 0.3);
    private double velYFactor = StdOps.randBounds(-0.3, 0.0, 0.0, 0.3);

    //
    //  In order to use variable velocities, this boolean must be checked.
    //
    private final boolean variableVelocity;

    public StandardBoxParticle (double x, double y, double dimension,
            double velX, double velY, Color c, double life,
            StandardParticleHandler sph, double rotationAngle,
            ShapeType shape, boolean variableVelocity) {
        super(x, y, life, sph, c, rotationAngle);

        super.setWidth((int) dimension);
        super.setHeight((int) dimension);
        super.setVelX(velX);
        super.setVelY(velY);
        this.variableVelocity = variableVelocity;
        this.shapeType = shape;
    }

    @Override
    public void tick () {
        if (this.variableVelocity) {
            this.setVelX(this.getVelX() + this.velXFactor);
            this.setVelY(this.getVelY() + this.velYFactor);
        }

        super.updatePosition();
    }

    @Override
    public void render (Graphics2D g2) {
        g2.setColor(this.getColor());

        if (this.shapeType == ShapeType.RECTANGLE) {
            g2.fillRect((int) this.getX(), (int) this.getY(),
                    (int) this.getWidth(), (int) this.getHeight());
        }
        else {
            g2.fillOval((int) this.getX(), (int) this.getY(),
                    (int) this.getWidth(), (int) this.getHeight());
        }
    }

    /**
     * Sets a variable velocity for the X coordinate. This value will be applied
     * to the X velocity continuously over the life span of the particle.
     *
     * The range of values should be approx. -0.5 leq min leq 0.0 leq max leq
     * approx 0.5
     *
     * Other values higher or lower than +-0.5 are POSSIBLE, but it gets
     * insane...
     *
     * @param min
     * @param max
     */
    public void setVariableVelocityX (int min, int max) {
        if (!this.variableVelocity) {
            throw new IllegalStateException("To set the variable velocities, "
                    + "this particle must have variable velocities enabled.");
        }
        this.velXFactor = StdOps.randBounds(min, 0.0, 0.0, max);
    }

    /**
     * Sets a variable velocity for the Y coordinate. This value will be applied
     * to the Y velocity continuously over the life span of the particle.
     *
     * The range of values should be approx. -0.5 leq min leq 0.0 leq max leq
     * approx 0.5
     *
     * Other values higher or lower than +-0.5 are POSSIBLE, but it gets
     * insane...
     *
     * @param min
     * @param max
     */
    public void setVariableVelocityY (int min, int max) {
        if (!this.variableVelocity) {
            throw new IllegalStateException("To set the variable velocities, "
                    + "this particle must have variable velocities enabled.");
        }
        this.velYFactor = StdOps.randBounds(min, 0.0, 0.0, max);
    }
}
