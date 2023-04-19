package telran.multithreading.SymbolPrint;

import java.util.Scanner;

public class SymbolPrintController {
	
	public static void main(String[] args) {
		
		SymbolPrinter sPrinter = new SymbolPrinter("!@#$", 2000);
		
		sPrinter.start();
		Scanner scanner = new Scanner(System.in);
		while (!scanner.next().equals("q")) {
			sPrinter.interrupt();
		}
		sPrinter.stop = true;
		sPrinter.interrupt();
	}

}
