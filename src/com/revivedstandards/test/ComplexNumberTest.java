package com.revivedstandards.test;

import com.revivedstandards.platform.StandardConsoleApplication;
import com.revivedstandards.util.StandardComplexNumber;
import com.revivedstandards.util.StdOps;

public class ComplexNumberTest extends StandardConsoleApplication {

	public ComplexNumberTest() {
		super();
	}
	
	@Override
	public void run() {
		StandardComplexNumber c1 = new StandardComplexNumber(1, 2);
		StandardComplexNumber c2 = new StandardComplexNumber(1, 2);
		
		System.out.println(c1.multiply(c2));
		System.out.println(c1.pow(2));
		
		System.out.println(StdOps.normalize(20, 0, 600));
		
		StandardComplexNumber c3 = readComplex();
		
		System.out.println(c3);
	}

	
	public static void main(String[] args) {
		new ComplexNumberTest();
	}
}
