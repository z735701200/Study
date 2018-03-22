package chapter3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RejectThreadPoolDemo {
	public static class MyTask implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println(System.currentTimeMillis()+":Thread ID:" + Thread.currentThread().getId());
			try {
				Thread.sleep(100);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
//	public static void main(String[] args) throws InterruptedException {
//		MyTask task = new MyTask();
//		ExecutorService exec = new ThreadPoolExecutor(5,5,
//				0L,TimeUnit.MILLISECONDS,
//				new LinkedBlockingQueue<Runnable>(10),
//				Executors.defaultThreadFactory(),
//				new RejectedExecutionHandler() {
//					@Override
//					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//						// TODO Auto-generated method stub
//						System.out.println(r.toString()+"is discard");
//					}
//				});
//		for (int i = 0; i < Integer.MAX_VALUE; i++) {
//			exec.submit(task);
//			Thread.sleep(10);
//		}
//	}
	public static void main(String[] args) throws InterruptedException {
		MyTask task = new MyTask();
		ExecutorService exec = new ThreadPoolExecutor(5,5,
				0L,TimeUnit.MILLISECONDS,
				new SynchronousQueue<Runnable>(),
				new ThreadFactory() {
					
					@Override
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						t.setDaemon(true);
						System.out.println("create "+t);
						return t;
					}
				});
		for (int i = 0; i < 5; i++) {
			exec.submit(task);
		}
		Thread.sleep(2000);
	}
}
