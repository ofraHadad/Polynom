package myMath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.List;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointData;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

import javax.swing.JFrame;

/**
 * this class draw a Polynom graph.
 * the main draw this function:0.2x^4-1.5x^3+3.0x^2-x-5
 * and calculate the area between the function and the x-axis  
 * we used the Gral library, which we found on github 
 * @author ofra&shira 
 *
 */
public class PolynomDraw extends JFrame {

/**
 * this contractor create a graph of Polynom
 */
	public PolynomDraw(Polynom  p, double x0, double x1,double eps) {
		//we took some of the code from the gral project in github and merge it with are Polynom code
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);

		DataTable data = new DataTable(Double.class, Double.class); //we 
		DataTable dataExtreme = new DataTable(Double.class, Double.class);
		Polynom_able der= new Polynom(p);//the derivative of the Polynom
		der=p.derivative();

		XYPlot plot = new XYPlot(data,dataExtreme);
		double start=x0-eps;
		for (double x = x0; x < x1; x+=eps) {//run over the Polynom
			double y = p.f(x);
			if(der.f(x)*der.f(start)>0) {//when the derivative of the Polynom change from minus to pulse, the Polynom have an extreme point
				data.add(x, y);//most of the Polynom points
			}
			else {
				dataExtreme.add(x,y);//the extreme points
				if(der.f(start)<0) {
					System.out.println("min("+x+","+y+")");
				}
				else {
					System.out.println("max("+x+","+y+")");
				}
				start=x;
				
			}

		}

		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderers(data, lines);
		Color color = new Color(0.0f, 0.3f, 1.0f, 0.3f);

		plot.getLineRenderers(data).get(0).setColor(color);



		PointRenderer points1 = new DefaultPointRenderer2D();
		points1.setShape(new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
		points1.setColor(color.pink);
		plot.setPointRenderers(dataExtreme, points1);

		PointRenderer points2 = new DefaultPointRenderer2D();
		points2.setShape(new Rectangle2D.Double(-2.5, -2.5, 5.0, 5.0));
		points2.setColor(color.black);
		plot.setPointRenderers(data, points2);

		getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(getContentPane().getMinimumSize());
		setSize(504, 327);
	




	}

/**
 * draw this function:0.2x^4-1.5x^3+3.0x^2-x-5
 * and calculate the area between the function and the x-axis
 * @param args
 */

	public static void main(String[] args) {
		Polynom p= new Polynom("0.2x^4-1.5x^3+3.0x^2-x-5"); 
		PolynomDraw frame = new PolynomDraw(p,-2,6,0.01);
		frame.setVisible(true);
		double area= p.negativearea(-2,6,0.01);
		System.out.println("the area Above the function and under the X axis: "+area);
	}
}
