package telran.multithreading.Truck;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Arrays;

public class Kolhoz {

	private static final int N_TRUCKS = 1000;
	private static final int LOAD = 1;
	private static final int N_RINS = 100000;
	private static Temporal start;

	public static void main(String[] args) {
		
		Truck[] trucks = new Truck[N_TRUCKS];
		startTrucks(trucks);
		start = Instant.now();
		waitTrucks(trucks);
		displayResults();
		

	}

	private static void displayResults() {
		System.out.printf("running time %d\nelevator 1 contains %d tons\nelevator 2 contains %d tons\n", 
				ChronoUnit.MILLIS.between(start, Instant.now()), Truck.getElevator_1(),Truck.getElevator_2());
		
		
	}

	private static void waitTrucks(Truck[] trucks) {
		Arrays.stream(trucks).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
	}

	private static void startTrucks(Truck[] trucks) {
	
		for (int i = 0; i < trucks.length; i++) {
			trucks[i] = new Truck(LOAD, N_RINS);
			trucks[i].start();
		}
		
	}

}
