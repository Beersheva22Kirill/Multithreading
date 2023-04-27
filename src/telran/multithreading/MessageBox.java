package telran.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import telran.multithreading.producer.Sender;

public class MessageBox {
	
	Message message;
	Lock lock = new ReentrantLock();
	Condition waitingConsumer = lock.newCondition();
	Condition waitingProducer = lock.newCondition();
	
	public Message take(long idThread) throws InterruptedException {
		lock.lock();	
		try {
			while(message == null) {
				waitingConsumer.await();
			}
			Message res = message;
			message = null;
			waitingProducer.signal();
			return res;
		} finally {
			lock.unlock();
		}
			
	}
	
	private boolean getPermission(long idThread) {
		boolean res = false;
				if (message != null && idThread % 2 == 0 && message.getNumberMessage() % 2 != 0) {
					res = true;
				} else if (message != null && idThread % 2 != 0 && message.getNumberMessage() % 2 == 0) {
					res = true;
			}
			
		return res;
	}

	public void put(Message message) throws InterruptedException {
		lock.lock();	
		try {
			while(this.message != null) {
				waitingProducer.await();
			}
			this.message = message;
			waitingConsumer.signal();
		} finally {
			lock.unlock();
		}
	}

	public Message getMessage(long idThread) {
		lock.lock();
		try {
			Message res = message;
			message = null;
			if (res != null) {
				waitingProducer.signal();
			}
			return res;
		} finally {
			lock.unlock();
		}
		
	}
	
}
