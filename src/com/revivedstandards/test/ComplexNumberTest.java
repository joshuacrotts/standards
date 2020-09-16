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
    StandardComplexNumber n1 = readComplex();
    StandardComplexNumber n2 = readComplex();
    
    System.out.println(n1.add(n2));
  }

  public static void main(String[] args) {
    new ComplexNumberTest();
  }
}
