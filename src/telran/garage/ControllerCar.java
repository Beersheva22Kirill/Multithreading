package telran.garage;

import java.util.concurrent.*;

public class ControllerCar {
	
	private static final int PROBABILITY = 15;
	int probability;
	ControllerGarage garage;
	BlockingQueue<Car> cars;

	public ControllerCar(int probability) {
		this.probability = probability;
		this.cars = new LinkedBlockingQueue<>();
	}
	
	public ControllerCar() {
		this(PROBABILITY);
	}
	
	public Car createCar () {
		Car car = null;
		int num = getRandom(1,100);
		if (num < PROBABILITY) {
			car = new Car();
		}
		return car;
	}
		
	public void arrivalCar() {
			Car car = createCar();
			if (car != null) {
				try {
					System.out.println("Create car: " + car.hashCode());
					cars.put(car);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

	}
	
	private int getRandom(long min, long max) {
		
		return (int) (min + Math.random() * (max - min + 1));
	}

}
