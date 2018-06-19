import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenu extends JPanel
{
	public static final Dimension BUTTON_SIZE = new Dimension(200,60);
	
	public MainMenu(Game game)
	{
		super();
		
		//title card
		JLabel title = new JLabel(Display.GAME_TITLE, SwingConstants.CENTER);
		title.setFont(this.getFont().deriveFont(36.0f));
		title.setPreferredSize(new Dimension(game.getFrame().getWidth(), 150));
		this.add(title);
		
		//menu buttons
		JButton play = new JButton("Play");
		play.setPreferredSize(BUTTON_SIZE);
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					game.toRace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton chooseCar = new JButton("Choose Car");
		chooseCar.setPreferredSize(BUTTON_SIZE);
		
		JButton chooseTrack = new JButton("Choose Track");
		chooseTrack.setPreferredSize(BUTTON_SIZE);
		
		this.add(play);
		this.add(chooseCar);
		this.add(chooseTrack);
	}
}
