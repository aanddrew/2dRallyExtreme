import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Race extends JComponent 
{
	private Game game;
	private Car car;
	
	private boolean paused;
	public Race(Game gameIn)
	{
		game = gameIn;
		car = new Car();
		paused = false;
		
		addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				System.out.println("Key Pressed: " + KeyEvent.KEY_PRESSED);
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					togglePauseMenu();
				}
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
		this.requestFocusInWindow();
		this.grabFocus();
	}
	
	public void update()
	{
		car.rotate(0.001);
		
		this.repaint();
	}
	
	public void togglePauseMenu()
	{
		JButton exit = new JButton("To Main Menu");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				game.toMainMenu();
			}
			
		});
		if (!paused) 
		{
			this.add(exit);
		}
		else
		{
			this.remove(exit);
		}
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		car.paint(g2d);
	}
}
