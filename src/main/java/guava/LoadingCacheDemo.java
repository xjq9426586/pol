package guava;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class LoadingCacheDemo {
	   public static void main(String[] args) throws ExecutionException {
		   LoadingCache<Object,Object> cache= CacheBuilder.newBuilder()
	               .maximumSize(100) //最大缓存数目
	               .expireAfterAccess(1, TimeUnit.MINUTES) //缓存1秒后过期
	               .build(new CacheLoader<Object, Object>() {
	                   @Override
	                   public Object load(Object key) throws Exception {
	                       return key;
	                   }
	               });
		   Multimap<String,Integer> map= HashMultimap.create(); 
		   for (int i = 0; i <3; i++) {
			   map.put("value", i);
		       cache.put(i,map);
		       /*try {
		           System.out.println(cache.get(i));
		       } catch (ExecutionException e) {
		           e.printStackTrace();
		       }*/
		}
		   System.out.println(cache.asMap());
		System.out.println(map);
	   }
	}
