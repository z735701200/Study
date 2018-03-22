package chapter3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class DivTask implements Runnable{
	int a,b;
	public DivTask(int a,int b) {
		this.a = a;
		this.b = b;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		double re = a/b;
		System.out.println(re);
	}
	
	public static void main(String[] args) throws InterruptedException,ExecutionException{
//		ThreadPoolExecutor pools = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
//				0L, TimeUnit.SECONDS, 
//				new SynchronousQueue<Runnable>());
		ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE,
				0L, TimeUnit.SECONDS, 
				new SynchronousQueue<Runnable>());
		for (int i = 0; i < 5; i++) {
//			Future re = pools.submit(new DivTask(100,i));
//			re.get();
			pools.execute(new DivTask(100,i));
		}
	}
}
