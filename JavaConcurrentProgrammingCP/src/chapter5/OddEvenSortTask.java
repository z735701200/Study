package chapter5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OddEvenSortTask implements Runnable{
	public static void bubbleSort(int[] arr) {
		for (int i = arr.length - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}
	public static void oddEvenSort(int[] arr) {
		int exchFlag = 1,start = 0;
		while(exchFlag == 1 || start == 1) {
			exchFlag = 0;
			for (int i = start; i < arr.length - 1; i+=2) {
				if(arr[i]>arr[i+1]) {
					int temp = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = temp;
					exchFlag =1;
				}
			}
			if(start == 0) {
				start =1;
			}else {
				start = 0;
			}
		}
	}
	
	static int exchFlag = 1;
	static synchronized void setExchFlag(int v) {
		exchFlag = v;
	}
	static synchronized int getExchFlag() {
		return exchFlag;
	}
	
	int i;
	
	CountDownLatch latch;
	
	int[] arr;
	
	static ExecutorService executor = Executors.newFixedThreadPool(2);
	
	public OddEvenSortTask(int i,CountDownLatch latch) {
		this.i = i;
		this.latch = latch;
	}
	@Override
	public void run() {
		if(arr[i]>arr[i+1]) {
			int temp = arr[i];
			arr[i] = arr[i+1];
			arr[i+1] = temp;
			setExchFlag(1);
		}
		latch.countDown();
	}
	
	public static void pOddEvenSort(int[] arr) throws InterruptedException {
		int start = 0;
		while(getExchFlag() == 1 || start == 1) {
			setExchFlag(0);;
			CountDownLatch latch = new CountDownLatch(arr.length/2-(arr.length%2==0?start:0));
			for (int i = start; i < arr.length - 1; i+=2) {
				executor.submit(new OddEvenSortTask(i, latch));
			}
			latch.await();
			if(start == 0) {
				start =1;
			}else {
				start = 0;
			}
		}
	}
	
}
