package chapter2;

public class DaemonDemo {
	public static class DaemonT extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				System.out.println("I am alive");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread t = new DaemonT();
		t.setDaemon(true);
		t.start();
		Thread.sleep(2000);
	}
}
