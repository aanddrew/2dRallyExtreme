import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Race extends JPanel 
{
	private Game game;
	private Car car;
	
	private ArrayList<JComponent> pauseMenu;
	private boolean paused;
	
	public Race(Game gameIn)
	{
		game = gameIn;
		car = new Car();
		paused = false;
		pauseMenu = new ArrayList<JComponent>();
		initializePauseMenu();
		
		addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				System.out.println("Key Pressed: " + e.getKeyChar());
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) paused = !paused;
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				// TODO Auto-generated method stub
			}
			@Override
			public void keyTyped(KeyEvent e) 
			{
				// TODO Auto-generated method stub
			}
		});
		
		this.setFocusable(true);
	}
	
	private void initializePauseMenu() 
	{
		JButton exit = new JButton("To Main Menu");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				game.toMainMenu();
			}
			
		});
		pauseMenu.add(exit);
		
		for (JComponent comp : pauseMenu)
		{
			this.add(comp);
		}
	}

	public void update()
	{
		car.rotate(0.001);
		
		showPauseMenu(paused);
		
		this.revalidate();
		this.repaint();
	}
	
	public void showPauseMenu(boolean visible)
	{
		for (JComponent comp : pauseMenu)
		{
			comp.setVisible(visible);
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		car.paint(g2d);
	}
}
