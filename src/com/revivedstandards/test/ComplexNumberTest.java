package com.revivedstandards.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.revivedstandards.platform.StandardConsoleApplication;
import com.revivedstandards.util.StandardComplexNumber;
import com.revivedstandards.util.StdOps;

public class ComplexNumberTest extends StandardConsoleApplication {

  private static final int WIDTH = 600;
  private static final int HEIGHT = 600;

  private static final double MIN_COMPLEX = -2.0;
  private static final double MAX_COMPLEX = 2.0;

  public ComplexNumberTest() {
    super();
  }

  @Override
  public void run() {

  }

  /**
   * 
   * @param n
   * @return
   */
  private static BufferedImage mandelbrot(int n) {
    int[] colorMap = generateColorMap(n);

    BufferedImage fractal = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

    for (int x = 0; x < WIDTH; x++) {
      for (int y = 0; y < HEIGHT; y++) {

        int iterations = 0;
        double xc = StdOps.normalize(x, 0, WIDTH, MIN_COMPLEX, MAX_COMPLEX);
        double yc = StdOps.normalize(y, 0, HEIGHT, MIN_COMPLEX, MAX_COMPLEX);

        StandardComplexNumber z = new StandardComplexNumber(xc, yc);

        // Uncomment the line below this one to use a Julia Set.
        StandardComplexNumber c = new StandardComplexNumber(xc, yc);
        // StandardComplexNumber c = new StandardComplexNumber(-0.8, 0.156);

        while (z.abs().getReal() <= 2.0 && iterations < n) {
          z = z.pow(2).add(c);
          iterations++;
        }

        Color black = Color.BLACK;

        if (iterations < n)
          fractal.setRGB(x, y, colorMap[iterations]);
        else
          fractal.setRGB(x, y, black.getRGB());

      }
    }

    return fractal;
  }

  /**
   * 
   * @param n
   * @return
   */
  private static int[] generateColorMap(int n) {
    int[] colorMap = new int[n];

    for (int i = 0; i < n; i++) {
      colorMap[i] = Color.HSBtoRGB(i / 256f, 1, i / (i + 8f));
    }

    return colorMap;
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    new ComplexNumberTest();

    try {
      BufferedImage img = mandelbrot(5000);
      File output = new File("mandelbrot.png");
      ImageIO.write(img, "png", output);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
