
package myMath;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 * @author ofra&&shira
 */

public class Monom implements function{

	private double _coefficient;  
	private int _power;

	////////////////////////////////////constructors////////////////////////////////////

	/**
	 * a constructor that get two parameters
	 * create a Monom with this value   
	 * Note:for b smaller then 0 an exception will occur
	 * @param a the coefficient of the Monom
	 * @param b the power of the Monom
	 */
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
		if(b<0) {//the power of Monom is non negative
			throw new RuntimeException("ERR: power got a negative value");
		}
	}

	/**
	 *  The copy constructor
	 * Produce a Monom with the same values as the Monom ot
	 * @param ot a Monom
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	/**
	 * constructor that get a string
	 * Produce a Monom as the string.
	 * Note: 
	 * @param s string represent a Monom
	 */
	public Monom(String s) {
		if(s.length()==0) {
			throw new RuntimeException("ERR: invalid input");		
		}
		else {
			initString(s);
		}
	}

	//////////////////////////////////////function///////////////////////////////////////////
	@Override
	public double f(double x) {
		return get_coefficient()*(Math.pow(x, get_power()));//a*x^b
	} 

	////////////////////////////////////Monom only//////////////////////////////////////////
	/**
	 * take a Monom and return is derivative
	 * @return Monom derivative
	 */
	public  Monom derivative() {
		Monom derivative=new Monom(this);//copy and make the changes in derivative
		derivative.set_coefficient(this.get_coefficient()*this.get_power());
		derivative.set_power(this.get_power()-1);
		return derivative;

	}
	/**
	 * add to the Monom the other Monom
	 * note: if the powers of the Monoms are different, an exception will occur 
	 * @param m Monom
	 */
	public void add(Monom m) {
		if(m.get_power()!= get_power()) { //adding a Monom with a different power can't be in Monom
			throw new RuntimeException("ERR: ivaled input in Monom  ");
		}

		set_coefficient(get_coefficient()+m.get_coefficient());
	}

	/**
	 * subtracting the other Monom from the  Monom
	 * note: if the powers of the Monoms are different, an exception will occur 
	 * @param m1 Monom
	 */
	public void sub(Monom m1) { //subtracting a Monom with a different power can't be in Monom
		if(m1.get_power()!= get_power()) {
			throw new RuntimeException("ERR: ivaled input in Monom  ");
		}
		else set_coefficient(get_coefficient()-m1.get_coefficient());	
	}

	/**
	 * multiply the Monom with the input Monom
	 */
	public void  multiply(Monom m) {

		set_coefficient(m.get_coefficient()*get_coefficient());
		set_power(m.get_power()+get_power());
	}

	/**
	 * check if the Monom is zero
	 * return true if it is and false if not
	 * @return boolean
	 */
	public boolean isZero() {
		if(get_coefficient()!=0) {//if the coefficient of the Monom is zero, the Monom is zero
			return false;
		}
		return true;
	}

	/**
	 * print the Monom as a String
	 */
	public String toString() {
		if(get_coefficient()==0) {
			return "0";
		}
		if(get_power()==0) {
			return "("+ get_coefficient()+")";
		}
		if(get_power()==1) {
			if(get_coefficient()==1)return "x";
			return "("+ get_coefficient()+")x";
		}
		if(get_coefficient()==1)return "x^"+get_power();
		return "("+get_coefficient()+")x^"+get_power();
	}


	/////////////////////////getters and setters//////////////////////////////////
	public double get_coefficient() {
		return _coefficient;
	}

	public int get_power() {
		return _power;
	}

	private void set_coefficient(double a){
		this._coefficient = a;
	}


	private void set_power(int p) {
		this._power = p;
	}

	////////////////////////////////////private function///////////////////////////////////////
	/**
	 * take a string and make it a Monom
	 * note:
	 * @param s string represent a Monom
	 */
	private void initString(String s) {

		boolean ok=false;//to check if the input is valid
		s=parentheses(s);
		s= s.toLowerCase();

		if(!s.contains("x")) {//when there is no 'x' in the string
			if(isDouble(s)) {//check if it is a number and set the values if it is
				if(!(s.charAt(0)=='-' && s.length()==1)) {
					double coef= Double.parseDouble(s);
					set_coefficient(coef);
					set_power(0);
					return;
				}
			}
		}

		if(s.contains("x")) {//when there is a 'x' in the string
			
			String befor= s.substring(0, s.indexOf('x'));//only the coefficient
			if(befor.length()==0) {//if there is no coefficient
				set_coefficient(1);
				ok=true;
			}

			else {
				if(befor.charAt(befor.length()-1)=='*') {//with '*'
					befor=befor.substring(0, befor.length()-1);
					befor=parentheses(befor);
					if(isDouble(befor)) {//check if it is a number and set the values if it is
						double coef= Double.parseDouble(befor);
						set_coefficient(coef);
						ok=true;
					}	
				}
				else {
					befor=parentheses(befor);
					if(isDouble(befor)) {//check if it is a number and set the values if it is
						if(befor.length()==1 && befor.charAt(0)=='-') {//only a minus before 'x'
							set_coefficient(-1);
							ok=true;
						}
						else {
							double coef= Double.parseDouble(befor);
							set_coefficient(coef);
							ok=true;
						}
					}

				}
			}
			if(s.indexOf('x')+1==s.length()) {//there is no power
				set_power(1);
				if(ok) {
					ok=true;
				}
			}
			else {
				String after= s.substring(s.indexOf('x')+1);//take only the power
				if(ok) {//the coefficient is valid
					if(isNaturalPower(after)) {//check if it is a natural number and set the values if it is
						int power= Integer.parseInt(after.substring(1));
						set_power(power);

					}
					else ok=false;
				}
			}
		}

		if(!ok) {//if all the tests failed throw an exception
			throw new RuntimeException("ERR: invalid input");
		}
	}

	/**
	 * delete valid parentheses
	 * @param s
	 */
	private String parentheses(String s) {
		if( s.length()>2 && s.charAt(0)=='('  && s.charAt(s.length()-1)==')' ) {
			s = s.substring(1,s.length()-1);
		}
		if( s.length()>3 && s.charAt(0)=='-' && s.charAt(1)=='(' && s.charAt(s.length()-1)==')') {
			s= s.charAt(0)+ s.substring(2,s.length()-1);
		}

		return s;
	}

	/**
	 * take a string and check if it is a double
	 * return true if it is and false if not
	 * @param s string 
	 * @return boolean
	 */
	private boolean isDouble(String s) {
		if(s.length()==0) {
			return false;		
		}

		int countPoint=0;
		if(s.length()==1 && s.charAt(0)=='-') {//-1
			return true;
		}
		//the point should not be in the first char or the last
		if((((s.charAt(0)<'0'|| s.charAt(0)>'9') && (s.charAt(0)!='-' )) || (s.charAt(s.length()-1)<'0' || s.charAt(s.length()-1)>'9'))) {
			return false;
		}

		for(int i=1; i<s.length()-1; i++) {//run over the rest of the string
			if((s.charAt(i)<'0'|| s.charAt(i)>'9')&&s.charAt(i)!='.') {//confirm that there is only numbers and points in the string
				return false;
			}
			if(s.charAt(i)=='.') {//count the points
				countPoint++;
			}
		}
		if (countPoint>1)return false;//there should be only one point

		return true;
	}

	/**
	 * take a string and check if it is a natural power
	 * return true if it is and false if not
	 * @param s string
	 * @return boolean
	 */
	private boolean isNaturalPower(String s) {
		if(s.charAt(0)!='^') {//the first char should be '^'
			return false;
		}
		for(int i=1; i<s.length(); i++) {//run over the string and check that there is only numbers
			if((s.charAt(i)<'0'|| s.charAt(i)>'9')) {
				return false;
			}
		}
		return true;
	}
}