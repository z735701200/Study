package chapter5;

public class LasySingleton {
	 private LasySingleton() {
		 System.out.println("LasySingleton is create");
	 }
	 private static LasySingleton instance = null;
	 public static synchronized LasySingleton getInstance() {
		 if(instance == null) {
			 instance = new LasySingleton();
		 }
		 return instance;
	 }
}
