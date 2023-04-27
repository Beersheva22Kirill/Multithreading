package telran.multithreading.consumer;

import telran.multithreading.MessageBox;

public class Reciever extends Thread {
	
	private MessageBox messageBox;
	
	public Reciever(MessageBox messageBox) {
		setDaemon(true);
		this.messageBox = messageBox;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				System.out.printf("Thread ID: %s; Recive message: %s\n",getId(), messageBox.take(getId()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
