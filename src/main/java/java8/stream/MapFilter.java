package java8.stream;

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
//        try {
//           List<String> c = Files.lines(Paths.get("D:\\财务报表新增字段.sql"))
//                    .flatMap(line -> Arrays.stream(line.split("\n")))
//                   .collect(Collectors.toList());
//            c.forEach(System.out::println);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
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


    }

}