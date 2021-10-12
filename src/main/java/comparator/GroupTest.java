package comparator;

import java.util.*;
import java.util.stream.Collectors;

public class GroupTest {
    class Apple {
        public String color;
        public Integer weight;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Apple(String color, int weight) {
            super();
            this.color = color;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "{color=" + color + ", weight=" + weight + "}";
        }
    }

    /**
     * 按条件分组
     *
     * @param datas
     * @param c
     * @return
     */
    public static <T> List<List<T>> divider(Collection<T> datas, Comparator<? super T> c) {
        List<List<T>> result = new ArrayList<List<T>>();
        for (T t : datas) {
            boolean isSameGroup = false;//是否同一组标识
            for (int j = 0; j < result.size(); j++) {
                if (c.compare(t, result.get(j).get(0)) == 0) {
                    isSameGroup = true;
                    result.get(j).add(t);
                    break;
                }
            }
            if (!isSameGroup) {
                List<T> innerList = new ArrayList<T>();
                innerList.add(t);
                result.add(innerList);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Apple> list = new ArrayList<>();

        list.add(new GroupTest().new Apple("红", 205));
        list.add(new GroupTest().new Apple("红", 131));
        list.add(new GroupTest().new Apple("绿", 248));
        list.add(new GroupTest().new Apple("绿", 153));
        list.add(new GroupTest().new Apple("黄", 119));
        list.add(new GroupTest().new Apple("黄", 224));
        Map<String, List<Apple>> groupByMap = list.stream().collect(Collectors.groupingBy(Apple::getColor));
        groupByMap.forEach((k, v) -> System.out.println(k + "," + v));
        list.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(list);
        List<List<Apple>> byColors = divider(list, new Comparator<Apple>() {

            @Override
            public int compare(Apple o1, Apple o2) {
                // 按颜色分组
                return o1.color.compareTo(o2.color);
            }
        });
        System.out.println("按颜色分组" + byColors);
        List<List<Apple>> byWeight = divider(list, new Comparator<Apple>() {

            @Override
            public int compare(Apple o1, Apple o2) {
                // 按重量级

                return (o1.weight / 100 == o2.weight / 100) ? 0 : 1;
            }
        });
        System.out.println("按重量级分组" + byWeight);


        Collections.sort(list, new Comparator<Apple>() {

            @Override
            public int compare(Apple o1, Apple o2) {

                return o1.weight.compareTo(o2.weight);
            }

        });
        System.out.println("按重量级比较" + list);

    }

}