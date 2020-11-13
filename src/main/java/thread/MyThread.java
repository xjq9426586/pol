package thread;

public class MyThread extends Thread {
    
    private String str;
    
    public MyThread(String str) {
		super();
		this.str = str;
	}

	@Override
    public void run() {
		System.out.println(str);
			
    }
}