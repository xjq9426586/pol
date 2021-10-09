package distributedLock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {
    class Worker implements Runnable{
        CyclicBarrier cyclicBarrier;
        Service service;
        public Worker(CyclicBarrier cyclicBarrier, Service service){
            this.cyclicBarrier = cyclicBarrier;
            this.service = service;
        }
 
        @Override
        public void run() {
            try {
                cyclicBarrier.await(); // 等待其它线程
                System.out.println(Thread.currentThread().getName() + "启动@" + System.currentTimeMillis());
                service.seckill();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
 
    public void doTest() throws InterruptedException {
        final int N = 2; // 线程数
        Service service = new Service();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N);
        for(int i=0;i<N;i++){
            new Thread(new Worker(cyclicBarrier, service)).start();
        }
    }
 
    public static void main(String[] args) throws InterruptedException {
        TestCyclicBarrier testCyclicBarrier = new TestCyclicBarrier();
        testCyclicBarrier.doTest();
    }
}