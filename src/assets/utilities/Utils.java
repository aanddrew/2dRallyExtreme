package assets.utilities;

import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JFrame;

public class Utils 
{
	public static Point getRelMouseLoc(JFrame frame)
	{
		return new Point((int) (MouseInfo.getPointerInfo().getLocation().getX() - frame.getX()),
				(int) (MouseInfo.getPointerInfo().getLocation().getY() - frame.getY()));
	}
}
