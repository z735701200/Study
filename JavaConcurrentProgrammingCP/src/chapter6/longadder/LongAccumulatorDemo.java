package chapter6.longadder;

import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

public class LongAccumulatorDemo {
	public static void main(String[] args) throws InterruptedException {
		LongAccumulator accumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);
		Thread[] ts = new Thread[1000];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = new Thread(()->{
				Random random = new Random();
				long value = random.nextLong();
				accumulator.accumulate(value);
			});
			
			ts[i].start();
		}
		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}
		System.out.println(accumulator.longValue());
	}
}