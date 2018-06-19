import java.io.IOException;

public class RallyRunner 
{	
	public static void main(String[] args) throws InterruptedException, IOException
	{
		Game game = new Game();
		while (true)
		{
			game.update();
			Thread.sleep(1);
		}
	}
}
