package telran.multithreading.producer;

import telran.multithreading.Message;
import telran.multithreading.MessageBox;

public class Sender extends Thread {

	private MessageBox messageBox;
	private int nMasseges;

	public Sender(MessageBox messageBox, int nMasseges) {
		this.messageBox = messageBox;
		this.nMasseges = nMasseges;
	}
	
	@Override
	public void run() {
		for (int i = 1; i <= nMasseges; i++) {
			try {
				messageBox.put(new Message("Send of producer N: " + i, (long) i));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

}
