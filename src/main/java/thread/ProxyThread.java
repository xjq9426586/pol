package thread;

public class ProxyThread extends Thread{
	
	private DeadLock lock;
	public ProxyThread(DeadLock lock) {
		this.lock = lock;
	}
	
	@Override
	public void run() {
		try {
			lock.t1();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
