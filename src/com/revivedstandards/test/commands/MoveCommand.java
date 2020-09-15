package com.revivedstandards.test.commands;

import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.test.objects.AnimatedPlayerGameObject;

public class MoveCommand extends Command {

  public AnimatedPlayerGameObject player;
  public StandardAnimatorController animator;
  public float deltaX;
  public float deltaY;

  public MoveCommand(AnimatedPlayerGameObject sgo, StandardAnimatorController sa, float deltax, float deltay) {
    this.player = sgo;
    this.animator = sa;
    this.deltaX = deltax;
    this.deltaY = deltay;
  }

  @Override
  public void pressed(float delta) {
    // Allows for the SGO to have multiple SAC's
    this.player.setAnimation(this.animator);
    this.player.setMoving(true);

    if (this.animator != null) {
      this.animator.getStandardAnimation().advanceFrame();
    }
    this.player.setVelX(this.player.getVelX() + this.deltaX);
    this.player.setVelY(this.player.getVelY() + this.deltaY);
  }

  @Override
  public void released(float delta) {
    this.player.getAnimationController().stopAnimation();
    this.player.setMoving(false);
    this.player.setVelX(0);
    this.player.setVelY(0);
  }
}
