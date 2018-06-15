
public class RallyRunner 
{	
	public static void main(String[] args) throws InterruptedException
	{
		Game game = new Game();
		
		while (true)
		{
			game.update();
			Thread.sleep(1);
		}
	}
}
