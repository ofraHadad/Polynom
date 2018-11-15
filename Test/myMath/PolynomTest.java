package myMath;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class PolynomTest {

	@Test
	void testPolynom() {
		Polynom p=new Polynom();
		Iterator<Monom> it=p.iteretor(); 
		int count=0;
		while(it.hasNext()) {
			count++;
			assertEquals(0, it.next().get_coefficient());
		}
		assertEquals(1, count);
	}

	@Test
	void testPolynomString() {
		Polynom p=new Polynom ("2*x^2+5*x+2");
		assertEquals("(2.0)x^2 + (5.0)x + (2.0)", p.toString());
	}
	@Test
	void testPolynomString_invaildInput() {
		try {
			Polynom p=new Polynom ("2*x^2++5*x+2");
			fail("invailid input");
		}
		catch (Exception e) {

		}
	}


	@Test
	void testPolynomPolynom() {
		Polynom p=new Polynom("2x^2+12");
		Polynom same=new Polynom(p);
		Iterator<Monom> itP=p.iteretor(); 
		Iterator<Monom> itSame=same.iteretor(); 
		while(itSame.hasNext() && itP.hasNext()) {
			Monom mp=new Monom(itP.next());
			Monom msame=new Monom(itSame.next());
			assertEquals(mp.get_coefficient(), msame.get_coefficient());
			assertEquals(mp.get_power(), msame.get_power());
		}
		if(itP.hasNext() || itSame.hasNext()) {
			fail("Problem with constructor of polynom");
		}
	}
	@Test
	void testEqualsPolynom_able() {
		Polynom p0=new Polynom("5x^2+6x^4+x^8+9");
		Polynom p1=new Polynom("5x^2+6x^4+x^8+9");
		Polynom p2=new Polynom("5x^2+6x^4+x^8+8");
		assertEquals(true, p0.equals(p1));
		assertEquals(false, p0.equals(p2));
	}


	@Test
	void testF() {
		Polynom p=new Polynom("2*x^2+3");
		double x=2;
		assertEquals(11, p.f(x));
	}

	@Test
	void testAddPolynom_able() {
		Polynom p0=new Polynom ("2*x^6+x^5");
		Polynom_able p1= new Polynom("2x^7+x^6");
		Polynom_able expected=new Polynom("2x^7+3x^6+x^5");
		p0.add(p1);
		assertEquals(true, p0.equals(expected));	
	}

	@Test
	void testAddMonom() {
		Polynom p=new Polynom ("-2x^2+4x");
		Monom m0=new Monom("2x^2");
		Monom m1=new Monom ("5x^5");
		Polynom_able expected_addm0= new Polynom("4x");
		Polynom_able expected_addm1= new Polynom("4x+5x^5");
		p.add(m0);
		assertEquals(true,p.equals(expected_addm0) );
		p.add(m1);
		assertEquals(true,p.equals(expected_addm1) );
	}

	@Test
	void testSubstract() {
		Polynom p=new Polynom("80x^6-x^2+5");
		Polynom_able p0=new Polynom("40x^6+2x^3+5");
		Polynom_able expected=new Polynom("40x^6-x^2-2x^3");
		p.substract(p0);
		assertEquals(true, p.equals(expected));
	}

	@Test
	void testMultiply() {
		Polynom p0=new Polynom("5x^5+4x^2-2");
		Polynom_able p1= new Polynom("6x^6+2x^2+1");
		Polynom_able expected=new Polynom("30x^11+24x^8+10x^7-12x^6+5x^5+8x^4-2");
		p0.multiply(p1);
		assertEquals(true, p0.equals(expected));

	}

	@Test
	void testIsZero() {
		Polynom p=new Polynom("0");
		assertEquals(true, p.isZero());
	}

	@Test
	void testRoot() {
		Polynom p=new Polynom("-x^3");
		double root=p.root(-9, 1, 0.0001);
		if(0-0.0001<=p.f(root) && p.f(root)<0+0.0001) {

		}
		else {
			fail("Problem with the root function");
		}
	}
	@Test
	void testRoot_Invalidinput() {
		Polynom p=new Polynom("x^2");
		try {
			p.root(-1, 2,0.0001);
			fail("Problem with the root function");
		}
		catch(Exception e){

		}
	}

	@Test
	void testCopy() {
		Polynom p0= new Polynom ("x^7+ 5x+3");
		Polynom_able same= new Polynom();
		same= p0.copy();
		assertEquals(true, p0.equals(same));
	}

	@Test
	void testDerivative() {
		Polynom p0= new Polynom("2x^6+3x^3");
		Polynom_able expected=new Polynom("12x^5+9x^2");
		Polynom_able derivative=new Polynom();
		derivative=p0.derivative();
		assertEquals(true,derivative.equals(expected));
	}

	@Test
	void testArea() {
		Polynom p=new Polynom("x^2+4x^7");
		double area=p.area(1, 2,0.0001);
		double integral= 129.8333333333333;
		if(area<integral-1|| area>integral+1) {
			fail("the area function does not work well");
		}
	}
	@Test
	void testAreaInvailidInput() {
		Polynom p=new Polynom("x^2+4x^7");
		try {
			double area=p.area(2, 1,0.0001);
			fail("the area function does not work well");
		}
		catch(Exception e){

		}
	}

	@Test
	void testToString() {
		Polynom p=new Polynom("2*x^2+5*x+2");
		System.out.println(p);
		assertEquals("(2.0)x^2 + (5.0)x + (2.0)", p.toString());
	}

}
