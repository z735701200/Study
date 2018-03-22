package chapter8;

import java.util.ArrayList;

public class UnsafeArrayList {
	static ArrayList<Object> al = new ArrayList<>();
	static class AdddTask implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
			for (int i = 0; i < 1000000; i++) {
				al.add(new Object());
			}
		}
	}
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new AdddTask(),"t1");
		Thread t2 = new Thread(new AdddTask(),"t2");
		t1.start();
		t2.start();
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
			}
		},"t3");
		t3.start();
	}
}
