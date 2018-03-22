package chapter2;

public class AccountingSync implements Runnable{
	static AccountingSync instance = new AccountingSync();
	static volatile int i = 0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int j = 0; j < 10000000; j++) {
			synchronized(instance) {
				i++;
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}
}
