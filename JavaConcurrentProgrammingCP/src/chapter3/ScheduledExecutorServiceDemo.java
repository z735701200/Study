package chapter3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceDemo {
	public static void main(String[] args) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
					System.out.println(System.currentTimeMillis()/1000);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 0, 2, TimeUnit.SECONDS);
	}
}
