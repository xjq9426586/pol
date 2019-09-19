package java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: xujunqian
 * @Date: 2019/9/19 0019 10:11
 * @Description:
 */
public class Client {

    public static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beaf", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    public static List<String> filterDishName() {
        List<String> dishNames =
                menu.stream()                               // 获取流
                .filter(dish -> dish.getCalories() > 300)   // 筛选大于300
                .map(Dish::getName)                         // 获取菜名
                .limit(3)                                   // 取三位
                .collect(Collectors.toList());              // 终端转换为列表
        return dishNames;
    }

    public static void main(String[] args){
        filterDishName().forEach(System.out::println);

        String[] arrays = {"hello", "world"};
        List<String> list = Arrays.stream(arrays)
                .map(word -> word.split(""))        // 转换成字母数组
                .flatMap(Arrays::stream)            // 将两个数组流合成一个数组流
                .distinct()                         // 取唯一
                .collect(Collectors.toList());
        list.forEach(System.out::print);
        list.forEach(item -> System.out.print(item));
    }

}