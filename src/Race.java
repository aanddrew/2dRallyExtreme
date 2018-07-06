import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import assets.graphics.Particle;

public class Race extends JPanel 
{	
	public static final double FEET_PER_PIXEL = 20.0/40.0;
	public static final double RPM_FIX = 10;
	public static final double SPEED_FIX = 5;
	
	private Game game;
	private Car car;
	
	private Track track;
	
	private ArrayList<JComponent> pauseMenu;
	private boolean paused;
	
	private ArrayList<Particle> particles;
	private Animator animator;
	
	public Race(Game gameIn) throws IOException
	{
		game = gameIn;
		car = new Car(this);
		paused = false;
		pauseMenu = new ArrayList<JComponent>();
		initializePauseMenu();
		
		track = new Track(this);
		
		particles = new ArrayList<Particle>();
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
	
	public double getTraction()
	{
		return track.getTraction();
	}
	
	public void addParticle(Particle particle)
	{
		particles.add(particle);
//		System.out.println(particles.size());
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
	
	public void moveTrack(double dX, double dY)
	{
		track.move(dX,dY);
	}
	
	public Car getCar() {return car;}
	public JFrame getFrame() {return game.getFrame();}
	
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
		
		for (int i = 0; i < particles.size(); i++)
		{
			particles.get(i).update();
			particles.get(i).paint(g2d);
			if(particles.get(i).isDead())
			{
				particles.remove(i);
				i--;
			}
		}
		
		car.paint(g2d);
		drawHud(g2d);
	}
}
