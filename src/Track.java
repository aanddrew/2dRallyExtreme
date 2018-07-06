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
	
	private Color carOnColor;

	private BufferedImage image;
	
	public Track(Race raceIn) throws IOException
	{
		race = raceIn;
		
		image = ImageIO.read(new File("Track02.png"));
		x = 0;
		y = 0;
		
		
		acquireCarOnColor();
	}
	
	public void paint(Graphics2D g2d)
	{
		g2d.drawImage(image,(int) x, (int) y, null);
	}

	private void acquireCarOnColor()
	{
		try 
		{
			carOnColor = new Color(image.getRGB(-1*(int)x+race.getWidth()/2, -1*(int)y+race.getHeight()/2));
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			
		}
	}
	
	public double getTraction()
	{
		acquireCarOnColor();
		if (carOnColor.equals(new Color(128,128,128)))
			return 1;

		return 0.5;
	}
	
	public void move(double dX, double dY)
	{
		x += dX;
		y += dY;
	}
}
