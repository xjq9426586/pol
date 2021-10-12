package test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.sf.json.JSONObject;
import nullTest.student;
import nullTest.teacher;

public class test1 {
    public static void main(String[] args) throws NoSuchMethodException, SecurityException, Exception {
        test1.test();
        String str = "{id:1,type:1}";
        JSONObject json = new JSONObject();
        //json.put("id", 1);
        //json.put("tList", "");
        json = JSONObject.fromObject(str);
        Map<String, Object> map = new HashMap<>();
        map.put("tList", teacher.class);
        student s = new student();
        s = (student) JSONObject.toBean(json, student.class);
        teacher t = (teacher) JSONObject.toBean(json.getJSONObject("t1"), teacher.class);

        System.out.println("s".equals(s.getT()));
		
		/*student s = new student();
		s.setId(1);
		teacher t = new teacher();
		t.setId(2);
		List<teacher> tList = new ArrayList<teacher>();
		tList.add(t);
		s.settList(tList);
		JSONObject json = new JSONObject();
		json.put("s", s);
		System.out.println(json);
		/*student s = new student();
		 Class<?> c = student.class;  
		//取得本类的全部属性
         Field[] field = c.getDeclaredFields();

         for (Field field2 : field) {
        	 if(field2.getGenericType().toString().equals("class java.lang.Integer")){
            	 String fieldName =getMethodName( field2.getName());
            	 Method m = c.getMethod( "get" + fieldName);
        		Object val =  Optional.ofNullable(m.invoke(s)).orElse(1);
            	// System.out.println(val); 
            	 Method m1 = c.getMethod( "set" + fieldName,field2.getType());
            	 m1.invoke(s, val);
        	 }
        	
         }
         String name = s.getName();
         System.out.println(name+"p");*/
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) (items[0] - 'a' + 'A');
        return new String(items);
    }

    public static String test() {
        try {
            String s = "yuu";
            System.out.println("try" + Integer.valueOf(s));
            return "333";
        } catch (Exception e) {

        }
        return null;
    }
}
