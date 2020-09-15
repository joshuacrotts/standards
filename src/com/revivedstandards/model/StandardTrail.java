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

import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.view.ShapeType;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Renders a translucent trail behind the supplied StandardGameObject. This
 * trail can either be a shape (circle or rectangle), or a translucent image of
 * the sprite. The life denotes how long each trail should last (i.e. the lower
 * the life, the shorter the trail and vice versa). If the isImage flag is on,
 * it will draw a copy of the supplied SGO's current sprite.
 *
 * THIS CLASS IS VERY, VERY BROKEN.
 */
public class StandardTrail extends StandardGameObject {

  //
  // Information regarding the lifespan and visibility of the
  // trail.
  //
  private double angle = 0.0;
  private float alpha = 1.0F;
  private float life = -1f;
  private boolean isImage = false;

  //
  // Color and shape information if we are only rendering a shape as opposed
  // to an image. If we are rendering an image, the SGO's current sprite
  // is referenced.
  //
  private Color color;
  private ShapeType shape;
  private final StandardGameObject obj;

  //
  // Handler for the trail ONLY.
  //
  private final StandardHandler stdHandler;

  public StandardTrail(double x, double y, double width, double height, double angle, float life, Color color,
      StandardGameObject o, StandardHandler stdHandler, ShapeType shape) {
    super(x, y, (int) width, (int) height, StandardID.Trail);
    this.color = color;
    this.life = life;
    this.shape = shape;
    this.angle = angle;
    this.stdHandler = stdHandler;
    this.isImage = false;
    this.obj = o;

    this.checkNullShape();
  }

  public StandardTrail(double x, double y, double angle, float life, StandardGameObject obj,
      StandardHandler stdHandler) {
    super(x, y, StandardID.Trail);

    this.obj = obj;
    this.stdHandler = stdHandler;
    this.angle = angle;
    this.life = life;
    this.isImage = true;

    this.setWidth(this.obj.getCurrentSprite().getWidth());
    this.setHeight(this.obj.getCurrentSprite().getHeight());
  }

  private AlphaComposite makeTransparent(float alpha) {
    int type = 3;
    return AlphaComposite.getInstance(type, alpha);
  }

  @Override
  public void tick() {
    if (this.alpha >= this.life) {
      this.alpha = this.life;
    } else {
      this.stdHandler.removeEntity(this);
    }

  }

  @Override
  public void render(Graphics2D g2) {
    g2.setComposite(makeTransparent(this.alpha));
    if (!this.isImage && this.shape != null) {
      g2.setColor(this.color);
      if (this.shape == ShapeType.CIRCLE) {
        g2.fillOval((int) getX(), (int) getY(), getWidth(), getHeight());
      } else {
        g2.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
      }
    } else {
      g2.drawImage(this.obj.getCurrentSprite(), (int) getX(), (int) getY(), null);
    }
    g2.setComposite(makeTransparent(1.0F));
  }

  private void checkNullShape() {
    if (this.shape == null && !this.isImage) {
      System.err.println("Shape is NULL in a Trail. Defaulting to type RECTANGLE.");
      this.shape = ShapeType.RECTANGLE;
    }
  }

  public float getAlpha() {
    return this.alpha;
  }

  public void setAlpha(float alpha) {
    this.alpha = alpha;
  }

  public float getLife() {
    return this.life;
  }

  public void setLife(float life) {
    this.life = life;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public ShapeType getShape() {
    return this.shape;
  }

  public void setShape(ShapeType shape) {
    this.shape = shape;
  }

  public boolean isImage() {
    return this.isImage;
  }

  public void setImage(boolean isImage) {
    this.isImage = isImage;
  }

  public double getAngle() {
    return this.angle;
  }
}
