import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Race extends JPanel 
{
	private Game game;
	private Car car;
	
	private boolean paused;
	
	public Race(Game gameIn)
	{
		game = gameIn;
		car = new Car();
		paused = false;
		
//		togglePauseMenu();
		
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
	
	public void update()
	{
		car.rotate(0.001);
		
		if (paused)
		{
			togglePauseMenu();
		}
		
		this.revalidate();
		this.repaint();
	}
	
	public void togglePauseMenu()
	{
		System.out.println("hi");
		JButton exit = new JButton("To Main Menu");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				game.toMainMenu();
			}
			
		});
		this.add(exit);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		car.paint(g2d);
	}
}
