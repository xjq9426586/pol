package thread;

public class threadLocal {

    //①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值  
    private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

    //②获取下一个序列值  
    public int getNextNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    public static void main(String[] args) {
        threadLocal sn = new threadLocal();

        //③ 3个线程共享sn，各自产生序列号
        TestClient t1 = new TestClient(sn);
        TestClient t2 = new TestClient(sn);
        TestClient t3 = new TestClient(sn);
        t1.start();
        t2.start();
        t3.start();
    }

    private static class TestClient extends Thread {
        private threadLocal sn;

        public TestClient(threadLocal sn) {
            this.sn = sn;
        }

        public void run() {
            //④每个线程打出3个序列值
            for (int i = 0; i < 3; i++) {
                System.out.println("thread[" + Thread.currentThread().getName() +
                        "] sn[" + sn.getNextNum() + "]");
            }
        }
    }
}  
