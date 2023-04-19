package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PrinterController {

	public static void main(String[] args) throws InterruptedException {
		Printer printer1 = new Printer("String for printer 1", 10);
		Printer printer2 = new Printer("String for printer 2", 16);
		Instant start = Instant.now();
		
		printer1.start();
		printer2.start(); //waiting for printer 2 termination
		printer1.join(); //waiting for printer 1 termination
		printer2.join();
		
		System.out.printf("Time of running %d milisecond \n", ChronoUnit.MILLIS.between(start, Instant.now()));

	}

}
