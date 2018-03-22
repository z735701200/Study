package chapter2;

public class BadSuspend {
	public static Object u = new Object();
	static ChangeObjectThread t1 = new ChangeObjectThread("t1");
	static ChangeObjectThread t2 = new ChangeObjectThread("t2");
	public static class ChangeObjectThread extends Thread{
		public ChangeObjectThread(String name) {
			// TODO Auto-generated constructor stub
			super.setName(name);
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized(u) {
				System.out.println("in" + getName());
				Thread.currentThread().suspend();
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		t1.start();
		Thread.sleep(100);
		t2.start();
		System.out.println("t1.resume()");
		t1.resume();
		System.out.println("t2.resume()");
		t2.resume();
		t1.join();
		t2.join();
	}
}
