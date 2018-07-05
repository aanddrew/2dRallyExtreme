import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;

import assets.graphics.RotatingRectangle;
import assets.utilities.Utils;

/**
 * A car.
 * @author Andrew Weller andrewweller.cs@gmail.com
 */
public class Car 
{
	private Race race;
	private CarProperties props;
	
	public static final double TURN_SPEED = 0.008;
	public static final double MAX_TURN = Math.PI/4;
	public static final double ACCEL = 10;
	public static final double BRAKE_CONST = 0.00025;
	public static final double TOP_SPEED = 250.0;
	
	public static final double RED_LINE = 6000;
	public static final double[] GEAR_RATIOS = new double[] {6.4,3.15,1.75, 1.0, 0.75, 0.5};
	
	private double redLine;
	private double topSpeed;
	private double[] gears;
	public int gear;
	
	private double mouseAngle;
	
	private double brakeAmount;
	private double gasAmount;
	
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
	private boolean justClutched;
	
	public Car(Race raceIn)
	{
		race = raceIn;
		
		redLine = RED_LINE;
		gears = GEAR_RATIOS;
		gear = 0;
		
		x = 500;
		y = 350;
		angle = Math.PI/2;
		mouseAngle = Math.PI/2;
		
		xSpeed = 0;
		ySpeed = 0;
		
		color = Color.RED;
		length = 40;
		width = 25;
		
		brakeAmount = 0.0;
		gasAmount = 0.0;
		
		body = new RotatingRectangle(x,y,length,width, angle);
		wheelWells = new RotatingRectangle(x,y, length *0.7, width, angle);
		tires = new Tire[4];
		engine = new Engine();
		
		clutchIn = false;
		
		topSpeed = TOP_SPEED;
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_A) turningLeft = true;
		if (e.getKeyCode() == KeyEvent.VK_D) turningRight = true;
		if (e.getKeyCode() == KeyEvent.VK_W) accelerating = true;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) braking = true;
		
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) clutchIn = true; 
	}
	
	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_A) turningLeft = false;
		if (e.getKeyCode() == KeyEvent.VK_D) turningRight = false;
		if (e.getKeyCode() == KeyEvent.VK_W) accelerating = false;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) braking = false;
		
		if (e.getKeyCode() == KeyEvent.VK_Z && clutchIn) 
		{
			if (gear > 0) gear--;
		}
		if (e.getKeyCode() == KeyEvent.VK_Q && clutchIn)
		{
			if (gear < gears.length-1) gear++;
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) clutchIn = false; 
	}
	
	public void rotate(double dTheta)
	{
		angle += dTheta;
	}
	
	public void update()
	{
		System.out.println(TURN_SPEED/Math.pow(getLinSpeed(),2));
		//steering mechanics
		if (turningRight && turningAngle < MAX_TURN) turningAngle += TURN_SPEED/Math.pow(getLinSpeed(),2);
		else if (!turningRight && turningAngle > 0) turningAngle -= TURN_SPEED/2;
		
		if (turningLeft  && turningAngle > -1*MAX_TURN) turningAngle -= TURN_SPEED/Math.pow(getLinSpeed(),2);
		else if (!turningLeft && turningAngle < 0) turningAngle += TURN_SPEED/2;
		
		if (accelerating) engine.addRPM(ACCEL*getGearRatio());
		else engine.addRPM(-ACCEL*getGearRatio());
		
		//get the old angle to use to get the angle diff
		double oldAngle = angle *1;
		
		angle += turningAngle * getLinSpeed() * 0.01;
		
		if (!clutchIn) 
		{	
			if (!justClutched)
			{
				double linSpeed = engine.getRPM() /20000 / getGearRatio();
				
				xSpeed = Math.cos(angle-Math.PI) * linSpeed;
				ySpeed = -1*Math.sin(angle) * linSpeed;
			}
			else 
			{
				engine.setRPM(getLinSpeed()* 20000 * getGearRatio());
			}
			
			justClutched = false;
		}
		else {justClutched = true;}
		//get the difference after the angle is rotated
		double angleDiff = angle - oldAngle;
		
		xSpeed = xSpeed * Math.cos(angleDiff) - ySpeed * Math.sin(angleDiff);
		ySpeed = xSpeed * Math.sin(angleDiff) + ySpeed * Math.cos(angleDiff);
		
		if (braking)
		{
			angle += turningAngle *0.001;
			
			if (Math.abs(xSpeed) - BRAKE_CONST < 0)
				xSpeed = 0;
			else
				xSpeed += BRAKE_CONST * Math.cos(angle);
			if (Math.abs(ySpeed) - BRAKE_CONST < 0)
				ySpeed = 0;
			else
				ySpeed += BRAKE_CONST * Math.sin(angle);
		}
		else
		{
			xSpeed *= 0.9999;
			ySpeed *= 0.9999;
			engine.setRPM(engine.getRPM()*0.9999);
		}
		
//		x += xSpeed;
//		y += ySpeed;
		
		race.moveTrack(-1*xSpeed, -1*ySpeed);
		
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
	
	public double getDisplaySpeed()
	{
		return this.getSpeedMPH()/Race.SPEED_FIX;
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
	public boolean getClutchIn() {return clutchIn;}
	public double getXSpeed() {return xSpeed;}
	public double getYSpeed() {return ySpeed;}
}