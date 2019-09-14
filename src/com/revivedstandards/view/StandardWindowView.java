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
package com.revivedstandards.view;

import com.revivedstandards.main.StandardGame;
import com.revivedstandards.util.StdOps;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import javax.swing.JFrame;

/**
 * This class is the class that instantiates the Screen. It takes four
 * parameters: width, height, a title, and the Game class itself that will
 * automatically be added to the FRAME as a component due to it extending class
 * once it is instantiated.
 */
public class StandardWindowView extends Canvas {

    private final String ICON = "src/res/img/stds_icon.png";
    private final JFrame FRAME;
    private String title;

    private int width;
    private int height;

    public StandardWindowView (int width, int height, String title, StandardGame game, GraphicsConfiguration gc) {
        this.FRAME = new JFrame(title, gc);

        this.width = width;
        this.height = height;
        this.title = title;

        this.FRAME.setMinimumSize(new Dimension(width, height));
        this.FRAME.setMaximumSize(new Dimension(width, height));
        this.FRAME.setPreferredSize(new Dimension(width, height));
        this.FRAME.getContentPane().setSize(new Dimension(width, height));
        this.FRAME.setIconImage(StdOps.loadImage(this.ICON));

        this.FRAME.setResizable(false);
        this.FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.FRAME.setLocationRelativeTo(null);

        this.FRAME.add(game);
        this.FRAME.pack();

        this.FRAME.setVisible(true);

    }

    public StandardWindowView (int width, int height, String title, Object game) {
        this.FRAME = new JFrame();

        this.FRAME.setTitle(title);
        this.title = title;

        // Sets dimensions
        this.width = width;
        this.height = height;

        // Sets frame information
        this.FRAME.setMinimumSize(new Dimension(width, height));
        this.FRAME.setMaximumSize(new Dimension(width, height));
        this.FRAME.setPreferredSize(new Dimension(width, height));
        this.FRAME.getContentPane().setSize(new Dimension(width, height));
        this.FRAME.setIconImage(StdOps.loadImage(this.ICON));
        this.FRAME.setResizable(false);
        this.FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.FRAME.setLocationRelativeTo(null);

        this.FRAME.add((Component) game);
        this.FRAME.pack();

        this.FRAME.setVisible(true);

    }

    public void setBackgroundColor (Color color) {
        this.FRAME.setBackground(color);
    }

    /**
     * @return the provided width of the frame.
     */
    public int width () {
        return width;
    }

    /**
     *
     * @return the provided height of the frame.
     */
    public int height () {
        return height;
    }

    public JFrame getFrame () {
        return FRAME;
    }

    public void setWidth (short width) {
        this.width = width;
    }

    public void setHeight (short height) {
        this.height = height;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.FRAME.setTitle(title);
    }

}
