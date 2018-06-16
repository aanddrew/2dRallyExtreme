package assets.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

/**
 * A rectangle that is able to rotate on its center based on the instance variable angle.
 * @author Andrew Weller andrewweller.cs@gmail.com
 *
 */
public class RotatingRectangle 
{
	private double x;
	private double y;
	
	private double length;
	private double width;
	
	private double angle;
	
	private double diagAngle;
	private double radius;
	
	private Polygon poly;
	private int[] xCoords;
	private int[] yCoords;
	
	public RotatingRectangle(double xIn, double yIn, double lengthIn, double widthIn, double angleIn)
	{
		x = xIn;
		y = yIn;
		length = lengthIn;
		width = widthIn;
		angle = angleIn;
		
		diagAngle = Math.atan(width/length);
		radius = Math.sqrt(Math.pow(width/2, 2) + Math.pow(length/2, 2));
		
		createPoly();
	}
	
	public Point[] getCorners()
	{
		Point[] corners = new Point[4];
		for (int i = 0; i < 4; i++)
		{
			corners[i] = new Point(xCoords[i], yCoords[i]);
		}
		return corners;
	}
	
	public void createPoly()
	{
		xCoords = new int[] {(int) (x + radius*Math.cos(angle+diagAngle)),
				   (int) (x + radius*Math.cos(				  angle+(Math.PI - diagAngle))),
				   (int) (x + radius*Math.cos(				  angle+(Math.PI+diagAngle))),
				   (int) (x + radius*Math.cos(				  angle+(2*Math.PI-diagAngle)))
				   				  };
		yCoords = new int[] {(int) (y + radius*Math.sin(angle+diagAngle)),
				   (int) (y + radius*Math.sin(				  angle+(Math.PI - diagAngle))),
				   (int) (y + radius*Math.sin(				  angle+(Math.PI+diagAngle))),
				   (int) (y + radius*Math.sin(				  angle+(2*Math.PI-diagAngle)))
				   				  };
		//rectangle rotates as instance variable angle increases or decreases
		poly = new Polygon(xCoords, yCoords, 4);
	}
	
	public void paint(Graphics2D g2d)
	{
		createPoly();
		g2d.fillPolygon(poly);
	}
}
