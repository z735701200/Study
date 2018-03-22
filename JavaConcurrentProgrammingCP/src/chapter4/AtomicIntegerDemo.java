package chapter4;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
	static AtomicInteger i = new AtomicInteger();
	public static class AddThread implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int k = 0; k < 10000; k++) {
				i.incrementAndGet();
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread[] ts = new Thread[10];
		for (int k = 0; k < 10; k++) {
			ts[k] = new Thread(new AddThread());
		}
		for (int i = 0; i < ts.length; i++) {
			ts[i].start();
		}
		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}
		System.out.println(i);
	}
}
