package thread;

import java.util.LinkedHashMap;

public class MyThread extends Thread {

    private String str;

    public MyThread(String str) {
        super();
        this.str = str;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(str + i);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        LinkedHashMap<Object, Object> objectObjectLinkedHashMap = new LinkedHashMap<>();
        MyThread myThread = new MyThread("A");
        myThread.start();
        myThread.join();
        MyThread myThread2 = new MyThread("B");
        myThread2.start();
    }
}