package chapter1;

public class OrderExample {
	int a = 0;
	boolean flag = false;
	private void writer() {
		// TODO Auto-generated method stub
		a = 1;
		flag = true;
	}
	private void reader() {
		// TODO Auto-generated method stub
		if(flag) {
			int i = a + 1;
//			...
		}
	} 
	
}
