package com.revivedstandards.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.revivedstandards.platform.StandardSwingApplication;

public class SwingTest extends StandardSwingApplication {

  /* Test Swing Panel. */
  private SwingPanel panel;

  /*
   * Instance variable that is incremented via the timer to simulate the
   * animation.
   */
  public int x = 0;

  private SwingTest(int width, int height, int fps, String title) {
    super(width, height, fps, title);

    this.panel = new SwingPanel(this);

    super.addComponent(panel);
  }

  @Override
  public void run() {
  }

  public static void main(String[] args) {
    new SwingTest(800, 800, 60, "Swing Test.");
  }

  /**
   * This class is a JPanel to test the paintComponent method, since you can't
   * draw directly to the JFrame. Normally, a separate component like a JPanel
   * would be its own individual class, not listed as private. This is just for
   * demonstrative purposes.
   * 
   * @author Joshua
   */
  private final class SwingPanel extends JPanel {
    private final SwingTest test;
    private JButton jbutton;

    public SwingPanel(SwingTest test) {
      this.test = test;
      this.jbutton = new JButton("Test button.");
      this.setLayout(new BorderLayout());

      super.add(this.jbutton, BorderLayout.NORTH);

      // Adds an action listener to quit the program
      // when the button is clicked.
      this.jbutton.addActionListener(ex -> {
        this.test.setRunning(false);
      });
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(Color.RED);
      g.drawRect(test.x, 0, x, 200);
    }
  }
}
