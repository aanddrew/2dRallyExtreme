
public class RallyRunner 
{	
	public static void main(String[] args) throws InterruptedException
	{
		Game game = new Game();
		System.out.println("this is a test commit");
		while (true)
		{
			game.update();
			Thread.sleep(1);
		}
	}
}
