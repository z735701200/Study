package chapter4;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
	public static class Candidate{
		int id;
		volatile int score;
	}
	public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater 
		=AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
	//检查Upadter是否正常工作
	public static AtomicInteger allScore = new AtomicInteger(0);
	public static void main(String[] args) throws InterruptedException {
		final Candidate stu = new Candidate();
		Thread[] ts = new Thread[10000];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = new Thread() {
				@Override
				public void run() {
					if(Math.random()>0.4) {
						scoreUpdater.incrementAndGet(stu);
						allScore.incrementAndGet();
					}
				}
			};
			ts[i].start();
		}
		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}
		System.out.println("score="+stu.score);
		System.out.println("allScore="+allScore);
	}
	
	
	
}
