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
	private CarProperties props;
	
	public static final double TURN_SPEED = 0.005;
	public static final double MAX_TURN = Math.PI/4;
	public static final double ACCEL = 0.5;
	public static final double BRAKE_CONST = 0.999;
	public static final double TOP_SPEED = 250.0;
	
	public static final double RED_LINE = 6000;
	public static final double[] GEAR_RATIOS = new double[] {4,2,1, 0.75, 0.5};
	
	private double redLine;
	private double topSpeed;
	private double[] gears;
	public int gear;
	
	private double x;
	private double y;
	private double angle;
	
	private double xSpeed;
	private double ySpeed;
	
	private Color color;
	
	private double length;
	private double width;
	
	private double turningAngle;
	
	private RotatingRectangle body;
	private RotatingRectangle wheelWells;
	private Tire[] tires;
	private Engine engine;
	
	private boolean turningRight;
	private boolean turningLeft;
	private boolean accelerating;
	private boolean braking;
	
	private boolean clutchIn;
	
	private double driveSpeed;
	
	public Car()
	{
		redLine = RED_LINE;
		gears = GEAR_RATIOS;
		gear = 0;
		
		x = 500;
		y = 350;
		angle = Math.PI/2;
		
		xSpeed = 0;
		ySpeed = 0;
		
		color = Color.RED;
		length = 40;
		width = 25;
		
		body = new RotatingRectangle(x,y,length,width, angle);
		wheelWells = new RotatingRectangle(x,y, length *0.7, width, angle);
		tires = new Tire[4];
		engine = new Engine();
		
		clutchIn = false;
		
		topSpeed = TOP_SPEED;
		driveSpeed = 0;
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_A) turningLeft = true;
		if (e.getKeyCode() == KeyEvent.VK_D) turningRight = true;
		if (e.getKeyCode() == KeyEvent.VK_W) accelerating = true;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) braking = true;
		
		if (e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_TAB) clutchIn = true; 
	}
	
	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_A) turningLeft = false;
		if (e.getKeyCode() == KeyEvent.VK_D) turningRight = false;
		if (e.getKeyCode() == KeyEvent.VK_W) accelerating = false;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) braking = false;
		
		if (e.getKeyCode() == KeyEvent.VK_Z) 
		{
			clutchIn = false;
			if (gear > 0) gear--;
		}
		if (e.getKeyCode() == KeyEvent.VK_Q)
		{
			clutchIn = false;
			if (gear < gears.length-1) gear++;
		}
	}
	
	public void rotate(double dTheta)
	{
		angle += dTheta;
	}
	
	public void update()
	{
		//steering mechanics
		if (turningRight && turningAngle < Math.PI/4) turningAngle += TURN_SPEED;
		else if (!turningRight && turningAngle > 0) turningAngle -= TURN_SPEED/2;
		
		if (turningLeft  && turningAngle > -1*Math.PI/4) turningAngle -= TURN_SPEED;
		else if (!turningLeft && turningAngle < 0) turningAngle += TURN_SPEED/2;
		
		if (accelerating) engine.addRPM(ACCEL);
		else engine.addRPM(-ACCEL*3);
		
		//get the old angle to use to get the angle diff
		double oldAngle = angle *1;
		
		angle += turningAngle * getLinSpeed() * 0.01;
		
		xSpeed += Math.cos(angle-Math.PI) * engine.getRPM() * 0.0000001 * getGearRatio();
		ySpeed -= Math.sin(angle) * engine.getRPM() * 0.0000001 * getGearRatio();
		
		//get the difference after the angle is rotated
		double angleDiff = angle - oldAngle;
		
		xSpeed = xSpeed * Math.cos(angleDiff) - ySpeed * Math.sin(angleDiff);
		ySpeed = xSpeed * Math.sin(angleDiff) + ySpeed * Math.cos(angleDiff);
		
		if (braking)
		{
			xSpeed *= BRAKE_CONST;
			ySpeed *= BRAKE_CONST;
		}
		
		x += xSpeed;
		y += ySpeed;
		
		if (Math.abs(turningAngle) < 0.0025) turningAngle = 0;
//		System.out.println(1/Math.tan(turningAngle));
//		System.out.println(turningAngle);
//		System.out.println(1/Math.sqrt(1/Math.tan(turningAngle)));
	}
	
	public double getLinSpeed()
	{
		return Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2));
	}
	
	public double getSpeedMPH()
	{
		return this.getLinSpeed()*1000 * Race.FEET_PER_PIXEL * 3600/5280;
	}

	public void paint(Graphics2D g2d)
	{	
		body = new RotatingRectangle(x,y,length,width, angle);
		wheelWells = new RotatingRectangle(x,y, length *0.7, width, angle);
		
		g2d.setColor(Color.BLACK);
		for (int i = 0; i < wheelWells.getCorners().length; i++)
		{
			tires[i] = new Tire(this, i);
			Point point = wheelWells.getCorners()[i];
			
			if (i == 1 || i == 2)
				(new RotatingRectangle(point.getX(), point.getY(), 10,5, angle + turningAngle)).paint(g2d);
			else
				(new RotatingRectangle(point.getX(), point.getY(), 10,5, angle)).paint(g2d);
		}
		
		g2d.setColor(Color.BLACK);
		for (Tire tire : tires)
		{
//			tire.paint(g2d);
		}
		
		g2d.setColor(color);
		body.paint(g2d);
	}
	
	public double getRedLine() {return redLine;}
	public Engine getEngine() {return engine;}
	public double getGearRatio() {return gears[gear];}
	public int getGear() {return gear;}
	public double getTopSpeed() {return topSpeed;}
}