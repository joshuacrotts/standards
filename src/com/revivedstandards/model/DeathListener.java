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

/**
 * DeathListener allows the user to control what happens when a SGO "dies". A
 * flag should be set in the subclass (trust me I tried to do this with event
 * listeners to no avail) called aliveFlag or something to that effect.
 */
public interface DeathListener {

    /**
     * Method to be overridden in the implementing class. Once the entities is
     * in a "death" state, this method should be called but only once via some
     * extra: 
     * 
     * if (deathState) {
     *      uponDeath() 
     * } 
     * 
     * deathState = false; 
     *
     * This is useful for if the enemy/object disperses particles, drops items,
     * etc upon their "death".
     */
    public abstract void uponDeath();
}
