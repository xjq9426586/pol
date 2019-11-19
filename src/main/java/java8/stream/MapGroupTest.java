package java8.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: xujunqian
 * @Date: 2019/11/12 0012 13:36
 * @Description:
 */
public class MapGroupTest {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 123);
        map.put("v", 345);
        map.put("n", "n");
        map.put("d", "2020");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("a", 123);
        map1.put("v", 2);
        map1.put("n", "n");
        map1.put("d", "2018");
        List<Map<String, Object>> list = Arrays.asList(map1, map);
        List newList = list.stream().filter(m -> m.get("d").equals("2019")).collect(Collectors.toList());
        System.out.println(newList);


        Map m = list.stream().collect(Collectors.groupingBy(e -> e.get("a")));
        List a = new ArrayList();
        m.forEach((k, v) -> {
            Map<String, Object> re = new HashMap<>();
            re.put("indCd", k);
            List reList = new ArrayList();
            ((List) v).forEach(item -> {
                reList.add(((Map) item).get("v"));
                re.put("val", reList);
                re.put("nm", ((Map) item).get("n"));
            });
            a.add(re);
        });
        System.out.println(a);
    }
}
