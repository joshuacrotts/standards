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
 * ===========================================================================
 */
package com.revivedstandards.view;

import com.revivedstandards.model.StandardGameObject;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class StandardAnimationView implements Renderable {

    private final BufferedImage[] animationFrames;
    private final StandardGameObject obj;
    private BufferedImage currentFrame;

    public StandardAnimationView (BufferedImage[] frames, StandardGameObject obj) {
        this.animationFrames = frames;
        this.obj = obj;
    }

    @Override
    public void render (Graphics2D g2) {
        g2.drawImage(this.currentFrame, (int) obj.getX(), (int) obj.getY(), this.currentFrame.getWidth(), this.currentFrame.getHeight(), null);
    }

    public void render (Graphics2D g2, double x, double y, double theta) {
        AffineTransform backup = g2.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.rotate(theta, (x + this.currentFrame.getWidth() / 2.0f), (y + this.currentFrame.getHeight() / 2.0f));

        g2.transform(transform);
        g2.drawImage(this.currentFrame, (int) x, (int) y, this.currentFrame.getWidth(), this.currentFrame.getHeight(), null);
        g2.setTransform(backup);
    }

    public void render (Graphics2D g2, double x, double y, double width, double height, double theta) {
        AffineTransform backup = g2.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.rotate(theta, (x + this.currentFrame.getWidth() / 2.0f), (y + this.currentFrame.getHeight() / 2.0f));

        g2.transform(transform);
        g2.drawImage(this.currentFrame, (int) x, (int) y, (int) width, (int) height, null);
        g2.setTransform(backup);
    }

    public void renderBounds (Graphics2D g2, Rectangle rect) {
        g2.drawRect((int) rect.getX(), (int) rect.getY(),
                (int) rect.getWidth(), (int) rect.getHeight());
    }

    public BufferedImage[] getFrames () {
        return this.animationFrames;
    }

    public BufferedImage getCurrentFrame () {
        return this.currentFrame;
    }

    public void setCurrentFrameIndex (int frame) {
        this.currentFrame = this.animationFrames[frame];
    }

}
