package distributedLock;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Service {

    private static JedisPool pool = null;

    private DistributedLock lock = new DistributedLock(pool);

    private int i = 0;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(500);
        // 设置最大空闲数
        config.setMaxIdle(8);
        // 设置最大等待时间
        config.setMaxWaitMillis(1000 * 100);
        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "172.19.80.76", 56379, 3000);
    }

    public void seckill() {
        // 返回锁的value值，供释放锁时候进行判断
        String identifier = lock.lockWithTimeout("resource", 1000, 10000);
        System.out.println(Thread.currentThread().getName() + "获得了锁" + identifier);
        if (identifier == null) {
            System.out.println(Thread.currentThread().getName() + "等待其他线程释放锁");
        }else {
            System.out.println(i++);
        }
        boolean resource = lock.releaseLock("resource", identifier);
        System.out.println("释放" + resource);
    }

    public static void main(String[] args) throws InterruptedException {
        Service service = new Service();
        //service.seckill();
        //service.seckill();

    }
}