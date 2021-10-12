package thread;

public class testThread {
    int a = 0;

    public void test(int a) {
        this.a = a;
        System.out.println(this.a);
    }

    public static void main(String[] args) {
        final testThread tt = new testThread();
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                tt.test(1);
                while (true) {
                    tt.test(2);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    tt.test(2);
                }
            }
        });

        t.start();
        t2.start();
    }
}
