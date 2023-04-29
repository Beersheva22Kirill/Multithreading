package telran.garage;

public class Car {
	
	private static final long MIN_TIME = 30;
	private static final long MAX_TIME = 480;
	
	int TimeRecovery;

	public Car() {
		TimeRecovery = getTimeRandom();
		
	}

	private int getTimeRandom() {
		
		return (int) (MIN_TIME + Math.random() * (MAX_TIME - MIN_TIME + 1));
	}
	
	
}
