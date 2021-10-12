package jxls;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: xujunqian
 * @Date: 2019/11/19 0019 16:21
 * @Description:
 */
public class JxlsDemo {

    public static void main(String[] args) throws ParseException, IOException {

        String filePath = JxlsDemo.class.getResource("").getPath();
        List<Employee> employees = generateSampleEmployeeData();
        OutputStream os = new FileOutputStream("target/object_collection_output.xlsx");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("employees", generateData1());
        model.put("nowdate", new Date());
        InputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\代码提交申请单模板.xlsx");
        JxlsUtils.exportExcel(inputStream, os, model);
        os.close();
    }

    public static List<Employee> generateSampleEmployeeData() throws ParseException {
        List<Employee> employees = new ArrayList<Employee>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
        employees.add(new Employee("Elsa", dateFormat.parse("1970-Jul-10"), 1500, 0.15));
        employees.add(new Employee("Oleg", dateFormat.parse("1973-Apr-30"), 2300, 0.25));
        employees.add(new Employee("Neil", dateFormat.parse("1975-Oct-05"), 2500, 0.00));
        employees.add(new Employee("Maria", dateFormat.parse("1978-Jan-07"), 1700, 0.15));
        employees.add(new Employee("John", dateFormat.parse("1969-May-30"), 2800, 0.20));
        return employees;
    }

    public static List<Map<String, Object>> generateData() throws ParseException {
        List<Map<String, Object>> employees = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "123");
        map.put("birthDate", "123");
        map.put("payment", "123");
        map.put("bonus", "123");
        employees.add(map);
        return employees;
    }

    public static List<Map<String, Object>> generateData1() throws ParseException, FileNotFoundException {
        List<Map<String, Object>> employees = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("index", "123");
        map.put("name", "123");
        map.put("path", "123");
        map.put("reason", "123");
        map.put("date", new FileOutputStream(new File("D:\\work0\\credit_190718\\财报导航配置修改.patch")));
        employees.add(map);
        return employees;
    }
}
