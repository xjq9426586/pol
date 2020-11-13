package threadPool;

import thread.MyThread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	public static void main(String[] args) {
//		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//		for (int i = 0; i < 10; i++) {
//		    final int index = i;
//		    try {
//		        Thread.sleep(index * 1000);
//		    } catch (InterruptedException e) {
//		        e.printStackTrace();
//		    }
//
//		    cachedThreadPool.execute(() -> {
//				System.out.println(index);
//				System.out.println(Thread.currentThread().getName());
//			});
//		}
		Map<String, Object> map = new ConcurrentHashMap<>();
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 10; i++) {
		    final int index = i;
		    fixedThreadPool.execute(() -> {
				System.out.println(Thread.currentThread().getName());
				map.put(String.valueOf(index), Thread.currentThread());

			});
		}
		System.out.println(map);
	}
	
}
