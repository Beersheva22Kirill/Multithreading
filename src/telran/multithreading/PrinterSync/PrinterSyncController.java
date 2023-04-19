package telran.multithreading.PrinterSync;

import java.util.ArrayList;
import java.util.Scanner;

public class PrinterSyncController {
	
	public static void main(String[] args) throws InterruptedException {
		
		Scanner scanner = new Scanner(System.in);
		ArrayList<PrinterSync> printers = new ArrayList<>();
		
 		System.out.println("Enter count of Printer");
 		int countPr = scanner.nextInt();
 		
		for (int i = 0; i < countPr; i++) {
			printers.add(new PrinterSync(i + 1, i + 1, 10));
		}
		
		for (int i = 0; i < countPr; i++) {
			if (i != countPr - 1) {
				printers.get(i).setNextPrinter(printers.get(i + 1));	
			} else {
				printers.get(i).setNextPrinter(printers.get(0));
			}
			printers.get(i).start();	
		}
		
		printers.get(0).interrupt();
		
		for (PrinterSync printerSync : printers) {
			printerSync.join();
		}
		System.out.println("End printing");

	}

}
