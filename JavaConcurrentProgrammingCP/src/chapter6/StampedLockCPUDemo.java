package chapter6;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

public class StampedLockCPUDemo {
	static Thread[] holdCpuThreads = new Thread[3];
	static final StampedLock lock = new StampedLock();
	
	public static void main(String[] args) throws InterruptedException {
		new Thread() {
			@Override
			public void run() {
				long readLong = lock.writeLock();
				LockSupport.parkNanos(600000000000L);
				lock.unlockWrite(readLong);
			}
		}.start();
		Thread.sleep(100);
		for (int i = 0; i < holdCpuThreads.length; i++) {
			holdCpuThreads[i] = new Thread(new HoldCPUReadThread());
			holdCpuThreads[i].start();
		}
		Thread.sleep(1000);
		for (int i = 0; i < holdCpuThreads.length; i++) {
			holdCpuThreads[i].interrupt();
		}
	}
	private static class HoldCPUReadThread implements Runnable{
		@Override
		public void run() {
			long lockr = lock.readLock();
			System.out.println(Thread.currentThread().getName()+" 获得读锁");
			lock.unlockRead(lockr);
		}
	} 
}
