package xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;

/**
 * @Auther: xujunqian
 * @Date: 2019/11/25 0025 15:59
 * @Description:
 */
public class XmlUtil {
    public static Map<String, Object> xmlToMap(byte[] fileData) {
        return xmlToMap(new ByteArrayInputStream(fileData));
    }
    public static Map<String, Object> xmlToMap(String filePath) {
        return xmlToMap(new File(filePath));
    }
    public static Map<String, Object> xmlToMap(File file) {
        try {
            return xmlToMap(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @Author xujunqian
     * @Description 解析xml转换成map
     * @Date 2019/11/25 0025
     * @Param [is]输入流
     * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    public static Map<String, Object> xmlToMap(InputStream is) {
        Map<String, Object> map = new HashMap<>();
        //创建DOM4J解析器对象
        SAXReader saxReader = new SAXReader();
        try {
            if(is.available() > 0){
                //读取xml文件，并生成document对象
                Document document = saxReader.read(is);
                if (document != null){
                    Element root = document.getRootElement();
                    convert1(root, map);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    public static Map convert(Element e, Map<String, Object> map){
        String key = e.getName();
        List<Element> list = e.elements();
        List rList = new ArrayList();
        if(map.get(key) instanceof ArrayList){
            rList = (List) map.get(key);
        }else{
            rList.add(map.get(key));
        }
        if(list.size() == 0){//无子节点
            if(map.containsKey(key)){
                rList.add(e.getText());
                map.put(key, rList);
            }else{
                map.put(key, e.getText());
            }
        }else{
            Map<String, Object> newMap = new HashMap<>();
            if(map.containsKey(key)){
                rList.add(convert(e, newMap).get(key));
                map.put(key, rList);
            }else{
                for (Element element : list) {
                    map.put(key, convert(element, newMap));
                }
            }
        }
        return map;
    }

    public static Map convert1(Element e, Map<String, Object> map){
        String key = e.getName();
        List<Element> list = e.elements();
        /**
         * 判断是否已存在相同节点，若存在则判断是否存在则用list来存节点
         */
        if(map.containsKey(key)){
            List rList;
            Object o;
            /**
             * 存在两个以上相同节点名只需取出list不需new
             */
            if(map.get(key) instanceof ArrayList){
                rList = (List) map.get(key);
            }else{
                rList = new ArrayList();
                rList.add(map.get(key));
            }
            if(list.size() == 0){
                o = e.getText();
            }else{
                Map<String, Object> newMap = new HashMap<>();
                o = convert(e, newMap).get(key);//递归获取节点信息
            }
            rList.add(o);
            map.put(key, rList);
        }else{
            if(list.size() == 0){
                map.put(key, e.getText());
            }else{
                Map<String, Object> newMap = new HashMap<>();
                for (Element element : list) {
                    map.put(key, convert(element, newMap));//递归节点
                }
            }
        }
        return map;
    }
    public static void main(String[] args) {
        System.out.println(xmlToMap("C:\\Users\\Administrator\\Desktop\\we.xml"));
    }
}
