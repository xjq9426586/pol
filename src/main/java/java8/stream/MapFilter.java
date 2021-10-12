package java8.stream;

import com.jeedev.msdp.utlis.MapUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Auther: xujunqian
 * @Date: 2019/9/19 0019 10:39
 * @Description:
 */
public class MapFilter {
    public static <K, V> Map<K, V> filterByKey(Map<K, V> map, Predicate<K> predicate) {
        return map.entrySet().stream()
                .filter(x -> predicate.test(x.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static void main(String[] args) {

        List list = new ArrayList();
        Map<String, Object> HOSTING = new HashMap<>();
        HOSTING.put("1", "linode.com");
        HOSTING.put("2", "heroku.com");
        HOSTING.put("3", "digitalocean.com");
        HOSTING.put("4", "aws.amazon.com");

        //Map -> Stream -> Filter -> Map
        Map<String, Object> result1 = HOSTING.entrySet().stream()
                .filter(map -> map.getKey().equals(""))
                .collect(Collectors.toMap(h -> h.getKey(), h -> h.getValue()));

        System.out.println(result1);


        Map<String, Object> HOSTING1 = new HashMap<>();
        HOSTING1.put("11", "linode.com");
        HOSTING1.put("2", "heroku.com");
        HOSTING1.put("3", "digitalocean.com");
        HOSTING1.put("4", "aws.amazon.com");
        list.add(HOSTING);
        list.add(HOSTING1);
        Map<String, Object> result2 = HOSTING1.entrySet().stream()
                .filter(map -> map.getKey().equals(""))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(result2);

        System.out.println(filterByKey(HOSTING1, x -> x.equals("11")));
        System.out.println(HOSTING1.containsValue("digitalocean.com1"));
        boolean f = list.stream().anyMatch(map -> "linode.com".equals(MapUtil.getString((Map) map, "11")));

        List<Map<String, Object>> list1 = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("sn", 1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("sn", 12);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("sn", 13);
        list1.add(map2);
        list1.add(map1);
        list1.add(map3);
        System.out.println(list1.stream().map(map -> map.get("sn")).collect(Collectors.toList()));
        Comparator<Map<String, Object>> comparator = Comparator.comparing(m -> ((Integer) m.get("sn")));
        list1.sort(comparator.reversed());
        //list1.sort(comparator);
        //list1.stream().sorted(Comparator.comparing(m -> ((Integer) m.get("sn")))).collect(Collectors.toList());

        System.out.println(list1);
    }

}
