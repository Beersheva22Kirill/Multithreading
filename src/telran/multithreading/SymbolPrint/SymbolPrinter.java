package telran.multithreading.SymbolPrint;

import javax.print.attribute.standard.NumberUpSupported;

public class SymbolPrinter extends Thread {
	
	String string;
	int interval;
	boolean stop;
	
	public SymbolPrinter(String string, int interval) {
		this.string = string;
		this.interval = interval;
		this.stop = false;
	}
	
	@Override
	public void run() {
		char[] chars = string.toCharArray();
		int index = 0;
		while(true) {
			while(true) {
				System.out.print(chars[index]);
				try {
					sleep(interval);
				} catch (InterruptedException e) {
					break;
				}
			}	
			index = index < chars.length -1 ? ++index :  0;			
			if (stop) {	break; }
		}
	}
	

}
