/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revivedstandards.view;

/**
 * Updatable is the interface denoting which objects have the ability to update
 * information about themselves or other objects within their respective class
 * to the game loop.
 */
public interface Updatable
{
    public abstract void tick();
}
