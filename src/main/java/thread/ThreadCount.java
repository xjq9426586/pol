package thread;

import java.util.concurrent.atomic.AtomicLong;

public class ThreadCount {
    public static void main(String[] args) {
        
        Thread[] threads=new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i]=new AThread();
            threads[i].start();
        }
    }
}

class AThread extends Thread{

    @Override
    public void run() {
        System.out.println(MyCounter.calNum());
    }
    
}


class MyCounter{
    private static AtomicLong num=new AtomicLong();
    
    public static synchronized long calNum(){
        return num.incrementAndGet();
    }
}