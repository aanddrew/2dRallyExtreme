import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Track 
{
	private Race race;
	
	private double x;
	private double y;

	private BufferedImage image;
	
	public Track(Race raceIn) throws IOException
	{
		race = raceIn;
		
		image = ImageIO.read(new File("Track02.png"));
		x = 0;
		y = 0;
	}
	
	public void paint(Graphics2D g2d)
	{
		g2d.drawImage(image,(int) x, (int) y, null);
		try
		{
//			System.out.println(-1*x+race.getWidth()/2);
			Color c = new Color(image.getRGB(-1*(int)x+race.getWidth()/2, -1*(int)y+race.getHeight()/2));
//			System.out.println(c.getRed() + " " + c.getGreen() + " " + c.getBlue());
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("null");
		}
	}
	
	public void move(double dX, double dY)
	{
		x += dX;
		y += dY;
	}
}
