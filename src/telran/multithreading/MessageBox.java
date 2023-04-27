package telran.multithreading;

public class MessageBox {
	
	Message message;
	
	synchronized public String take(long idThread) throws InterruptedException {
			while (!getPermission(idThread)) {
				wait();
			}
			String res = message.getMessage();
			message = null;
			notifyAll();

		return res;
	}
	
	synchronized private boolean getPermission(long idThread) {
		boolean res = false;
				if (message != null && idThread % 2 == 0 && message.getNumberMessage() % 2 != 0) {
					res = true;
				} else if (message != null && idThread % 2 != 0 && message.getNumberMessage() % 2 == 0) {
					res = true;
			}
			
		return res;
	}

	synchronized public void put(Message message) throws InterruptedException {
		while (this.message != null) {
			wait();
			
		}
		this.message = message;
		notifyAll();		
	}

	public Message getMessage() {
		return message;
	}
	
	
}
