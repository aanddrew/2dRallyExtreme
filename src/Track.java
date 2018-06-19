import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Track 
{
	private double x;
	private double y;

	private Image image;
	
	public Track() throws IOException
	{
		image = ImageIO.read(new File("Track01.png"));
		x = 0;
		y = 0;
	}
	
	public void paint(Graphics2D g2d)
	{
		g2d.drawImage(image,(int) x, (int) y, null);
	}
}
