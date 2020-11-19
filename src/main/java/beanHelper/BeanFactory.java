package beanHelper;

import annotation.Inject;
import annotation.Scan;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xujunqian
 * @Date: 2020/11/19 16:09
 * @Description:
 */
@Slf4j
@Scan({"beanHelper"})
public class BeanFactory {
    private static Map<String, Object> beansMap = new HashMap<>();

    static {
        ClassPathResource classPathResource = new ClassPathResource("/file/BeanFactory.xml");
        try {
            InputStream inputStream = classPathResource.getInputStream();
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            List<Element> beans = rootElement.selectNodes("//bean");
            for (Element bean : beans) {
                String id = bean.attributeValue("id");
                String clazz = bean.attributeValue("class");
                Class clz;
                try {
                    clz = Class.forName(clazz);
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage(), e);
                    continue;
                }
                Object o = clz.newInstance();
                beansMap.put(id, o);
            }
            List<Element> properties = rootElement.selectNodes("//property");
            for (Element property : properties) {
                String ref = property.attributeValue("ref");
                String beanId = property.getParent().attributeValue("id");
                Object bean = beansMap.get(beanId);
                Field[] fields = bean.getClass().getDeclaredFields();
                for (Field field : fields) {
                    Annotation[] annotations = field.getAnnotations();
                    for (Annotation annotation : annotations) {
                        if(annotation instanceof Inject){
                            field.setAccessible(true);
                            //注入bean
                            //TODO 接口注入逻辑
                            field.set(bean, beansMap.get(ref));
                        }
                    }
                }
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    public static Object getBean(String beanName){
        return beansMap.get(beanName);
    }
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        UserService userService = (UserService) BeanFactory.getBean("userService");
        userService.test();
    }
}
