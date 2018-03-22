package chapter2;

public class CreateThread3 implements Runnable {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Oh,I am Runnable");
	}

	public static void main(String[] args) {
		Thread thread = new Thread(new CreateThread3());
		thread.start();
	}
}
