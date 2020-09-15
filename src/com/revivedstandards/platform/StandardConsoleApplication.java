package com.revivedstandards.platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.revivedstandards.util.StandardComplexNumber;

public abstract class StandardConsoleApplication {

	/* BufferedReader for listening for console IO. */
	private final BufferedReader reader;

	public StandardConsoleApplication() {
		this.reader = new BufferedReader(new InputStreamReader(System.in));
		this.run();
	}

	/**
	 * Override this method in the subclass.
	 */
	public abstract void run();

	/**
	 * Reads in an integer from standard input.
	 * 
	 * @return
	 */
	public int readInt() {
		int n = 0;

		try {
			n = Integer.parseInt(this.reader.readLine());
		} catch (ClassCastException | IOException ex) {
			Logger.getLogger(StandardConsoleApplication.class.getName(), null).log(Level.SEVERE, null, ex);
		}

		return n;
	}

	/**
	 * Reads in a line from the standard input as a String.
	 * 
	 * @return
	 */
	public String readLine() {
		String line = null;

		try {
			line = this.reader.readLine();
		} catch (IOException ex) {
			Logger.getLogger(StandardConsoleApplication.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}

		return line;
	}

	/**
	 * Reads in a char from the std. input.
	 * 
	 * @return
	 */
	public char readChar() {
		char c = '\0';

		try {
			c = (char) this.reader.read();
		} catch (IOException ex) {
			Logger.getLogger(StandardConsoleApplication.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}

		return c;
	}

	/**
	 * Reads in a float from standard input.
	 * 
	 * @return
	 */
	public float readFloat() {
		float n = 0;

		try {
			n = Float.parseFloat(this.reader.readLine());
		} catch (NumberFormatException | IOException ex) {
			Logger.getLogger(StandardConsoleApplication.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}

		return n;
	}

	/**
	 * Reads in a double value.
	 * 
	 * @return
	 */
	public double readDouble() {
		double n = 0.0D;

		try {
			n = Double.parseDouble(this.reader.readLine());
		} catch (NumberFormatException | IOException ex) {
			Logger.getLogger(StandardConsoleApplication.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}

		return n;
	}
	
	/**
	 * 
	 * @return
	 */
	public StandardComplexNumber readComplex() {
		Pattern pattern = Pattern.compile("([0-9]+)\\s?([+-])\\s?(-?[0-9])i");
		Matcher matcher = null;
		
		// Verify that we can actually parse this string.
		try {
			String complexString = this.reader.readLine();
			matcher = pattern.matcher(complexString);
			
			if (!matcher.matches()) {
				throw new IllegalArgumentException("Cannot match this as scientific notation!");
			}
			
		} catch (IllegalArgumentException | IOException ex) {
			Logger.getLogger(StandardConsoleApplication.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}
		
		// Pull the tokens from the matcher.
		double real = Double.parseDouble(matcher.group(1));
		String sign = matcher.group(2);
		double imaginary = Double.parseDouble(matcher.group(3));
		
		// If we have a negative as the operator, flip the imaginary number
		// to fit a + bi.
		if (sign.equals("-")) {
			imaginary *= -1;
		}
		
		return new StandardComplexNumber(real, imaginary);
	}

	/**
	 * Reads in a scientific number.
	 * 
	 * @return
	 */
	public double readScientificNumber() {
		String scientificNotation = null;
		Pattern pattern = Pattern.compile("[+-]?[0-9]+[.]?[0-9]*([Ee]-?[0-9]+)?");
		Matcher matcher = null;
		
		try {
			scientificNotation = this.reader.readLine();
			matcher = pattern.matcher(scientificNotation);
			
			if (!matcher.matches()) {
				throw new IllegalArgumentException("Cannot match this as scientific notation!");
			}
			
		} catch (IllegalArgumentException | IOException ex) {
			Logger.getLogger(StandardConsoleApplication.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}

		return Double.parseDouble(scientificNotation);
	}
}
