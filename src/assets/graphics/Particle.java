package assets.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

public class Particle 
{
	public static final int SPREAD = 30;
	private double x;
	private double y;
	private double xSpeed;
	private double ySpeed;
	
	public Particle(double xIn, double yIn, double xSpeedIn, double ySpeedIn)
	{
		x = xIn -SPREAD/2 + Math.random() * SPREAD;
		y = yIn -SPREAD/2 + Math.random() * SPREAD;
		xSpeed = xSpeedIn;
		ySpeed = ySpeedIn;
	}
	
	public void update()
	{
		x += xSpeed;
		y += ySpeed;
		xSpeed *= 0.99;
		ySpeed *= 0.99;
	}
	
	public boolean isDead()
	{
		return (xSpeed < 0.01 || ySpeed < 0.01);
	}
	
	public void paint(Graphics2D g2d) 
	{
		g2d.setColor(Color.BLACK);
		g2d.drawRect((int)x, (int)y, 2, 2);
	}
}
