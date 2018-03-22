package chapter2;

public class StopThreadUnsafe {
	public static User u = new User();
	public static class User{
		private int id;
		private String name = "0";
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class ChangeObjectThread extends Thread{
		volatile boolean stopme = false;
		public void stopMe() {
			// TODO Auto-generated method stub
			stopme = true;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				if(stopme) {
					System.out.println("exit by stop me");
					break;
				}
				
				synchronized(u) {
					int v = (int)(System.currentTimeMillis()/1000);
					u.setId(v);
					try {
						Thread.sleep(100);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					u.setName(String.valueOf(v));
				}
				Thread.yield();
			}
		}
	}
	public static class ReadObjectThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				synchronized(u) {
					if(u.getId() != Integer.parseInt(u.getName())) {
						System.out.println("User [id=" + u.getId() + ",name=" + u.getName() + "]");
					}
				}
				Thread.yield();
			}
		}
	}
	
	public static void main(String[] args) {
		new ReadObjectThread().start();
		while(true) {
			Thread thread = new ChangeObjectThread();
			thread.start();
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			thread.stop();
			((ChangeObjectThread)thread).stopMe();
		}
	}
}
