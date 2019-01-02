package thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
//                Thread myThread1 = new MyThread("ste");     // 创建一个新的线程  myThread1  此线程进入新建状态
//                Thread myThread2 = new MyThread("mmmm");     // 创建一个新的线程 myThread2 此线程进入新建状态
//                myThread1.start();                     // 调用start()方法使得线程进入就绪状态
//                myThread2.start();                     // 调用start()方法使得线程进入就绪状态
       
    	DeadLock lock = new DeadLock();
    	Thread t = new ProxyThread(lock);
    	Thread t2 = new ProxyThread2(lock);
    	t.start();
    	t2.start();     
    }
}
