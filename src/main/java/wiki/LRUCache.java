package wiki;

import java.util.LinkedHashMap;
import java.util.Map;


public class LRUCache<K,V> extends LinkedHashMap<K,V> {

    private final int CACHE_SIZE;

    public LRUCache( int cacheSize) {
        super((int)Math.ceil(cacheSize / 0.75) + 1 ,0.75f,true);
        this.CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > CACHE_SIZE;
    }

    public static void main(String[] args) {
        LRUCache<Integer,Integer> lruCache = new LRUCache<>(10);
        for (int i = 0; i < 15; i++) {
            lruCache.put(i,i);
        }

        Integer integer1 = lruCache.get(0);
        for (Integer integer : lruCache.keySet()) {
            System.out.println(integer);
        }

    }
}