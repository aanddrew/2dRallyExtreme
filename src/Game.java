import javax.swing.JFrame;

public class Game
{
	private Display disp;
	
	private boolean racing;
	private Race race;
	
	public Game()
	{
		disp = new Display();
		
		toMainMenu();
//		disp.add(new MainMenu(this));
	}
	
	public void update()
	{
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
		
		racing = false;
		disp.getContentPane().removeAll();
		
		disp.add(new MainMenu(this));
		disp.revalidate();
		disp.repaint();
	}
	
	public void toRace()
	{
		System.out.println("hi");
		racing = true;
		disp.getContentPane().removeAll();
		
		race = new Race(this);
		disp.add(race);
		
		disp.revalidate();
		disp.repaint();
	}
	
	public JFrame getFrame() {return ((JFrame) disp);}
}
