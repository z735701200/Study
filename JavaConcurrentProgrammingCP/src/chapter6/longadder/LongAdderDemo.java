package chapter6.longadder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderDemo {
	private final int MAX_THREADS = 3;
	private final int TASK_COUNT = 3;
	private final int TARGET_COUNT = 10000000;
	
	private AtomicLong acount = new AtomicLong(0L);
	private LongAdder lacount = new LongAdder();
	
	private long count = 0;
	
	private CountDownLatch cdlsync = new CountDownLatch(TASK_COUNT);
	private CountDownLatch cdlatomic = new CountDownLatch(TASK_COUNT);
	private CountDownLatch cdladdr = new CountDownLatch(TASK_COUNT);
	
	protected synchronized long inc() {
		return count ++;
	}
	protected synchronized long getCount() {
		return count;
	}
	
	public class SyncThread implements Runnable{
		protected String name;
		protected long starttime;
		LongAdderDemo out;
		public SyncThread(LongAdderDemo o,long starttime) {
			out = o;
			this.starttime = starttime;
		}
		@Override
		public void run() {
			long v = out.getCount();
			while(v<TARGET_COUNT) {
				v = out.inc();
			}
			long endtime = System.currentTimeMillis();
			System.out.println("SyncThread spend:"+(endtime - starttime) +"ms"+" v="+v);
			cdlsync.countDown();
		}
		
	}
	
	public class AtomicThread implements Runnable{
		protected String name;
		protected long starttime;
		public AtomicThread(long starttime) {
			this.starttime = starttime;
		}
		@Override
		public void run() {
			long v = acount.get();
			while(v<TARGET_COUNT) {
				v = acount.incrementAndGet();
			}
			long endtime = System.currentTimeMillis();
			System.out.println("AtomicThread spend:"+(endtime - starttime) +"ms"+" v="+v);
			cdlsync.countDown();
		}
	}
	
	public class LongAddrThread implements Runnable{
		protected String name;
		protected long starttime;
		public LongAddrThread(long starttime) {
			this.starttime = starttime;
		}
		@Override
		public void run() {
			long v = lacount.sum();
			while(v<TARGET_COUNT) {
				lacount.increment();
				v = lacount.sum();
			}
			long endtime = System.currentTimeMillis();
			System.out.println("LongAddrThread spend:"+(endtime - starttime) +"ms"+" v="+v);
			cdlsync.countDown();
		}
	}
	
	private void testSync() throws InterruptedException {
		ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
		long starttime = System.currentTimeMillis();
		SyncThread sync = new SyncThread(this, starttime);
		for (int i = 0; i < TARGET_COUNT; i++) {
			exe.submit(sync);
		}
		cdlsync.await();
		exe.shutdown();
	}
	
	private void testAtomic() throws InterruptedException {
		ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
		long starttime = System.currentTimeMillis();
		AtomicThread atomic = new AtomicThread( starttime);
		for (int i = 0; i < TARGET_COUNT; i++) {
			exe.submit(atomic);
		}
		cdlsync.await();
		exe.shutdown();
	}
	
	private void testAtomicLong() throws InterruptedException {
		ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
		long starttime = System.currentTimeMillis();
		LongAddrThread atomic = new LongAddrThread( starttime);
		for (int i = 0; i < TARGET_COUNT; i++) {
			exe.submit(atomic);
		}
		cdlsync.await();
		exe.shutdown();
	}
	
	public static void main(String[] args) throws InterruptedException {
		LongAdderDemo longAdderDemo = new LongAdderDemo();
//		longAdderDemo.testSync();
//		longAdderDemo.testAtomic();
		longAdderDemo.testAtomicLong();
	}
}
