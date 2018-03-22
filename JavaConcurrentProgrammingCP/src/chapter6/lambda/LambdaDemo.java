package chapter6.lambda;

import java.util.Arrays;
import java.util.function.IntConsumer;

public class LambdaDemo {
	static int[] arr= {0,1,2,3,4,5,6,7,8,9,10};
	public static void main(String[] args) {
		//原始
		for (int i : arr) {
			System.out.println(i);
		}
		//优化1
		Arrays.stream(arr).forEach(new IntConsumer() {
			@Override
			public void accept(int value) {
				System.out.println(value);
			}
		});
		//优化2
		Arrays.stream(arr).forEach((final int x)->{
			System.out.println(x);
		});
		//优化3
		Arrays.stream(arr).forEach((x)->{
			System.out.println(x);
		});
		//优化4
		Arrays.stream(arr).forEach((x)->System.out.println(x));
	}
}
