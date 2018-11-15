package myMath;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MonomTest {

	@Test
	void testMonomDoubleInt() {
		Monom m=new Monom(2.2,2);
		assertEquals(2, m.get_power());
		assertEquals(2.2, m.get_coefficient());
	}

	@Test
	void testMonomDoubleInt_negativePower() {


		try {
			Monom m=new Monom(2.2,-2);
			fail("the monom get invalid input in the power");
		}
		catch(Exception e) {

		}
	}
	@Test
	void testMonomMonom() {
		Monom m=new Monom(5,3);
		Monom same=new Monom(m);
		assertEquals(m.get_coefficient(), same.get_coefficient());
		assertEquals(m.get_power(), same.get_power());

	}

	@Test
	void testMonomString_xDoesnotExist() {
		String goodInput []= {"-1.2","2"};
		Monom m0=new Monom(-1.2,0);
		Monom m1=new Monom(2,0);
		Monom [] arrM= {m0,m1};
for(int i=0; i<arrM.length;i++) {
	Monom m=new Monom(goodInput[i]);
	assertEquals(arrM[i].get_coefficient(), m.get_coefficient());
	assertEquals(arrM[i].get_power(), m.get_power());
}
	}
	@Test
	void testMonomString_xDoesExist() {
	String goodInput []= {"2x","x","-2*x","(2.2)x^3","x^4"};
	Monom m0= new Monom(2,1);
	Monom m1= new Monom(1,1);
	Monom m2= new Monom(-2,1);
	Monom m3= new Monom(2.2,3);
	Monom m4= new Monom(1,4);
	Monom arrM []= {m0,m1,m2,m3,m4};
	for(int i=0; i<arrM.length;i++) {
		Monom m=new Monom(goodInput[i]);
		assertEquals(arrM[i].get_coefficient(), m.get_coefficient());
		assertEquals(arrM[i].get_power(), m.get_power());
	}}
	
	@Test
	void testMonomString_badInput() {
		String badInput []= {"2..2","2x^mm","2x^-8","x^3.3","5^2"};
		for(int i=0; i<badInput.length;i++) {
			try {
				Monom m=new Monom(badInput[i]);
				fail("Problem with constructor of string the input is:"+badInput[i]);
			}
			catch(Exception e){
				
			}
		}
	
	
	
	
	
	
	}

	@Test
	void testF() {
		Monom m=new Monom(1,3);
		double x=3;
		assertEquals(27, m.f(x));
	}

	@Test
	void testDerivative() {
		Monom m=new Monom(3,5);
		Monom m_derivative=m.derivative();
		assertEquals(15, m_derivative.get_coefficient());
		assertEquals(4, m_derivative.get_power());
	}

	@Test
	void testAdd() {
		Monom m0=new Monom(4,2);
		Monom m1=new Monom(5,2);
		m0.add(m1);
		assertEquals(9, m0.get_coefficient());
		assertEquals(2, m0.get_power());
	}
	@Test
	void testAdd_diffrentPower() {
		Monom m0=new Monom(4,3);
		Monom m1=new Monom(5,2);
		try {
			m0.add(m1);
			fail("the add function doesn't work well");
		}
		catch(Exception e) {

		}

	}


	@Test
	void testMultiply() {
		Monom m0=new Monom(3,5);
		Monom m1=new Monom(4,2);
		m1.multiply(m0);
		assertEquals(12, m1.get_coefficient());
		assertEquals(7, m1.get_power());
	}


	@Test
	void testToString() {
		Monom m= new Monom(2,5);
		assertEquals("(2.0)x^5", m.toString());
	}
	@Test
	void testToString_monomZero() {
		Monom m= new Monom(0,5);
		assertEquals("0", m.toString());
	}

}
