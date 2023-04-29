package telran.multithreading.consumer;

import java.util.concurrent.BlockingQueue;

import telran.multithreading.Message;
import telran.multithreading.MessageBox;

public class Reciever extends Thread {
	
	private BlockingQueue<Message> messageBox;
	
	public Reciever(BlockingQueue<Message> messageBox) {
		//setDaemon(true);
		this.messageBox = messageBox;
	}
	
	@Override
	public void run() {
		while(true) {
			Message message;
			try {
				message = messageBox.take();
				System.out.printf("Thread ID: %s; Recive message: %s\n",getId(), message.getMessage());

			} catch (InterruptedException e) {
				
				do {
					message = messageBox.poll();
					if (message != null) {
						System.out.printf("Thread ID: %s; Recive message: %s\n",getId(), message.getMessage());
					} 
				} while (message != null);
				break;
			}
		} 
	}

}
