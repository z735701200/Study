package chapter6;

public interface IAnimal {
	default void breadth() {
		System.out.println("breath");
	}
}
