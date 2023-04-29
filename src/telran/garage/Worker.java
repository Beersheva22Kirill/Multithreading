package telran.garage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Worker extends Thread {
	
	int id;
	ControllerGarage garage;
	long timeNotWork;

	
	public void run() {

		while (true) {
				try {
					Instant startTime = Instant.now();
					Car car = garage.carsForRecovery.take();
					timeNotWork += ChronoUnit.MILLIS.between(startTime,Instant.now());
					System.out.printf("Worker id: %s begin repair of car N %s (time repair: %s)\n", id, car.hashCode() ,car.TimeRecovery );
					sleep(car.TimeRecovery);
					ControllerGarage.setCarRecovery();
					System.out.printf("Worker id: %s end repair of car N %s (time repair: %s)\n", id, car.hashCode() , car.TimeRecovery);
				} catch (InterruptedException e) {
					break;
				}
		
		}
			
	}


	public Worker(int id, ControllerGarage garage) {
		this.id = id;
		this.garage = garage;
	}
}
