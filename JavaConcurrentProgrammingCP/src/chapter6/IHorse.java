package chapter6;

public interface IHorse {
	void eat();
	default void run() {
		System.out.println("hourse run");
	}
}
