import java.io.IOException;

import javax.swing.JFrame;

/**
 * The main Game which contains the JFrame and the menus along with the race.
 * @author Andrew Weller andrewweller.cs@gmail.com
 */
public class Game
{
	private Display disp;
	
	private boolean racing;
	private Race race;
	
	public Game()
	{
		disp = new Display();
		
		toMainMenu();
	}
	
	public void update()
	{
		//if the game is currently in a race, then update the damn race
		if (racing)
		{
			try
			{
				race.update();
			}
			catch (NullPointerException e)
			{
				
			}
		}
	}
	
	public void toMainMenu()
	{
		//clears the pane and adds a
		racing = false;
		disp.getContentPane().removeAll();
		
		disp.add(new MainMenu(this));
		disp.revalidate();
		disp.repaint();
	}
	
	public void toRace() throws IOException
	{
		System.out.println("Entering Race!");
		racing = true;
		disp.getContentPane().removeAll();
		
		race = new Race(this);
		disp.add(race);
		//aha this line is so important do not delete, it brings the focus of the JFrame to the race
		disp.getContentPane().transferFocus();
		
		disp.revalidate();
		disp.repaint();
		
		race.repaint();
	}
	
	public JFrame getFrame() {return ((JFrame) disp);}
}
