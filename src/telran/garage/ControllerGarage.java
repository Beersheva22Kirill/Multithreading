package telran.garage;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ControllerGarage extends Thread{
	
	static final int DEFAULT_MAX_QUEUE = 10;
	static final int DEFAULT_COUNT_WORKERS = 2;
	
	ControllerCar cars;
	BlockingQueue<Car> carsForRecovery;

	ArrayList<Worker> workers;
	int countWorkers;
	long carRecovery;
	long carNotRecovery;
	long allTimeNotWork;
	public static Object mutex = new Object();
	
	@Override
	public void run() {
		
		for (int i = 0; i < countWorkers; i++) {
			workers.add(new Worker(i, this));
			workers.get(i).start();
		}
			while (true) {
				try {
					Car car = cars.cars.take();
						if (!carsForRecovery.offer(car)) {
							System.out.println("Car: " + car.hashCode() + " rejected. Size queue: " +  carsForRecovery.size());
							carNotRecovery++;
						}
				} catch (InterruptedException e) {
					
					for (Worker worker : workers) {
						worker.interrupt();
						}
					for (Worker worker : workers) {
						try {
							worker.join();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					
					System.out.println("\n-----------Result---------------");
					for (Worker worker : workers) {
						allTimeNotWork += worker.nonWorkingTime;
						System.out.printf("Worker ID: %s didn't work %s min\n",worker.id,worker.nonWorkingTime);
					}
					System.out.printf("\nTotal non working time: %s min\n", allTimeNotWork);
					System.out.printf("Total count of repaired cars: %s\n", carRecovery);
					System.out.println("Count of rejected cars: " + carNotRecovery);
					break;
				} 
			}	
			System.out.println("Garage finished work\n");
			 
	}

	public long getCarRecovery() {
		return carRecovery;
	}

	public void setCarRecovery() {
		synchronized (mutex) {
			carRecovery++;
		}
	}

	public ControllerGarage(int max_queue, int count_worker, ControllerCar cars) {
		this.carsForRecovery = new LinkedBlockingQueue<>(max_queue);
		this.carNotRecovery = 0l;
		this.cars = cars;
		this.countWorkers = count_worker;
		this.workers = new ArrayList<>();
	}

	public ControllerGarage(ControllerCar cars) {
		this(DEFAULT_MAX_QUEUE, DEFAULT_COUNT_WORKERS, cars);
	}

}
