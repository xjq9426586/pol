package java8.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: xujunqian
 * @Date: 2019/9/19 0019 10:39
 * @Description:
 */
public class test {
    public static void main(String[] args) {
        try {
           List<String> c = Files.lines(Paths.get("D:\\财务报表新增字段.sql"))
                    .flatMap(line -> Arrays.stream(line.split("\n")))
                   .collect(Collectors.toList());
            c.forEach(System.out::println);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
