package chapter6.lambda;

import java.util.ArrayList;
import java.util.List;

public class BadMethoddRef {
	public static void main(String[] args) {
		List<Double> numbers = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			numbers.add(Double.valueOf(i));
		}
//		numbers.stream().map(Double::toString).forEach(System.out::println);
	}
}
