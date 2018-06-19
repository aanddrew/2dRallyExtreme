import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Race extends JPanel 
{
	public static final double FEET_PER_PIXEL = 20.0/40.0;
	
	private Game game;
	private Car car;
	
	private Track track;
	
	private ArrayList<JComponent> pauseMenu;
	private boolean paused;
	
	private Animator animator;
	
	public Race(Game gameIn) throws IOException
	{
		game = gameIn;
		car = new Car();
		paused = false;
		pauseMenu = new ArrayList<JComponent>();
		initializePauseMenu();
		
		track = new Track();
		
		animator = new Animator(this);
		
		addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) paused = !paused;
				car.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				car.keyReleased(e);
			}
			@Override
			public void keyTyped(KeyEvent e) 
			{
				
			}
		});
		
		this.setFocusable(true);
	}
	
	private void initializePauseMenu() 
	{
		JButton exit = new JButton("To Main Menu (Exit Race)");
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
		if (!paused)
		{
			car.update();
		}
		
		showPauseMenu(paused);
		
		this.revalidate();
		this.repaint();
	}
	
	public void drawHud(Graphics2D g2d)
	{
		animator.drawTachometer(g2d);
		animator.drawGear(g2d);
		animator.drawSpeedometer(g2d);
		animator.drawPedals(g2d);
		if (paused) {
			g2d.setColor(new Color(0,0,0,128));
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	}
	
	public Car getCar() {return car;}
	
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
		
		track.paint(g2d);
		
		car.paint(g2d);
		drawHud(g2d);
	}
}
