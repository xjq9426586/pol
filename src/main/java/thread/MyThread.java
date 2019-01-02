package thread;

class MyThread extends Thread {
    
    private String str;
    
    public MyThread(String str) {
		super();
		this.str = str;
	}

	@Override
    public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(str);
		}
			
    }
}