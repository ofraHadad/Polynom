package myMath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

import javax.management.RuntimeErrorException;

import org.omg.CORBA.Current;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	private final Monom_Comperator comper=new Monom_Comperator();
	private ArrayList <Monom> polynom;

	///////////////////////constructors///////////////////////////////////////////
	/**
	 * The default constructor
	 * Produce the zero polynom
	 */
	public Polynom (){
		polynom=new ArrayList<Monom>();
		Monom zero= new Monom (0,0);
		polynom.add(zero);
	}

	/**
	 *  The copy constructor
	 * Produce a polynom with the same values as p
	 * @param p
	 */
	public Polynom(Polynom p) {
		this.polynom=new ArrayList<Monom>();
		Iterator<Monom> it=p.iteretor() ;//run over p

		while(it.hasNext()) {
			Monom m=new Monom(it.next());
			getPolynom().add(m);

		}
	}

	/**
	 * constructor that get a string
	 * Produce a polynom as the string.
	 * Note:
	 * bad String for input {x^2c","hhh","2*X^7","2*8x","143..0"}
	 * good String for input {"(x^2)","3*x^5+15","15"} 
	 * @param s
	 */
	public Polynom(String s) {
		this.polynom=new ArrayList<Monom>();
		if(s.length()==0) {
			throw new RuntimeException("ERR: invalid input");		
		}
		else {
			initString(s);
		}
	}

	///////////////////////Polynom_able/////////////////////////////////
	@Override
	public double f(double x) {
		double sum=0;
		for(int i=0; i<getPolynom().size();i++ ) {//run over the polynom
			sum+=polynom.get(i).f(x);
		}
		return sum;
	}

	@Override
	public void add(Polynom_able p1) {

		Polynom_able temp= new Polynom();
		temp= p1.copy();

		Iterator<Monom> it=temp.iteretor() ;//run over temp- same as p1
		while(it.hasNext()){//add to this polynom all values in p1
			Monom m=new Monom(it.next());
			add(m);

		}
	}

	@Override
	public void add(Monom m1) {
		Iterator<Monom> it=iteretor() ;//run over this Polynom
		while(it.hasNext()) {
			Monom m2=new Monom(it.next());
			if(m2.get_power()==m1.get_power()) {//find the place to add the Monom
				m2.add(m1);
				it.remove();
				getPolynom().add(m2);
				organizer();
				return;
			}
		}
		getPolynom().add(m1);
		organizer();
	}

	@Override
	public void substract(Polynom_able p1) {
		Polynom_able temp= new Polynom();
		temp= p1.copy(); 

		Iterator<Monom> it=temp.iteretor() ;//run over temp- same as p1
		while(it.hasNext()){//sub p1 from this Polynom
			substract(it.next());
		}
		this.organizer();
	}

	@Override
	public void multiply(Polynom_able p1) {

		Polynom temp=new Polynom();

		Iterator<Monom> it1=iteretor() ;//run over the Polynom
		while(it1.hasNext()) {
			Monom m1=new Monom(it1.next());
			Iterator<Monom> it2=p1.iteretor();//run over p1
			while(it2.hasNext()) {
				Monom tempM= new Monom(m1);
				Monom m2=new Monom(it2.next());
				tempM.multiply(m2);
				temp.add(tempM);
			}		
		}	

		empty();
		Iterator<Monom> it=temp.iteretor() ;//run over temp= p1*polynom
		while(it.hasNext()) {
			Monom m=new Monom(it.next());
			getPolynom().add(m);//put the values of temp in the Polynom
		}			
	}

	@Override
	public boolean equals(Polynom_able p1) {
		int counter=0;
		Iterator<Monom> it1=p1.iteretor() ;//run over p1
		while(it1.hasNext()) {
			counter++;
			it1.next();
		}
		if(counter!=getPolynom().size())return false;//check if they have the same size

		Iterator<Monom> it2=p1.iteretor() ;//run over p1
		Iterator<Monom> it3=iteretor() ;//run over the Polynom
		while(it2.hasNext()) {
			Monom m2= new Monom(it2.next());
			Monom m3= new Monom(it3.next());

			if(!compare(m2,m3))//compare all the monoms in the polynoms(the polynoms organize)
				return false;
		}
		return true;
	}

	@Override
	public boolean isZero() {
		Iterator<Monom> it=this.polynom.iterator() ; {
		};
		while(it.hasNext()) {
			if(!it.next().isZero()) {//check all the monoms if they are zero
				return false;}}

		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {

		if(f(x0)*f(x1)<=0) {//right input (assumed in the function description) 
			double x2=(x1+x0)/2;//the middle between x1 and x0
			if(0-eps<=f(x2) && 0+eps>f(x2)) {//the right x2
				return x2;
			}
			else {
				if((f(x2)>0 && f(x0)>0)||(f(x2)<0 && f(x0)<0)){//f(x2) and f(x0) in the same side of the x axis(x=0)
					return root(x2,x1,eps);
				}
				else {//f(x2) and f(x1) at the same side of the x axis(x=0)
					return root(x0,x2,eps);
				}

			}
		}
		else {
			throw new RuntimeException("ERR: worng input");
		}
	}

	@Override
	public Polynom_able copy() {
		Polynom_able same=new Polynom(this);//produce a polynom_able as the polynom
		return same;	
	}

	@Override
	public Polynom_able derivative() {

		Polynom_able derivative = new Polynom();

		Iterator<Monom> it= iteretor();//run over the polynom
		while (it.hasNext()) {
			Monom m= new Monom(it.next());
			Monom mD= new Monom (m);
			mD= m.derivative();//derivative all the monoms in the polynom
			derivative.add(mD);
		}
		return derivative;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		double area=0;
		if(x0>x1)
			throw new RuntimeException("ERR: invalid input");	
		for(; x0<=x1; x0=x0+eps) {//run from x0 to x1 on the polynom
			if(f(x0)<0) {	//ignore the negative area
			}
			else {
				area+=f(x0)*eps;//Calculates the area
			}
		}
		return area;
	}

	@Override
	public Iterator<Monom> iteretor() {
		return getPolynom().iterator();
	}

	//////////////////////////Polynom only/////////////////////////////////////	
	
	public  double negativearea(double x0, double x1, double eps) {
		double area=0;
		if(x0>x1)
			throw new RuntimeException("ERR: invalid input");	
		for(; x0<=x1; x0=x0+eps) {//run from x0 to x1 on the polynom
			if(f(x0)>0) {	//ignore the positive  area
			}
			else {
				area+=f(x0)*eps;//Calculates the area
			}
		}
		return Math.abs(area);
	}
	
	/**
	 * print the Polynom as a string
	 */
	public String toString() {

		Iterator<Monom> it=iteretor();//run over the polynom

		String ans=""+it.next().toString();

		while(it.hasNext()) {
			ans= ans+" + "+it.next().toString();
		}
		return ans;
	}

	//////////////////////////private functions////////////////////////////////
	/**
	 * return the ArrayList<Monom>
	 * @return
	 */
	private ArrayList<Monom> getPolynom() {
		return polynom;
	}

	/**
	 * sort the polynom by the power of the monom
	 * and delete all the extra zero
	 */
	private void organizer() {
		Iterator<Monom> it=iteretor() ;//run over the polynom
		while(it.hasNext()) {
			if(it.next().isZero()) {//delete all the zeros
				it.remove();
			}
		}
		getPolynom().sort(comper);//sort the polynom

		Iterator<Monom> isEmpty=iteretor() ;//run over the polynom
		if(!isEmpty.hasNext()) {//if the polynom is empty add zero
			Monom zero= new Monom(0,0);
			getPolynom().add(zero);
		}
	}

	/**
	 * Builds a polynom according to the string the user put
	 * @param s
	 */

		private void initString(String s) {
			String temp="";
			temp= temp + s.charAt(0);
			for(int i=1; i<s.length(); i++) {//run over the string and add '+' between the monoms if there is a minus
				if(s.charAt(i)=='-' && s.charAt(i-1)!='(') {
					temp=temp+"+"+s.charAt(i);  
				}

				else {
					temp= temp+s.charAt(i);
				}
			}
			s=temp+"+";
			//delete spaces between the monoms:	
			for(int i=0; i<s.length();) {
				String monom=s.substring(0, s.indexOf('+'));
				for(int j= 0; j<monom.length();j++) {
					if(monom.charAt(j)==' ') {
						monom=	monom.substring(j+1);
					}
					else {
						break;
					}
				}
				for(int j= monom.length()-1; j>-1;j--) {
					if(monom.charAt(j)==' ') {
						monom=	monom.substring(0,j);
					}
					else {
						break;
					}
				}
			//

			s=s.substring(s.indexOf('+')+1, s.length());//split the polynom by the '+'
			Monom m=new Monom(monom);//builds the monom by string
			add(m);
		}
	}

	/**
	 *  Get a Monom and substract it from the polynomial
	 * @param m1
	 */

	private void substract(Monom m1) {
		Iterator<Monom> it=iteretor() ;//run over the Polynom
		while(it.hasNext()) {
			Monom m2=new Monom(it.next());
			if(m2.get_power()==m1.get_power()) {//check where to sub in the Polynom
				m2.sub(m1);
				it.remove();
				this.polynom.add(m2);
				return;
			}
		}
		Monom temp=new Monom(m1.get_coefficient()*-1,m1.get_power());
		getPolynom().add(temp);//add to the list with -
		organizer();
	}

	/**
	 * Get two Monoms and comper them
	 * return true if they are equal, and false otherwise
	 * @param o1
	 * @param o2
	 * @return
	 */
	private boolean compare(Monom o1, Monom o2) {
		//Checks that both the coefficients are equal and also the powers
		if(o1.get_power()-o2.get_power()==0&&o1.get_coefficient()-o2.get_coefficient()==0) {
			return true;
		}
		return false;
	}

	/**
	 * clear the polynom
	 */
	private void empty() {
		Iterator<Monom> remove=iteretor() ;//run over the polynom
		while(remove.hasNext()) {
			remove.next();
			remove.remove();//remove all the Monoms
		}
	}
}