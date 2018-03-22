package chapter2;

public class InterruptThreadDemo {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread() {
			@Override
			public void run() {
				while(true) {
					if(Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted");
						break;
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
						System.out.println("Interruted When Sleep");
						Thread.currentThread().interrupt();
					}
					Thread.yield();
				}
			}
		};
		thread.start();
		Thread.sleep(2000);
		thread.interrupt();
	}
}
