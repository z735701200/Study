package chapter3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TraceThreadPoolExecutor extends ThreadPoolExecutor{
	public TraceThreadPoolExecutor(int corePoolSize,int maximumPoolSizze,
			long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSizze, keepAliveTime, unit, workQueue);
	}
	
	@Override
	public void execute(Runnable task) {
		// TODO Auto-generated method stub
		super.execute(wrap(task,clientTrace(),Thread.currentThread().getName()));
	}
	@Override
	public Future<?> submit(Runnable task) {
		// TODO Auto-generated method stub
		return super.submit(wrap(task,clientTrace(),Thread.currentThread().getName()));
	}
	
	private Exception clientTrace() {
		return new Exception("Client stack trace");
	}
	private Runnable wrap(final Runnable task,final Exception clientStack,
			String clientThreadName) {
		return new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					task.run();
				}catch(Exception e) {
					clientStack.printStackTrace();
					throw e;
				}
			}
		};
	}
}
