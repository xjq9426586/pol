package excel.read;

import classLoader.HotClassLoader;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.ExcelReadUtil;
import com.jeedev.msdp.utlis.StringUtil;
import com.rabbitmq.tools.json.JSONUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;

import javax.jws.Oneway;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: xujunqian
 * @Date: 2019/10/17 0017 15:15
 * @Description:
 */
public class readExcelTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        out2("D:\\work\\credit_source\\tansun-tcp-base\\tansun-tcp-base-boot\\target\\tansun-tcp-base-boot-2.0.0-SNAPSHOT.jar");
    }

    public static void out2(String classPath) throws IOException, ClassNotFoundException {
        List<List> finaceList = ExcelReadUtil.readExcel(new File("C:\\Users\\xjq\\Desktop\\heshun2_20201119_7(1).xls"));
        List<Map<String, Object>> list = finaceList.get(0);

        List<String> packagelist = new ArrayList<>();
        out3("D:\\work\\credit_source\\tansun-tcp-base\\tansun-tcp-base-service\\target\\classes\\", packagelist);

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL(
                "file:" + classPath)});
        for (String s : packagelist) {
            Class<?> aClass = urlClassLoader.loadClass(s);
            Field[] declaredFields = aClass.getDeclaredFields();
            Table tableName = aClass.getAnnotation(Table.class);
            for (Field declaredField : declaredFields) {

                Column annotation = declaredField.getAnnotation(Column.class);
                if (annotation == null) continue;
                String name = annotation.name();
                List<Map<String, Object>> es = list.stream().filter(map -> map.get("5").equals(name))
                        .filter(map -> StringUtils.equalsIgnoreCase((String) map.get("1"), tableName.name()))
                        .collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(es)) {
                    if (!es.get(0).get("6").equals(declaredField.getName())) {
                        System.out.println(s.substring(s.lastIndexOf(".") + 1));
                        System.out.println(es.get(0).get("6") + "     " + declaredField.getName());
                    }
                }
            }
        }


    }

    public void out1() throws IOException {
        List<List> finaceList = ExcelReadUtil.readExcel(
                new File("C:\\Users\\xjq\\Desktop\\heshun2_20201119_7(1).xls")
        );
        List<Map<String, Object>> list = finaceList.get(0);
        String str = "cc.Ctf_Tp";
        String[] split = str.split(",");
        StringBuffer sb = new StringBuffer();
        for (String s : split) {
            String ta = s.trim().replace("cc.", "");
            List<Map<String, Object>> es = list.stream().filter(map -> StringUtils.equalsIgnoreCase((String) map.get("1"), "CUST_CORPORAT"))
                    .filter(map -> StringUtils.equalsIgnoreCase((String) map.get("3"), ta)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(es)) {
                String feild = (String) es.get(0).get("5");
                sb.append(s.trim(), 0, s.trim().indexOf(".") + 1);
                sb.append(feild);
            } else {
                sb.append(s.trim());
            }
            sb.append(",");
            sb.append("\n");

        }
        System.out.println(sb.toString().trim());
    }

    public static void out(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONObject j = (JSONObject) jsonObject.get("data");
        JSONObject grid = (JSONObject) j.get("grid");
        JSONArray data = (JSONArray) grid.get("list");
        List<List> finaceList;
        {
            try {
                finaceList = ExcelReadUtil.readExcel(
                        new File("C:\\Users\\xjq\\Desktop\\heshun2_20201119_7(1).xls")
                );
                List<Map<String, Object>> list = finaceList.get(0);
                StringBuffer sb = new StringBuffer();
                data.forEach(m -> {
                    String fieldName = (String) ((Map<String, Object>) m).get("fieldName");
                    String fieldKey = (String) ((Map<String, Object>) m).get("fieldKey");
                    List<Map<String, Object>> es = list.stream().filter(map -> map.get("4").equals(fieldName)).collect(Collectors.toList());
                    String newField = fieldKey;
                    if (CollectionUtil.isNotEmpty(es)) {
                        if (es.get(0).get("6").equals(fieldKey)) {
                            System.out.println("原值：" + fieldKey);
                        } else {
                            System.out.println("原值：" + fieldKey + "     " + "新值：" + es.get(0).get("6"));
                            newField = (String) es.get(0).get("6");
                        }
                    } else {
                        System.out.println("原值：" + fieldKey + "     " + "未找到值：");
                    }
                    sb.append("\"");
                    sb.append(newField);
                    sb.append("\",");
                });

                System.out.println(sb.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void out3(String path, List<String> list) {
        File dir = new File(path);
        File[] dirfiles = dir.listFiles(pathname -> pathname.getPath().indexOf("com") != -1);
        for (File dirfile : dirfiles) {
            if (dirfile.isDirectory()) {
                out3(dirfile.getPath(), list);
            } else {
                if (dirfile.getPath().indexOf("entity") != -1) {
                    String dPath = dirfile.getPath();
                    list.add(dPath.substring(dPath.indexOf("com"), dPath.lastIndexOf(".")).replace("\\", "."));
                }
            }
        }
    }
}
