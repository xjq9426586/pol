package thread;

public class DeadLock {
	private Object o1 = new Object();
	private Object o2 = new Object();
	
	public void  t1() throws InterruptedException {
		synchronized (o1) {
			Thread.sleep(2000);
			synchronized (o2) {
				System.out.println(1);
			}
		}
	}
	public void t2() throws InterruptedException {
		synchronized (o2) {
			Thread.sleep(2000);
			synchronized (o1) {
				System.out.println(2);
			}
		}
	}
}
