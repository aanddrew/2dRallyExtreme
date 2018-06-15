import java.awt.Color;
import java.awt.Graphics2D;

public class Car 
{
	private double x;
	private double y;
	private double angle;
	
	private Color color;
	private int size;
	
	public Car()
	{
		x = 500;
		y = 350;
		angle = Math.PI/2;
		
		color = Color.RED;
		size = 20;
	}
	
	public void rotate(double dTheta)
	{
		angle += dTheta;
	}
	
	public void paint(Graphics2D g2d)
	{
		//get the corners of the car currently
		int[] xCoords = new int[] {(int) (x + size*Math.cos(angle+Math.PI/6)),
				   (int) (x + size*Math.cos(angle+5*Math.PI/6)),
				   (int) (x + size*Math.cos(angle+7*Math.PI/6)),
				   (int) (x + size*Math.cos(angle+11*Math.PI/6))};
		int[] yCoords = new int[] {(int) (y + size*Math.sin(angle+Math.PI/6)),
				   (int) (y + size*Math.sin(angle+5*Math.PI/6)),
				   (int) (y + size*Math.sin(angle+7*Math.PI/6)),
				   (int) (y + size*Math.sin(angle+11*Math.PI/6))};
		//rectangle rotates as instance variable angle increases or decreases
		
		
		g2d.setColor(Color.BLACK);
		for (int i = 0; i < xCoords.length; i++)
		{
			g2d.fillRect(xCoords[i], yCoords[i], 10, 5);
		}
		
		g2d.setColor(color);
		g2d.fillPolygon(xCoords, yCoords, 4);
	}
}
