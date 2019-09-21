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
 * and we use John Carmack's fast inverse square root function.
 * ===========================================================================
 */
package com.revivedstandards.model;

import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.view.StandardAnimationView;
import java.awt.image.BufferedImage;

/**
 * This class represents the model for an animation.
 */
public final class StandardAnimation {

    //
    //  Underlying parent StandardGameObject
    //
    private final StandardGameObject sgo;

    //
    //  View for the animation; renders the current frame pointed by
    //  frameIndex.
    //
    private final StandardAnimationView view;

    //
    //  Reference to an animation that this animation should
    //  return to upon completion.
    //
    private StandardAnimatorController returnAnimation = null;
    
    //
    //
    //

    // Current frame index in the array of images
    private int frameIndex;

    // Time between the last system call and the current one
    private long lastTime = 0;

    // How many frames of animation should are played have per second
    private double fps;

    // Rotation of the current frame
    private double rotation;

    // Should we mirror the image along the y axis?
    private boolean mirror = false;

    //  Determines where in the sprite sheet the
    //  pointer should START (not continue from). This is mainly for sprite
    //  sheets that share multiple animations to save
    //  memory on not having to rely on various
    //  BI arrays.
    //
    private int startFrameIndex = 0;
    private int endFrameIndex = -1;

    //
    //Once the animation reaches the ending frame,
    //it will hit this frame and repeat from here.
    //
    private int frameHaltPosition = -1;

    public StandardAnimation (StandardGameObject sgo, BufferedImage[] frames, double fps) {
        this.sgo = sgo;
        this.fps = fps;
        this.view = new StandardAnimationView(frames, sgo);

        // By default, the end frame index is just the end of the frame index's array length.
        this.endFrameIndex = frames.length;

        this.setCurrentFrameIndex(0);
        this.setDefaultDimensions();
    }

    public StandardAnimation (StandardGameObject sgo, BufferedImage[] frames, double fps, int frameHaltPosition) {
        this.sgo = sgo;
        this.fps = fps;
        this.view = new StandardAnimationView(frames, sgo);

        // By default, the end frame index is just the end of the frame index's array length.
        this.endFrameIndex = frames.length;

        this.frameHaltPosition = frameHaltPosition;
        this.setCurrentFrameIndex(0);
        this.setDefaultDimensions();
    }

    /**
     * Sets the current frame of animation with the supplied index.
     *
     * Contacts the view to set its current frame bufferedimage variable to
     * point to frameIndex's position in the bufferedimage array.
     *
     * @param frameIndex
     */
    private void setCurrentFrameIndex (int frameIndex) {
        if ((frameIndex < this.startFrameIndex || frameIndex >= this.endFrameIndex) && this.returnAnimation == null) {
            // If we hit the ending frame position and we have a frame halt position,
            // we will reset the frame index pointer to this position.
            if (frameIndex >= this.endFrameIndex && this.frameHaltPosition != -1) {
                this.frameIndex = this.frameHaltPosition;
            }
            else {
                this.frameIndex = this.startFrameIndex;
            }
            this.view.setCurrentFrameIndex(this.frameIndex);
        }
        else if ((frameIndex < this.startFrameIndex || frameIndex >= this.endFrameIndex) && this.returnAnimation != null) {
            this.stopAnimation();
            this.sgo.setAnimation(this.returnAnimation);
        }
        else {
            this.view.setCurrentFrameIndex(frameIndex);
            this.frameIndex = frameIndex;
        }
    }

    /**
     * Advances the current frame of animation to the next one in succession.
     */
    public void advanceFrame () {
        this.setCurrentFrameIndex(this.getCurrentFrameIndex() + 1);
    }

    /**
     * Assigns the default SGO width/height to the width and height of the first
     * frame of animation.
     */
    private void setDefaultDimensions () {
        this.sgo.setWidth(this.getView().getCurrentFrame().getWidth());
        this.sgo.setHeight(this.getView().getCurrentFrame().getHeight());
    }

    /**
     * This method is for assigning end and start indexes for animating
     * different frames contained in the same bufferedimage array.
     *
     * For instance, if the sprite sheet has 4 sprites for each animation set
     * (walking left, walking right, walking up, walking down), this means
     * buffered image indexes 0-3 are reserved for walking left, 4-7 are for
     * walking right, 8-11 are for walking up, and 12-15 are for walking down.
     *
     * However, for each set, the last index is exclusive. Therefore, it should
     * be one higher than what your indexes say. In the above example, to tell
     * the animation to loop through the walking left frames, the function call
     * would be setFramePositions(0, 4), as the algorithm will iterate over
     * frames 0, 1, 2, and 3.
     *
     * @param startIndex
     * @param endIndex
     */
    public void setFramePositions (int startIndex, int endIndex) {
        this.startFrameIndex = startIndex;
        this.endFrameIndex = endIndex;
    }

    /**
     * Resets the current animation to the beginning, also resets the frame
     * index to 0.
     */
    public void stopAnimation () {
        this.frameIndex = this.startFrameIndex;
    }

    public void setReturnAnimation (StandardAnimatorController sac) {
        this.returnAnimation = sac;
    }
    
    public boolean hasReturnAnimation()
    {
        return this.returnAnimation != null;
    }
    
    public StandardGameObject getStandardGameObject () {
        return this.sgo;
    }

    public int getCurrentFrameIndex () {
        return this.frameIndex;
    }

    public StandardAnimationView getView () {
        return this.view;
    }

    public void setFrameSpeed (double fps) {
        this.fps = fps;
    }

    public double getFrameSpeed () {
        return this.fps;
    }

    public long getLastTime () {
        return this.lastTime;
    }

    public void setLastTime (long t) {
        this.lastTime = t;
    }

    public void setRotation (double theta) {
        this.rotation = theta;
    }

    public void setFrameHaltPosition (int frame) {
        this.frameHaltPosition = frame;
    }

    public double getRotation () {
        return this.rotation;
    }

    /**
     * Mirrors the image along the y-axis. BE AWARE, this is very buggy. It is
     * best to just manually create the reversed images.
     *
     * @param mirror
     */
    public void setMirrored (boolean mirror) {
        this.mirror = mirror;
    }

    public boolean isMirrored () {
        return this.mirror;
    }

}
