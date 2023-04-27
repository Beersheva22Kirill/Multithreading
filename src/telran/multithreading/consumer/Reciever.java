package telran.multithreading.consumer;

import telran.multithreading.Message;
import telran.multithreading.MessageBox;

public class Reciever extends Thread {
	
	private MessageBox messageBox;
	
	public Reciever(MessageBox messageBox) {
		//setDaemon(true);
		this.messageBox = messageBox;
	}
	
	@Override
	public void run() {
		while(true) {
			Message message;
			try {
				message = messageBox.take(getId());
				System.out.printf("Thread ID: %s; Recive message: %s\n",getId(), message.getMessage());

			} catch (InterruptedException e) {
				
				do {
					message = messageBox.getMessage(getId());
					if (message != null) {
						System.out.printf("Thread ID: %s; Recive message: %s\n",getId(), message.getMessage());
					} 
				} while (message != null);
				break;
			}
		} 
	}

}
