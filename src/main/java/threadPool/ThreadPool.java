package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	public static void main(String[] args) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
		for (int i = 0; i < 10; i++) {  
		    final int index = i;  
		    try {  
		        Thread.sleep(index * 1000);  
		    } catch (InterruptedException e) {  
		        e.printStackTrace();  
		    }  
		  	
		    cachedThreadPool.execute(new Runnable() {  
		  	
		        @Override  
		        public void run() {  
		            System.out.println(index);  
		        }  
		    });  
		}  
//		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);  
//		for (int i = 0; i < 10; i++) {  
//		    final int index = i;  
//		    fixedThreadPool.execute(new Runnable() {  
//		  
//		  
//		        @Override  
//		        public void run() {  
//		            try {  
//		                System.out.println(index);  
//		                Thread.sleep(2000);  
//		            } catch (InterruptedException e) {  
//		                // TODO Auto-generated catch block  
//		                e.printStackTrace();  
//		            }  
//		        }  
//		    });  
//		}  
	}
	
}
