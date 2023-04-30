package telran.garage;

public class GarageApp {
	
public static final long TIME_WORK = 480;

	public static void main(String[] args) throws InterruptedException {
		
	
		ControllerCar cars = new ControllerCar();
		ControllerGarage garage = new ControllerGarage(cars);
		garage.start();
		
		for (int i = 0; i < TIME_WORK; i++) {
			cars.arrivalCar();
			Thread.sleep(1);
		}
	
		garage.interrupt();
		
		garage.join();
		
		System.out.println("Application closed");
		

	}

}
