package chapter5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShellSortTask implements Runnable {
	public static void insertSort(int[] arr) {
		int length = arr.length;
		int j, i, key;
		for (i = 1; i < length; i++) {
			key = arr[i];
			j = i - 1;
			while (j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = key;
		}
	}

	public static void shellSort(int[] arr) {
		int h = 1;
		while (h < arr.length / 3) {
			h = h * 3 + 1;
		}
		while (h > 0) {
			for (int i = h; i < arr.length; i++) {
				if (arr[i] < arr[i - h]) {
					int tmp = arr[i];
					int j = i - h;
					while (j >= 0 && arr[j] > tmp) {
						arr[j + h] = arr[j];
						j -= h;
					}
					arr[j + h] = tmp;
				}
			}
			h = (h - 1) / 3;
		}
	}

	int[] arr;

	static ExecutorService executor = Executors.newFixedThreadPool(2);

	int i = 0;
	int h = 0;
	CountDownLatch l;

	public ShellSortTask(int i, int h, CountDownLatch latch) {
		this.i = i;
		this.h = h;
		this.l = latch;
	}

	@Override
	public void run() {
		if (arr[i] < arr[i - h]) {
			int tmp = arr[i];
			int j = i - h;
			while (j >= 0 && arr[j] > tmp) {
				arr[j + h] = arr[j];
				j -= h;
			}
			arr[j + h] = tmp;
		}
		l.countDown();
	}

	public void pShellSort(int[] arr) throws InterruptedException {
		int h = 1;
		CountDownLatch latch = null;
		while (h <= arr.length / 3) {
			h = h * 3 + 1;
		}
		while (h > 0) {
			if (h >= 4)
				latch = new CountDownLatch(arr.length - h);
			for (int i = h; i < arr.length; i++) {
				if (h >= 4) {
					executor.execute(new ShellSortTask(i, h, latch));
				} else {
					if (arr[i] < arr[i - h]) {
						int tmp = arr[i];
						int j = i - h;
						while (j >= 0 && arr[j] > tmp) {
							arr[j + h] = arr[j];
							j -= h;
						}
						arr[j + h] = tmp;
					}
				}
			}
			latch.await();
			h = (h - 1) / 3;
		}
	}

}
