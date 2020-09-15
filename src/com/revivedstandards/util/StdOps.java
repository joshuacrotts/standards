/*
 * ===========================================================================
 * Standards Java Game Library Source Code
 * Copyright (C) 2017-2020 Joshua Crotts & Andrew Matzureff
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
package com.revivedstandards.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

/**
 * This class will be similar to the Math class in terms of operations. Used for
 * arithmetic, but primarily as of now (06-07-2019), it's good for:
 *
 * - Random Numbers - Determining Mouse Location (if the mouse coordinates are
 * over a rectangle (area) - Added the ability to load in specific fonts at a
 * specific size. Pass in the String and the size, and it will be returned. -
 * Clamping a value to a specific range - Loading an image, fast math from Quake
 * III (sqrt and inverse sqrt())
 */
public abstract class StdOps {

	private static final int SQRT_MAGIC = 0x5f3759df;

	/**
	 * Returns a random integer between min and max.
	 *
	 * @param min
	 * @param max
	 * @return random integer
	 */
	public static int randomInt(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException(" Max must be smaller than min ");
		}
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	/**
	 * Generates an integer between [min, minUpperBound) U (maxLowerBound, max)
	 *
	 * For instance to generate a number between -10 and 10, but no lower than -5 or
	 * 5, do randBounds( -10, -5, 5, 10). Precision doesn't really matter;
	 *
	 * @param min
	 * @param minUpperBound
	 * @param maxLowerBound
	 * @param max
	 *
	 *                      In the end, min leq x leq minUpperBound OR maxLowerBound
	 *                      leq x leq max;
	 * @return
	 */
	public static double randomIntBounds(int min, int minUpperBound, int maxLowerBound, int max) {
		double n;

		do {
			n = StdOps.randomInt(min, max);
		} while ((n < min || n > minUpperBound) && (n < maxLowerBound || n > max));

		return n;
	}

	/**
	 * Returns a random double between min and max.
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static double randomDouble(double min, double max) {
		if (min >= max) {
			throw new IllegalArgumentException(" Max must be smaller than min ");
		}

		return ThreadLocalRandom.current().nextDouble(min, max + 1);
	}

	/**
	 * Returns a random double between [0, 1).
	 * 
	 * @return
	 */
	public static double randomDouble() {
		return ThreadLocalRandom.current().nextDouble();
	}

	/**
	 * Generates a double between [min, minUpperBound) U (maxLowerBound, max)
	 *
	 * For instance to generate a number between -10 and 10, but no lower than -5 or
	 * 5, do randBounds( -10, -5, 5, 10). Precision doesn't really matter;
	 *
	 * @param min
	 * @param minUpperBound
	 * @param maxLowerBound
	 * @param max
	 *
	 *                      In the end, min leq x leq minUpperBound OR maxLowerBound
	 *                      leq x leq max;
	 * @return
	 */
	public static double randomDoubleBounds(double min, double minUpperBound, double maxLowerBound, double max) {
		double n;

		do {
			n = StdOps.randomDouble(min, max);
		} while ((n < min || n > minUpperBound) && (n < maxLowerBound || n > max));

		return n;
	}

	/**
	 * Returns true if the mouse coordinates are within a specified
	 * bounds/rectangle, false otherwise,
	 *
	 * @param mx     - mouse x coordinate
	 * @param my     - mouse y coordinate
	 * @param x      - x position of rectangle
	 * @param y      - y position of rectangle
	 * @param width  - width of rectangle
	 * @param height - height of rectangle
	 * @return
	 */
	public static boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		return ((mx > x) && (mx < x + width)) && ((my > y) && (my < y + height));
	}

	/**
	 * Clamps num between min and max.
	 *
	 * @param num
	 * @param min
	 * @param max
	 */
	public static int clamp(int num, int min, int max) {
		if (num < min) {
			return min;
		} else if (num > max) {
			return max;
		}

		return num;
	}

	/**
	 * 
	 * @param n
	 * @param oldMin
	 * @param oldMax
	 * @param newMin
	 * @param newMax
	 * @return
	 */
	public static double normalize(double n, double oldMin, double oldMax, double newMin, double newMax) {
		if (n < oldMin || n > oldMax) {
			throw new IllegalArgumentException("Number cannot be outside the range [" + oldMin + ", " + oldMax + "].");
		}

		return (((n - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
	}

	/**
	 * Normalizes a number between 0.0 and 1.0.
	 * 
	 * @param n
	 * @param oldMin
	 * @param oldMax
	 * @return
	 */
	public static double normalize(double n, double oldMin, double oldMax) {
		return normalize(n, oldMin, oldMax, 0.0, 1.0);
	}

	/**
	 * 
	 * @param path
	 * @param size
	 * @return
	 */
	public static Font initFont(String path, float size) {
		Font f = null;

		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			return null;
		}
		return f;
	}

	/**
	 * 
	 * @param path
	 * @param size
	 * @return
	 */
	public static Font initFont(InputStream path, float size) {
		Font f = null;

		try {
			f = Font.createFont(Font.TRUETYPE_FONT, path).deriveFont(size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			return null;
		}
		return f;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static BufferedImage loadImage(String path) {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static BufferedImage loadImage(InputStream path) {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}

	/**
	 * Algorithm: http://ilab.usc.edu/wiki/index.php/Fast_Square_Root
	 * 
	 * @param x
	 * @return
	 */
	public static float fastSqrt(float x) {
		float xhalf = 0.5f * x;
		float u = x;
		int i = 0;
		i = StdOps.SQRT_MAGIC - (i >> 1); // gives initial guess y0
		return x * u * (1.5f - xhalf * u * u);// Newton step, repeating increases accuracy
	}

	/**
	 * Carmack's fast inverse sqrt function
	 *
	 * @param x
	 * @return
	 */
	public static double fastInvSqrt(double x) {
		double xhalf = 0.5d * x;
		long i = Double.doubleToLongBits(x);
		i = 0x5fe6ec85e7de30daL - (i >> 1);
		x = Double.longBitsToDouble(i);
		x *= (1.5d - xhalf * x * x);
		return x;
	}
}
