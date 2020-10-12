import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Test3 {
 
	private static final double EPSILON = 0.0001;
	
	private Polynomial P1;
	private Polynomial P2;

	@Before
	public void setUp() throws Exception {
		 P1 = new PolynomialImp("2x^2+-4x+10");
		 P2 = new PolynomialImp("4x^3+-2x^2+5x+3");
	}

	@Test
	public void testAdd() {
		Polynomial P3 = P1.add(P2);
		Polynomial P4 = new PolynomialImp("4.0x^3+1.0x+13.0");
		System.out.printf("Add-> P3: %s, P4: %s\n", P3.getString(), P4.getString());
		for(int i =0 ; i<P3.getSize();i++) {
			assertTrue(P3.getElement(i).getCoefficient()==(P4).getElement(i).getCoefficient());
			assertTrue(P3.getElement(i).getExponent()==(P4).getElement(i).getExponent());

		}
		
	}

	@Test
	public void testSubtract() {
		Polynomial P3 = P1.subtract(P2);
		Polynomial P4 = new PolynomialImp("-4.0x^3+4.0x^2+-9.0x+7.0");
		System.out.printf("Subtract I-> P1: %s,P2: %s,P3: %s, P4: %s\n,",P1.getString(),P2.getString(),P3.getString(), P4.getString());
		for(int i =0 ; i<P3.getSize();i++) {
			assertTrue(P3.getElement(i).getCoefficient()==(P4).getElement(i).getCoefficient());
			assertTrue(P3.getElement(i).getExponent()==(P4).getElement(i).getExponent());

		}
		
		P3 = new PolynomialImp("0").subtract(P1);
		P4 = new PolynomialImp("-2x^2+4x+-10");
		System.out.printf("Subtract II-> P3: %s, P4: %s\n", P3, P4);
		for(int i =0 ; i<P3.getSize();i++) {
			assertTrue(P3.getElement(i).getCoefficient()==(P4).getElement(i).getCoefficient());
			assertTrue(P3.getElement(i).getExponent()==(P4).getElement(i).getExponent());

		}
		
		
	}

	@Test
	public void testMultiplyPolynomial() {
		Polynomial P3 = P1.multiply(P2);
		Polynomial P4 = new PolynomialImp("8x^5+-20x^4+58x^3+-34x^2+38x+30");
		System.out.printf("Multiply 1-> P3: %s, P4: %s\n", P3.getString(), P4.getString());
		for(int i =0 ; i<P3.getSize();i++) {
			assertTrue(P3.getElement(i).getCoefficient()==(P4).getElement(i).getCoefficient());
			assertTrue(P3.getElement(i).getExponent()==(P4).getElement(i).getExponent());

		}

		P3 = P1.multiply(new PolynomialImp("0"));
		P4 = new PolynomialImp("0");
		System.out.printf("Multiply 2-> P3: %s, P4: %s\n", P3, P4);
		for(int i =0 ; i<P3.getSize();i++) {
			assertTrue(P3.getElement(i).getCoefficient()==(P4).getElement(i).getCoefficient());
			assertTrue(P3.getElement(i).getExponent()==(P4).getElement(i).getExponent());

		}

	}

	@Test
	public void testDerivative() {
		Polynomial P3 = P1.derivative();
		Polynomial P4 = new PolynomialImp("4x+-4");
		System.out.printf("Derivative-> P3: %s, P4: %s\n", P3, P4);
		for(int i =0 ; i<P3.getSize();i++) {
			assertTrue(P3.getElement(i).getCoefficient()==(P4).getElement(i).getCoefficient());
			assertTrue(P3.getElement(i).getExponent()==(P4).getElement(i).getExponent());

		}
	}

	@Test
	public void testIndefiniteIntegral() {
		Polynomial P3 = P2.indefiniteIntegral();
		double c1 = -2.0/3.0, c2=5.0/2.0;
		String strP4 = "x^4+" + c1 + "x^3+" + c2 + "x^2+3x+1";
		Polynomial P4 = new PolynomialImp(strP4);
		System.out.printf("Indefinite Integral-> P3: %s, P4: %s\n", P3, P4);
		for(int i =0 ; i<P3.getSize();i++) {
			assertTrue(P3.getElement(i).getCoefficient()==(P4).getElement(i).getCoefficient());
			assertTrue(P3.getElement(i).getExponent()==(P4).getElement(i).getExponent());

		}

	}

	@Test
	public void testDefiniteIntegral() {
		double number1 = P1.definiteIntegral(2, 9);
		double number2 = 396.666667;
		System.out.printf("Definite Integral-> number1: %f, number2: %f\n", number1, number2);
		double delta = Math.abs(number1 - number2);
		assertTrue(delta < EPSILON);
	}

	@Test
	public void testDegree() {
		int number1 = P2.degree();
		int number2 = 3;
		System.out.printf("Degree -> number1: %d, number2: %d\n", number1, number2);
		assertTrue(number1 == number2);
	}

	@Test
	public void testEvaluate() {
		double number1 = P1.evaluate(5);
		double number2 = 40.0;
		System.out.printf("Evaluate-> number1: %f, number2: %f\n", number1, number2);
		double delta = Math.abs(number1 - number2);
		assertTrue(delta < EPSILON);
	}

	@Test
	public void testMultiplyDouble() {
		Polynomial P3 = P1.multiply(-5);
		Polynomial P4 = new PolynomialImp("-10x^2+20x+-50");
		System.out.printf("Multiply -> P3: %s, P4: %s\n", P3, P4);
		assertTrue(P3.equals(P4));
	}

	@Test
	public void testToString() {
		String string1 = P1.toString();
		String string2 = "2.00x^2+-4.00x+10.00";
		System.out.printf("toString-> string1: %s, string2: %s\n", string1, string2);
		assertTrue(string1.equals(string2));
	}

}
