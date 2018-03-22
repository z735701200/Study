package chapter5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchTask implements Callable<Integer>{
	int begin,end,searchValue;
	public SearchTask(int searchValue,int begin,int end) {
		this.searchValue = searchValue;
		this.begin = begin;
		this.end = end;
	}
	@Override
	public Integer call() throws Exception {
		int re = search(searchValue,begin,end);
		return re;
	}
	
	static int[] arr;
	static ExecutorService pool = Executors.newCachedThreadPool();
	static final int Thread_Num = 2;
	static AtomicInteger result = new AtomicInteger(-1);
	public static int search(int searchValue,int beginPos,int endPos) {
		int i =0;
		for (i = beginPos; i < endPos; i++) {
			if(result.get() >= 0) {
				return result.get();
			}
			if(arr[i] == searchValue) {
				if(!result.compareAndSet(-1, i)) {
					return result.get();
				}
				return i;
			}
		}
		return -1;
	}
	
	public static int pSearch(int serachValue) throws InterruptedException, ExecutionException {
		int subArrSize = arr.length/Thread_Num+1;
		List<Future<Integer>> re = new ArrayList<>();
		for (int i = 0; i < arr.length; i+=subArrSize) {
			int end = i + subArrSize;
			if(end >= arr.length)end = arr.length;
			re.add(pool.submit(new SearchTask(serachValue,i,end)));
		}
		for (Future<Integer> future : re) {
			if(future.get()>=0) return future.get();
		}
		return -1;
	}

}
