
public class Engine 
{
	private double rpm;
	
	public Engine()
	{
		rpm = 0;
	}
	
	public void addRPM(double omega)
	{
		if (rpm + omega > 0)
			rpm += omega;
	}
	
	public double getRPM()
	{
		return rpm;
	}
}
