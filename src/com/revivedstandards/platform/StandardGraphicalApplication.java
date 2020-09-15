package com.revivedstandards.platform;

public abstract class StandardGraphicalApplication extends StandardGame {

  public StandardGraphicalApplication(int width, int height, String title) {
    super(width, height, title);
    super.printFramesToConsole(false);
    super.printFramesToTitle(false);
    super.StartGame();
  }
}
