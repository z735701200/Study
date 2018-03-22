package chapter3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemapDemo implements Runnable {
	final Semaphore semp = new Semaphore(5);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			semp.acquire();
			//模拟耗时操作
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getId()+":done");
			semp.release();
		}catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(20);
		final SemapDemo demo = new SemapDemo();
		for (int i = 0; i < 20; i++) {
			exec.submit(demo);
		}
	}
}
