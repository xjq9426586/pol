package xml;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: xujunqian
 * @Date: 2019/11/26 0026 17:12
 * @Description:
 */
public class TextUtil {

    public static List textSplit(File file, String regex1, String regex2) {
        try {
            return textSplit(new FileInputStream(file), regex1, regex2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List textSplit(byte[] fileData, String regex1, String regex2) {
        return textSplit(new ByteArrayInputStream(fileData), regex1, regex2);
    }

    /**
     * @return java.util.List
     * @Author xujunqian
     * @Description 文本分割解析, 某些分割符，需要加"\\"转义;
     * @Date 2019/11/27 0027
     * @Param [is, regex1, regex2]（输入流，行分隔符，列分割符）
     **/
    public static List textSplit(InputStream is, String regex1, String regex2) {
        List list = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> c = br.lines()
                .flatMap(line -> Arrays.stream(line.split(regex1)))
                .collect(Collectors.toList());
        c.forEach(item -> list.add(Arrays.asList(item.split(regex2))));
        return list;
    }

    public static void main(String[] args) {
        System.out.println(textSplit(new File("D:\\we.txt"), "\n", "\\|"));
    }
}
