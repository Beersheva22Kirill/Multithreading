package telran.garage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Worker extends Thread {
	
	int id;
	ControllerGarage garage;
	boolean endWork;
	long nonWorkingTime;

	
	public void run() {

		while (!endWork) {
			Car car;
				try {
					Instant startTime = Instant.now();
					car = garage.carsForRecovery.take();
					nonWorkingTime += ChronoUnit.MILLIS.between(startTime,Instant.now());
					carRecovery(car);
				} catch (InterruptedException e) {
					while (!endWork) {
						car = garage.carsForRecovery.poll();
						if (car != null) {
							try {
								carRecovery(car);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						} else {
							endWork = true;
						} 
					}
					
				}
		
		}
		System.out.printf("Worker ID: %s finished work\n", id);
			
	}


	private void carRecovery(Car car) throws InterruptedException {
		System.out.printf("Worker id: %s BEGIN repair of car N %s (time repair: %s)\n", id, car.hashCode() ,car.TimeRecovery );
		sleep(car.TimeRecovery);
		garage.setCarRecovery();
		System.out.printf("Worker id: %s END repair of car N %s (time repair: %s)\n", id, car.hashCode() , car.TimeRecovery);
	}


	public Worker(int id, ControllerGarage garage) {
		this.id = id;
		this.garage = garage;
		endWork = false;
	}
}
