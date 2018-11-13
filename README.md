# Polynom:

#Monom

A class that represents the monom function. Monom is a function of the form:
F(x)= a*x^b 
Description

    Class variables:
Double _coefficient: Represents the coefficient of X in the monom
Int _power:Represents the power of X in the monom - must be a natural number
 
    Class functions:
Constructors:

Constructor	Description
Monom(double a, int b)
a constructor that get two parameters create a Monom with this value Note: for b smaller then 0 an exception will occur
Monom(java.lang.String s)	constructor that get a string Produce a Monom as the string.
Monom(Monom ot)
The copy constructor Produce a Monom with the same values as the Monom ot
•	Method Summary
All MethodsInstance MethodsConcrete Methods

Modifier and Type	Method	Description
void	add(Monom m)
add to the Monom the other Monom note: if the powers of the Monoms are different, an exception will occur
Monom
derivative()
take a Monom and return is derivative
double	f(double x)
 Get a x and return the f(x) (from the interface function)
double	get_coefficient()	 Return the monom coefficient
int	get_power()
 Return the Monom power
boolean	isZero()
check if the Monom is zero return true if it is and false if not
void	multiply(Monom m)
multiply the Monom with the input Monom
void	sub(Monom m1)
subtracting the other Monom from the Monom note: if the powers of the Monoms are different, an exception will occur
java.lang.String	toString()
print the Monom as a String









#Polynom

A class that represents the polynomial function. A polynomial is a collection of monomers. Function of form:
F(x)= a(1)*x^b(1) + a(2)*x^b(2) …. +a(n)*x^b(n) 
Description

    Class variables:
ArrayList<Monom> polynom: ArrayList containing monomers - representation of the polynomial

Constructors

Constructor	Description
Polynom()
The default constructor Produce the zero polynom
Polynom(java.lang.String s)
constructor that get a string Produce a polynom as the string.
Polynom(Polynom p)
The copy constructor Produce a polynom with the same values as p
All MethodsInstance MethodsConcrete Methods

Modifier and Type	Method	Description
void	add(myMath.Monom m1)
Add m1 to this Polynom
void	add(myMath.Polynom_able p1)	Add p1 to this Polynom
double	area(double x0, double x1, double eps)	Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps, see: https://en.wikipedia.org/wiki/Riemann_integral
myMath.Polynom_able	copy()
create a deep copy of this Polynum
myMath.Polynom_able	derivative()
Compute a new Polynom which is the derivative of this Polynom
boolean	equals(myMath.Polynom_able p1)	Test if this Polynom is logically equals to p1.
double	f(double x)
 
boolean	isZero()
Test if this is the Zero Polynom
java.util.Iterator<myMath.Monom>	iteretor()
 
void	multiply(myMath.Polynom_able p1)	Multiply this Polynom by p1
double	root(double x0, double x1, double eps)	Compute a value x' (x0<=x'<=x1) for with |f(x')| < eps assuming (f(x0)*f(x1)<=0, returns f(x2) such that: * (i) x0<=x2<=x2 && (ii) f(x2)<eps< div=""> </eps<>
void	substract(myMath.Polynom_able p1)	Subtract p1 from this Polynom
java.lang.String	toString()
print the Polynom as a string

