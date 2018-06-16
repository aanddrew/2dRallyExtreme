import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;

import assets.graphics.RotatingRectangle;

/**
 * A car.
 * @author Andrew Weller andrewweller.cs@gmail.com
 */
public class Car 
{
	public static final double TURN_SPEED = 0.005;
	
	private double x;
	private double y;
	private double angle;
	
	private Color color;
	
	private double length;
	private double width;
	
	private double turningAngle;
	
	private RotatingRectangle body;
	private RotatingRectangle wheelWells;
	
	private boolean turningRight;
	private boolean turningLeft;
	
	public Car()
	{
		x = 500;
		y = 350;
		angle = Math.PI/2;
		
		color = Color.RED;
		length = 40;
		width = 25;
		
		body = new RotatingRectangle(x,y,length,width, angle);
		wheelWells = new RotatingRectangle(x,y, length *0.7, width, angle);
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_A) turningLeft = true;
		if (e.getKeyCode() == KeyEvent.VK_D) turningRight = true;
	}
	
	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_A) turningLeft = false;
		if (e.getKeyCode() == KeyEvent.VK_D) turningRight = false;
	}
	
	public void rotate(double dTheta)
	{
		angle += dTheta;
	}
	
	public void update()
	{
		if (turningRight) turningAngle += TURN_SPEED;
		if (turningLeft ) turningAngle -= TURN_SPEED;
	}
	
	public void paint(Graphics2D g2d)
	{	
		body = new RotatingRectangle(x,y,length,width, angle);
		wheelWells = new RotatingRectangle(x,y, length *0.7, width, angle);
		
		g2d.setColor(Color.BLACK);
		for (int i = 0; i < wheelWells.getCorners().length; i++)
		{
			Point point = wheelWells.getCorners()[i];
			
			if (i == 1 || i == 2)
				(new RotatingRectangle(point.getX(), point.getY(), 10,5, angle + turningAngle)).paint(g2d);
			else
				(new RotatingRectangle(point.getX(), point.getY(), 10,5, angle)).paint(g2d);
		}
		
		g2d.setColor(color);
		body.paint(g2d);
	}
}