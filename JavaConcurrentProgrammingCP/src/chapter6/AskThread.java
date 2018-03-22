package chapter6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AskThread implements Runnable{
	CompletableFuture<Integer> re = null;
	public AskThread(CompletableFuture<Integer> re) {
		this.re = re;
	}
	@Override
	public void run() {
		int myRe = 0;
		try {
			myRe = re.get() * re.get();
		} catch (Exception e) {}
		System.out.println(myRe);
	}
	
	public static Integer calc(Integer para) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		return para * para;
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		final CompletableFuture<Integer> future = new CompletableFuture<>();
//		new Thread(new  AskThread(future)).start();
//		Thread.sleep(1000);
//		future.complete(60);
		
//		final CompletableFuture<Integer> future = CompletableFuture.supplyAsync(()->calc(50));
//		System.out.println(future.get());
		
//		CompletableFuture<Void> fu = CompletableFuture
//				.supplyAsync(()->calc(50))
//				.thenApply((i)->Integer.toString(i))
//				.thenApply((str)->"\""+str+"\"")
//				.thenAccept(System.out::println);
//		fu.get();
		
//		CompletableFuture<Void> fu = CompletableFuture
//				.supplyAsync(()->calc(50))
//				.exceptionally(ex->{
//					System.out.println(ex.toString());
//					return 0;
//				})
//				.thenApply((i)->Integer.toString(i))
//				.thenApply((str)->"\""+str+"\"")
//				.thenAccept(System.out::println);
//		fu.get();
		
//		CompletableFuture<Void> fu = CompletableFuture
//				.supplyAsync(()->calc(2))
//				.thenCompose((i)->CompletableFuture.supplyAsync(()->calc(i)))
//				.thenApply((str)->"\""+str+"\"")
//				.thenAccept(System.out::println);
//		fu.get();
		
		CompletableFuture<Integer> intFuture1 = CompletableFuture.supplyAsync(()->calc(2));
		CompletableFuture<Integer> intFuture2 = CompletableFuture.supplyAsync(()->calc(3));
		CompletableFuture<Void> fu = intFuture1.thenCombine(intFuture2, (i,j)->(i+j))
				.thenApply((str)->"\""+str+"\"")
				.thenAccept(System.out::println);
		fu.get();
		
	}
}
