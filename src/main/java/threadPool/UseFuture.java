package threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.*;

public class UseFuture implements Callable {
    private String para;

    public UseFuture(String para) {
        this.para = para;
    }

    /**
     * 这里是真实的业务逻辑，其执行可能很慢
     */
    @Override
    public String call() throws Exception {
        if (para == "query1") {
            //模拟执行耗时
            Thread.sleep(3000);
        }
        if (para == "query2") {
            //模拟执行耗时
            Thread.sleep(10000);
        }
        String result = this.para + "处理完成";
        return result;
    }

    //主控制函数
    public static void main(String[] args) throws Exception {
        String queryStr1 = "query1";
        String queryStr2 = "query2";
        //构造FutureTask，并且传入需要真正进行业务逻辑处理的类,该类一定是实现了Callable接口的类
        FutureTask<String> future1 = new FutureTask<String>(new UseFuture(queryStr1));
        FutureTask<String> future2 = new FutureTask<String>(new UseFuture(queryStr2));

        //创建一个固定线程的线程池且线程数为1,
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //这里提交任务future,则开启线程执行RealData的call()方法执行
        Future freturn1 = executor.submit(future1);
        Future freturn2 = executor.submit(future2);
        System.out.println("主函数为future1和future2开启了两个线程，接下来主函数可以继续执行，不必等到这两个线程执行完毕");

        try {
            //这里可以做额外的数据操作，也就是主程序执行其他业务逻辑
            System.out.println("我是主函数，我继续忙自己的事儿");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // submit和excute的区别在于submit有返回值，执行成功返回null
        while (true) {
            System.out.println("等待执行");
            if (freturn1.get() == null && freturn2.get() == null) {
                future1.cancel(true);
                future1.isCancelled();
                System.out.println("执行完毕");
                break;
            }
        }

        //调用获取数据方法,如果call()方法没有执行完成,则依然会进行等待
        System.out.println("我是future1，我的执行结果为：" + future1.get());
        System.out.println("我是future2，我的执行结果为：" + future2.get());
        executor.shutdown();
    }
}