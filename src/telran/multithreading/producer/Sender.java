package telran.multithreading.producer;

import java.util.concurrent.BlockingQueue;

import telran.multithreading.Message;
import telran.multithreading.MessageBox;

public class Sender extends Thread {

	private BlockingQueue<Message> messageBox;
	private int nMasseges;

	public Sender(BlockingQueue<Message> messageBox, int nMasseges) {
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
