import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Animator 
{
	private Race race;
	private Car car;
	
	public Animator(Race raceIn)
	{
		race = raceIn;
		car = raceIn.getCar();
	}
	
	public void drawTachometer(Graphics2D g2d)
	{
		//background arc
		g2d.setStroke(new BasicStroke(10));
		g2d.setColor(Color.BLACK);
		g2d.drawArc(50, race.getHeight()-250, 200, 200, -120, -300);
		//blue filling arc
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(new Color(100,255,255));
		g2d.drawArc(50, race.getHeight()-250, 200, 200, -120, (int)(-300*(car.getEngine().getRPM()/(car.getRedLine()+2000))));
		
		g2d.setColor(new Color(255, 0, 0));
		g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
		for (int i = 0; i <= (car.getRedLine()+2000)/1000; i++)
		{
			g2d.drawString(Integer.toString(i), (int)(145+ 75*Math.cos(2*Math.PI/3 + ((5*Math.PI/3)*(i*1000/(car.getRedLine()+2000))))), 
				(int)(race.getHeight()-145 + 75*Math.sin(2*Math.PI/3 + (5*Math.PI/3)*(i*1000/(car.getRedLine()+2000)))));
		}
		
		//the needle
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine(150, race.getHeight()-150, (int)(150+ 100*Math.cos(2*Math.PI/3 + ((5*Math.PI/3)*(car.getEngine().getRPM()/(car.getRedLine()+2000))))), 
				(int)(race.getHeight()-150 + 100*Math.sin(2*Math.PI/3 + (5*Math.PI/3)*(car.getEngine().getRPM()/(car.getRedLine()+2000)))));
	}
	
	public void drawGear(Graphics2D g2d)
	{
		g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
		g2d.setColor(Color.BLACK);
		g2d.drawRect(145-15, race.getHeight()-50-25, 40, 40);
		g2d.setColor(Color.RED);
		g2d.drawString(Integer.toString(car.getGear() + 1), 145, race.getHeight()-50);
//		((Graphics) g2d).drawString(Integer.toString(car.getGear() + 1), 100, 100);
	}
	
	public void drawSpeedometer(Graphics2D g2d)
	{
		//background arc
		g2d.setStroke(new BasicStroke(10));
		g2d.setColor(Color.BLACK);
		g2d.drawArc(race.getWidth()-250, race.getHeight()-250, 200, 200, -120, -300);
		//blue filling arc
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(new Color(100,255,255));
		g2d.drawArc(race.getWidth()-250, race.getHeight()-250, 200, 200, -120, (int)(-300*(car.getSpeedMPH()/car.getTopSpeed())));
		
		g2d.setColor(new Color(255, 0, 0));
		g2d.setFont(new Font("Calibri", Font.PLAIN, 10));
		for (int i = 0; i <= car.getTopSpeed(); i+=10)
		{
			g2d.drawString(Integer.toString(i), (int)(race.getWidth() -157+ 80*Math.cos(2*Math.PI/3 + ((5*Math.PI/3)*(i/car.getTopSpeed())))), 
				(int)(race.getHeight()-150 + 80*Math.sin(2*Math.PI/3 + (5*Math.PI/3)*(i/car.getTopSpeed()))));
		}
		
		//the needle
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine(race.getWidth()-150, race.getHeight()-150, (int)(race.getWidth() - 150+ 100*Math.cos(2*Math.PI/3 + ((5*Math.PI/3)*(car.getSpeedMPH()/car.getTopSpeed())))), 
				(int)(race.getHeight()-150 + 100*Math.sin(2*Math.PI/3 + (5*Math.PI/3)*(car.getSpeedMPH()/car.getTopSpeed()))));
		
//		System.out.println(car.getLinSpeed()*1000 * Race.FEET_PER_PIXEL * 3600/5280);
	}
	
	public void drawPedals(Graphics2D g2d)
	{
		g2d.setColor(Color.BLACK);
		if (car.getClutchIn()){g2d.fillRect(300, race.getHeight()-50, 50, -20);}
		else {g2d.fillRect(300, race.getHeight()-50, 50, -100);	}
	}
}
