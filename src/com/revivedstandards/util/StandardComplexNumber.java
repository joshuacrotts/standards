package com.revivedstandards.util;

public class StandardComplexNumber {

  public static final StandardComplexNumber ZERO = new StandardComplexNumber(0, 0);
  public static final StandardComplexNumber ONE = new StandardComplexNumber(1, 0);
  public static final int I = -1; // i^2 = -1

  private double real;
  private double imaginary;

  /**
   * 
   * @param real
   * @param imaginary
   */
  public StandardComplexNumber(double real, double imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }

  /**
   * 
   * @param c
   * @return
   */
  public StandardComplexNumber add(StandardComplexNumber c) {
    double real = this.real + c.real;
    double imaginary = this.imaginary + c.imaginary;

    return new StandardComplexNumber(real, imaginary);
  }

  /**
   * 
   * @param c
   * @return
   */
  public StandardComplexNumber subtract(StandardComplexNumber c) {
    double real = this.real - c.real;
    double imaginary = this.imaginary - c.imaginary;

    return new StandardComplexNumber(real, imaginary);
  }

  /**
   * 
   * @param c
   * @return
   */
  public StandardComplexNumber multiply(StandardComplexNumber c) {
    double real = ((this.real * c.real) + ((this.imaginary * c.imaginary) * I));
    double imaginary = ((this.real * c.imaginary) + (this.imaginary * c.real));

    return new StandardComplexNumber(real, imaginary);
  }

  /**
   * 
   * @param c
   * @return
   */
  public StandardComplexNumber divide(StandardComplexNumber c) {
    return this.multiply(c.reciprocal());
  }

  /**
   * 
   * @return
   */
  public StandardComplexNumber reciprocal() {
    double scale = this.real * this.real + this.imaginary * this.imaginary;
    return new StandardComplexNumber(this.real / scale, this.imaginary / scale);
  }

  /**
   * 
   * @param n
   * @return
   */
  public StandardComplexNumber pow(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Cannot compute a negative exponent!");
    } else if (n == 0) {
      return ONE;
    } else if (n == 1) {
      return this;
    }

    StandardComplexNumber complex = new StandardComplexNumber(this.real, this.imaginary);

    for (int i = 2; i <= n; i++) {
      complex = complex.multiply(this);
    }

    return complex;
  }

  /**
   * 
   * @return
   */
  public StandardComplexNumber conjugate() {
    return new StandardComplexNumber(this.real, -this.imaginary);
  }

  /**
   * 
   * @return
   */
  public StandardComplexNumber abs() {
    double absReal = Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    return new StandardComplexNumber(absReal, 1);
  }

  public double getReal() {
    return this.real;
  }

  public double getImaginary() {
    return this.imaginary;
  }

  /**
   * 
   */
  @Override
  public String toString() {
    return this.real + " + " + this.imaginary + "i";
  }
}
