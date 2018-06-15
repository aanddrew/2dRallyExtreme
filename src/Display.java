import javax.swing.JFrame;

/**
 * This is the JFrame that the game uses.
 * @author andrew
 */
public class Display extends JFrame
{
	public static final String GAME_TITLE = "2D Rally Extreme";
	
	public Display()
	{
		super(GAME_TITLE);
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.setVisible(true);
	}
}
