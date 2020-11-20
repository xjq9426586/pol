package beanHelper;

import annotation.Bean;
import annotation.Inject;
import annotation.Scan;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
import java.util.Set;

/**
 * @Author: xujunqian
 * @Date: 2020/11/19 16:09
 * @Description:
 */
@Slf4j
@Scan({"beanHelper"})
public class BeanFactory {
    private static ThreadLocal<HashMap<String, Object>> beansMap = new ThreadLocal<>();

    static {
        Class<BeanFactory> clz = BeanFactory.class;
        Scan scan = clz.getAnnotation(Scan.class);
        if(scan != null){
            String[] packagePath = scan.value();
            initBeanFromAnnotation(packagePath);
        }else{
            initBeanFromXml();
        }
    }

    /**
     * 通过注解方式初始化bean，扫描packagePath下所有加上@Bean注解的类
     * @param packagePath
     */
    public static void initBeanFromAnnotation(String[] packagePath){
        if(beansMap.get() == null){
            beansMap.set(new HashMap<>());
        }
        for (String path : packagePath) {
            Set<Class<?>> classes = ClassHelper.getClzFromPkg(path);
            for (Class<?> aClass : classes) {
                Bean beanAnno = aClass.getAnnotation(Bean.class);
                if(beanAnno != null){
                    String beanId = beanAnno.value();
                    if(StringUtils.isEmpty(beanId)){
                        beanId = aClass.getSimpleName();
                        beanId = StringUtils.uncapitalize(beanId);
                    }
                    try {
                        Object bean = aClass.newInstance();
                        Field[] fields = aClass.getDeclaredFields();
                        for (Field field : fields) {
                            Annotation[] annotations = field.getAnnotations();
                            for (Annotation annotation : annotations) {
                                if(annotation instanceof Inject){
                                    field.setAccessible(true);
                                    //注入bean
                                    //TODO 接口注入逻辑
                                    field.set(bean, field.getType().newInstance());
                                }
                            }
                        }
                        beansMap.get().put(beanId, bean);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 通过xml初始化Bean
     */
    public static void initBeanFromXml(){
        if(beansMap.get() == null){
            beansMap.set(new HashMap<>());
        }
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
                beansMap.get().put(id, o);
            }
            List<Element> properties = rootElement.selectNodes("//property");
            for (Element property : properties) {
                String ref = property.attributeValue("ref");
                String beanId = property.getParent().attributeValue("id");
                Object bean = beansMap.get().get(beanId);
                Field[] fields = bean.getClass().getDeclaredFields();
                for (Field field : fields) {
                    Annotation[] annotations = field.getAnnotations();
                    for (Annotation annotation : annotations) {
                        if(annotation instanceof Inject){
                            field.setAccessible(true);
                            //注入bean
                            //TODO 接口注入逻辑
                            field.set(bean, beansMap.get().get(ref));
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
        return beansMap.get().get(beanName);
    }
    public static void main(String[] args) {
        new BeanFactory();
        UserService userService = (UserService) BeanFactory.getBean("userService");
        userService.test();
    }
}
