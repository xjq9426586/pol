package thread;

public class ProxyThread2 extends Thread{
	
	private DeadLock lock;
	public ProxyThread2(DeadLock lock) {
		this.lock = lock;
	}
	
	@Override
	public void run() {
		try {
			lock.t2();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
