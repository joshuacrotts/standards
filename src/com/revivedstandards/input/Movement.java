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
package com.revivedstandards.input;

import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.model.StandardGameObject;

public class Movement extends Command {

    public StandardGameObject player;
    public StandardAnimatorController animator;
    public float deltaX;
    public float deltaY;

    public Movement(StandardGameObject sgo, StandardAnimatorController sa, float deltax, float deltay) {
        this.player = sgo;
        this.animator = sa;
        this.deltaX = deltax;
        this.deltaY = deltay;
    }

    @Override
    public void pressed(float delta) {
        if (this.animator != null) {
            this.animator.getStandardAnimation().advanceFrame();
        }
        this.player.setVelX(this.player.getVelX() + this.deltaX);
        this.player.setVelY(this.player.getVelY() + this.deltaY);
    }
}
