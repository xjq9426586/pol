package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Test {

    public static void main(String[] args) {

        ExecutorService exector = Executors.newCachedThreadPool();
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            Future<?> future = exector.submit(() -> {
                try {
                    Thread.sleep(1000);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                System.out.println("正在执行线程！i=" + finalI);
            });
            Future<?> future2 = exector.submit(() -> {
                try {
                    Thread.sleep(1000);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                System.out.println("正在执行线程2！i=" + finalI);
            });

            while(true) {
                if(future.isDone() && future2.isDone()) {
                    System.out.println("主线程已经执行完了");
                    future.cancel(true);
                    System.out.println(future.isCancelled());
                    break;
                }
            }
        }


        exector.shutdown();
    }
}
