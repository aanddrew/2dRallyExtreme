import java.awt.BasicStroke;
import java.awt.Color;
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
		
		g2d.setColor(Color.ORANGE);
		for (int i = 0; i < (car.getRedLine()+2000)%1000; i++)
		{
			g2d.drawString(Integer.toString(i), (int)(150+ 70*Math.cos(2*Math.PI/3 + ((5*Math.PI/3)*(i*1000/(car.getRedLine()+2000))))), 
				(int)(race.getHeight()-150 + 70*Math.sin(2*Math.PI/3 + (5*Math.PI/3)*(i*1000/(car.getRedLine()+2000)))));
		}
		
		//the needle
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine(150, race.getHeight()-150, (int)(150+ 100*Math.cos(2*Math.PI/3 + ((5*Math.PI/3)*(car.getEngine().getRPM()/(car.getRedLine()+2000))))), 
				(int)(race.getHeight()-150 + 100*Math.sin(2*Math.PI/3 + (5*Math.PI/3)*(car.getEngine().getRPM()/(car.getRedLine()+2000)))));
	}
}
