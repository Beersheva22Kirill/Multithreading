package telran.multithreading.PrinterSync;

public class PrinterSync extends Thread{
	
	int name;
	int numberForPrint;
	int count;
	int portion;
	PrinterSync nextPrinter;
	
	
	public PrinterSync(int name, int numberForPrint, int portion) {
		this.name = name;
		this.numberForPrint = numberForPrint;
		this.portion = portion;
		this.count = 0;	

	}
	
	@Override
	public void run() {	
		while (true) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					if (count < 100) {
						printPortion();
						nextPrinter.interrupt();
					} else {
						nextPrinter.interrupt();
						break;
					}	
				}
			}
		}		
	}

	private void printPortion() {
		for (int i = 0; i < portion; i++) {
			System.out.print(numberForPrint + " ");
		}
		System.out.print(" printer N "+ name + "\n");
		count += portion;	
	}

	public void setNextPrinter(PrinterSync nextPrinter) {
		this.nextPrinter = nextPrinter;
	}

}
