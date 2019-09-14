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
package com.revivedstandards.main;

import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class StandardDraw {

    public static Graphics2D Renderer;

    /**
     * Draws an image to the screen using doubles that are downcasted to ints.
     *
     * @param image
     * @param x
     * @param y
     */
    public static void image (BufferedImage image, double x, double y) {
        StandardDraw.Renderer.drawImage(image, (int) x, (int) y, null);
    }

    /**
     * Draws an image to the screen with integer parameters.
     *
     * @param image
     * @param x
     * @param y
     */
    public static void image (BufferedImage image, int x, int y) {
        StandardDraw.Renderer.drawImage(image, x, y, null);
    }

    /**
     * Draws a string of text to the screen with a specific font, font size, and
     * color.
     *
     * @param text
     * @param x
     * @param y
     * @param font - name of font, not the .tff location or Font class object.
     * @param size
     * @param color
     */
    public static void text (String text, int x, int y, String font, float size, Color color) {
        Font oldFont = StandardDraw.Renderer.getFont();
        Color oldColor = StandardDraw.Renderer.getColor();

        if (font != null && !font.equals("")) {
            StandardDraw.Renderer.setFont(StdOps.initFont(font, size));
        }
        else {
            StandardDraw.Renderer.setFont(new Font("Arial", 0, (int) size));
        }
        StandardDraw.Renderer.setColor(color);
        StandardDraw.Renderer.drawString(text, x, y);

        StandardDraw.Renderer.setColor(oldColor);
        StandardDraw.Renderer.setFont(oldFont);
    }

    /**
     * Draws a string of text to the screen with a specific font, font size, and
     * color.
     *
     * @param text
     * @param x
     * @param y
     * @param font - must be the Font class object.
     * @param size
     * @param color
     */
    public static void text (String text, int x, int y, Font font, float size, Color color) {
        Font oldFont = StandardDraw.Renderer.getFont();
        Color oldColor = StandardDraw.Renderer.getColor();

        if (font != null) {
            StandardDraw.Renderer.setFont(font.deriveFont(size));
        }
        else {
            StandardDraw.Renderer.setFont(new Font("Arial", 0, (int) size));
        }
        StandardDraw.Renderer.setColor(color);
        StandardDraw.Renderer.drawString(text, x, y);

        StandardDraw.Renderer.setColor(oldColor);
        StandardDraw.Renderer.setFont(oldFont);
    }

    /**
     * Draws a rectangle at position x, y, dim width, height, Color color to the
     * screen.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color - if null, defaults to black.
     * @param fill - if true, will fill the rectangle with color. Otherwise,
     * will draw the outline only
     */
    public static void rect (double x, double y, double width, double height, Color color, boolean fill) {
        if (color == null) {
            color = Color.black;
        }

        if (fill) {
            Color old = StandardDraw.Renderer.getColor();
            StandardDraw.Renderer.setColor(color);
            StandardDraw.Renderer.fillRect((int) x, (int) y, (int) width, (int) height);
            StandardDraw.Renderer.setColor(old);
        }
        else {
            Color old = StandardDraw.Renderer.getColor();
            StandardDraw.Renderer.setColor(color);
            StandardDraw.Renderer.drawRect((int) x, (int) y, (int) width, (int) height);
            StandardDraw.Renderer.setColor(old);
        }
    }

    /**
     * Draws an ellipse at x, y, dim width,height, Color color to the screen.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color - if null, defaults to black.
     * @param fill - if true, will fill the ellipse with color. Otherwise, will
     * draw the outline only
     */
    public static void ellipse (double x, double y, double width, double height, Color color, boolean fill) {
        if (color == null) {
            color = Color.black;
        }
        if (fill) {
            Color old = StandardDraw.Renderer.getColor();
            StandardDraw.Renderer.setColor(color);
            StandardDraw.Renderer.fillOval((int) x, (int) y, (int) width, (int) height);
            StandardDraw.Renderer.setColor(old);
        }
        else {
            Color old = StandardDraw.Renderer.getColor();
            StandardDraw.Renderer.setColor(color);
            StandardDraw.Renderer.drawOval((int) x, (int) y, (int) width, (int) height);
            StandardDraw.Renderer.setColor(old);
        }
    }

    /**
     * Draws the supplied object to the screen. Calls obj.render(...)
     *
     * @param obj
     */
    public static void Object (StandardGameObject obj) {
        obj.render(StandardDraw.Renderer);
    }

    /**
     * Renders all elements within a standard handler.
     *
     * @param handler
     */
    public static void Handler (StandardHandler handler) {
        handler.render(StandardDraw.Renderer);
    }

    /**
     * Generates a random color.
     *
     * @return new randomized color (0-255, 0-255, 0-255).
     */
    public static Color getRandomColor () {
        return new Color(StdOps.rand(0, 255), StdOps.rand(0, 255), StdOps.rand(0, 255));
    }

    public static final Color RED = Color.RED;
    public static final Color PINK = new Color(255, 192, 203);
    public static final Color SALMON_PINK = new Color(255, 145, 164);
    public static final Color CORAL_PINK = new Color(248, 131, 121);
    public static final Color SALMON = new Color(250, 128, 114);
    public static final Color RED_PANTONE = new Color(237, 41, 57);
    public static final Color RED_CRAYOLA = new Color(238, 32, 77);
    public static final Color SCARLET = new Color(255, 36, 0);
    public static final Color RED_IMPERIAL = new Color(237, 41, 57);
    public static final Color INDIAN_RED = new Color(205, 92, 92);
    public static final Color SPANISH_RED = new Color(230, 0, 38);
    public static final Color DESIRE = new Color(234, 60, 83);
    public static final Color LUST = new Color(230, 32, 32);
    public static final Color CARMINE = new Color(150, 0, 24);
    public static final Color RUBY = new Color(224, 17, 95);
    public static final Color CRIMSON = new Color(220, 20, 60);
    public static final Color RUSTY_RED = new Color(218, 44, 67);
    public static final Color FIRE_ENGINE_RED = new Color(206, 32, 41);
    public static final Color CARDINAL_RED = new Color(196, 30, 58);
    public static final Color CHILI_RED = new Color(226, 61, 40);
    public static final Color CORNELL_RED = new Color(179, 27, 27);
    public static final Color FIRE_BRICK = new Color(178, 34, 34);
    public static final Color REDWOOD = new Color(164, 90, 82);
    public static final Color OU_CRIMSON_RED = new Color(153, 0, 0);
    public static final Color DARK_RED = new Color(139, 0, 0);
    public static final Color MAROON = new Color(128, 0, 0);
    public static final Color BARN_RED = new Color(124, 10, 2);
    public static final Color TURKEY_RED = new Color(169, 17, 1);

    public static final Color BLUE = Color.BLUE;
    public static final Color BABY_BLUE = new Color(137, 207, 240);
    public static final Color LIGHT_BLUE = new Color(176, 216, 230);
    public static final Color PERIWINKLE = new Color(204, 204, 255);
    public static final Color POWDER_BLUE = new Color(176, 224, 230);
    public static final Color MORNING_BLUE = new Color(141, 163, 153);
    public static final Color BLUE_MUNSELL = new Color(0, 147, 175);
    public static final Color BLUE_PANTONE = new Color(0, 24, 168);
    public static final Color BLUE_CRAYOLA = new Color(31, 117, 254);
    public static final Color BLUE_MEDIUM = new Color(0, 0, 205);
    public static final Color SPANISH_BLUE = new Color(0, 112, 184);
    public static final Color LIBERTY = new Color(84, 90, 167);
    public static final Color EGYPTIAN_BLUE = new Color(16, 52, 166);
    public static final Color ULTRAMARINE = new Color(63, 0, 255);
    public static final Color DARK_BLUE = new Color(0, 0, 139);
    public static final Color RESOLUTION_BLUE = new Color(0, 35, 185);
    public static final Color NAVY_BLUE = new Color(0, 0, 128);
    public static final Color MIDNIGHT_BLUE = new Color(25, 25, 112);
    public static final Color INDEPENDENCE = new Color(76, 81, 109);
    public static final Color SPACE_CADET = new Color(29, 41, 81);
    public static final Color CAROLINA_BLUE = new Color(123, 175, 212);
    public static final Color DUKE_BLUE = new Color(0, 0, 156);

    public static final Color GREEN = Color.GREEN;
    public static final Color ARTICHOKE = new Color(143, 151, 121);
    public static final Color ARTICHOKE_GREEN_PANTONE = new Color(75, 111, 68);
    public static final Color ASPARAGUS = new Color(135, 169, 107);
    public static final Color AVOCADO = new Color(86, 130, 3);
    public static final Color FERN_GREEN = new Color(79, 121, 66);
    public static final Color FERN = new Color(113, 188, 120);
    public static final Color FOREST_GREEN = new Color(34, 139, 34);
    public static final Color HOOKER_GREEN = new Color(73, 121, 107);
    public static final Color JUNGLE_GREEN = new Color(41, 171, 135);
    public static final Color LAUREL_GREEN = new Color(169, 186, 157);
    public static final Color LIGHT_GREEN = new Color(144, 238, 144);
    public static final Color MANTIS = new Color(116, 195, 101);
    public static final Color MOSS_GREEN = new Color(138, 154, 91);
    public static final Color DARK_MOSS_GREEN = new Color(74, 93, 35);
    public static final Color MYRTLE_GREEN = new Color(49, 120, 115);
    public static final Color MINT_GREEN = new Color(152, 251, 152);
    public static final Color PINE_GREEN = new Color(1, 121, 111);
    public static final Color SAP_GREEN = new Color(0, 158, 96);
    public static final Color IRISH_GREEN = new Color(0, 158, 96);
    public static final Color ST_PATRICK = IRISH_GREEN;
    public static final Color TEA_GREEN = new Color(208, 240, 192);
    public static final Color TEAL = new Color(0, 128, 128);
    public static final Color DARK_GREEN = new Color(0, 100, 0);
    public static final Color GREEN_PANTONE = new Color(0, 173, 131);
    public static final Color GREEN_CRAYOLA = new Color(28, 172, 120);
    public static final Color ARMY_GREEN = new Color(75, 83, 32);
    public static final Color BOTTLE_GREEN = new Color(0, 106, 78);
    public static final Color BRIGHT_GREEN = new Color(102, 255, 0);
    public static final Color BRIGHT_MINT = new Color(79, 255, 176);
    public static final Color BRUNSWICK_GREEN = new Color(27, 77, 62);
    public static final Color CELADON = new Color(173, 255, 175);
    public static final Color DARK_PASTEL_GREEN = new Color(3, 192, 160);
    public static final Color DARTMOUTH_GREEN = new Color(0, 105, 62);
    public static final Color EMERALD = new Color(80, 220, 100);
    public static final Color GREEN_YELLOW = new Color(173, 255, 47);
    public static final Color HARLEQUIN = new Color(63, 255, 0);
    public static final Color HUNTER_GREEN = new Color(53, 94, 59);
    public static final Color INDIA_GREEN = new Color(19, 136, 8);
    public static final Color ISLAMIC_GREEN = new Color(0, 144, 0);
    public static final Color JADE = new Color(0, 168, 107);
    public static final Color KELLY_GREEN = new Color(75, 187, 23);
    public static final Color MIDNIGHT_GREEN = new Color(0, 73, 83);
    public static final Color NEON_GREEN = new Color(57, 255, 20);
    public static final Color OFFICE_GREEN = new Color(0, 128, 0);
    public static final Color PERSIAN_GREEN = new Color(0, 166, 147);

    public static final Color YELLOW = Color.YELLOW;
    public static final Color CREAM = new Color(255, 255, 204);
    public static final Color YELLOW_MUNSELL = new Color(239, 204, 0);
    public static final Color YELLOW_PANTONE = new Color(254, 223, 0);
    public static final Color YELLOW_CRAYOLA = new Color(252, 232, 131);
    public static final Color UNMELLOW_YELLOW = new Color(255, 255, 102);
    public static final Color LEMON = new Color(253, 255, 0);
    public static final Color ROYAL_YELLOW = new Color(250, 219, 94);
    public static final Color GOLD = new Color(255, 215, 0);
    public static final Color CYBER_YELLOW = new Color(255, 211, 0);
    public static final Color SAFETY_YELLOW = new Color(238, 210, 2);
    public static final Color GOLDENROD = new Color(218, 165, 32);

    public static final Color ORANGE = Color.ORANGE;
    public static final Color ORANGE_WHEEL = new Color(255, 127, 0);
    public static final Color DARK_ORANGE = new Color(255, 140, 0);
    public static final Color ORANGE_PANTONE = new Color(255, 88, 0);
    public static final Color ORANGE_CRAYOLA = new Color(255, 117, 56);
    public static final Color PEACH = new Color(255, 229, 180);
    public static final Color LIGHT_ORANGE = new Color(254, 216, 177);
    public static final Color APRICOT = new Color(251, 206, 177);
    public static final Color MELON = new Color(253, 188, 180);
    public static final Color TEA_ROSE = new Color(248, 131, 121);
    public static final Color CARROT_ORANGE = new Color(237, 145, 33);
    public static final Color ORANGE_PEEL = new Color(255, 159, 0);
    public static final Color PRINCETON_ORANGE = new Color(245, 128, 37);
    public static final Color UT_ORANGE = new Color(255, 130, 0);
    public static final Color SPANISH_ORANGE = new Color(232, 97, 0);
    public static final Color PUMPKIN = new Color(255, 117, 24);
    public static final Color VERMILION = new Color(227, 66, 52);
    public static final Color TOMATO = new Color(255, 99, 71);
    public static final Color BURNT_ORANGE = new Color(191, 87, 0);

    public static final Color PURPLE = new Color(128, 0, 128);
    public static final Color ROYAL_PURPLE = new Color(120, 81, 169);
    public static final Color RED_VIOLET = new Color(199, 21, 133);
    public static final Color PURPLE_MEDIUM = new Color(147, 112, 219);
    public static final Color MUNSELL = new Color(159, 0, 197);
    public static final Color MAUVE = new Color(224, 176, 255);
    public static final Color ORCHID = new Color(218, 112, 214);
    public static final Color HELIOTROPE = new Color(223, 115, 255);
    public static final Color PHLOX = new Color(223, 0, 255);
    public static final Color PURPLE_PIZZAZZ = new Color(254, 78, 218);
    public static final Color MULBERRY = new Color(197, 75, 140);
    public static final Color PEARLY_PURPLE = new Color(183, 104, 162);
    public static final Color PURPUREUS = new Color(154, 78, 174);
    public static final Color MARDI_GRAS = new Color(136, 0, 137);
    public static final Color PANSY_PURPLE = new Color(120, 24, 74);
    public static final Color DARK_PURPLE = new Color(48, 25, 52);
    public static final Color VIOLET = new Color(127, 0, 255);
}
