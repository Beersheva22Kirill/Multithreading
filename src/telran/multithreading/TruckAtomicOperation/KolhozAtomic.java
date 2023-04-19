package telran.multithreading.TruckAtomicOperation;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Arrays;

public class KolhozAtomic {

	private static final int N_TRUCKS = 1000;
	private static final int LOAD = 1;
	private static final int N_RINS = 100000;
	private static Temporal start;

	public static void main(String[] args) {
		
		TruckAtomic[] trucks = new TruckAtomic[N_TRUCKS];
		startTrucks(trucks);
		start = Instant.now();
		waitTrucks(trucks);
		displayResults();
		

	}

	private static void displayResults() {
		System.out.printf("running time %d\nelevator 1 contains %d tons\nelevator 2 contains %d tons\n", 
				ChronoUnit.MILLIS.between(start, Instant.now()), TruckAtomic.getElevator_1(),TruckAtomic.getElevator_2());
		
		
	}

	private static void waitTrucks(TruckAtomic[] trucks) {
		Arrays.stream(trucks).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
	}

	private static void startTrucks(TruckAtomic[] trucks) {
	
		for (int i = 0; i < trucks.length; i++) {
			trucks[i] = new TruckAtomic(LOAD, N_RINS);
			trucks[i].start();
		}
		
	}

}
