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
import java.util.*;

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
        if (scan != null) {
            String[] packagePath = scan.value();
            initBeanFromAnnotation(packagePath);
        } else {
            initBeanFromXml();
        }
    }

    /**
     * 通过注解方式初始化bean，扫描packagePath下所有加上@Bean注解的类
     *
     * @param packagePath
     */
    private static void initBeanFromAnnotation(String[] packagePath) {
        if (beansMap.get() == null) {
            beansMap.set(new HashMap<>());
        }
        for (String path : packagePath) {
            Set<Class<?>> classes = ClassHelper.getClzFromPkg(path);
            for (Class<?> aClass : classes) {
                Bean beanAnno = aClass.getAnnotation(Bean.class);
                if (beanAnno != null) {
                    String beanId = beanAnno.value();
                    if (StringUtils.isEmpty(beanId)) {
                        beanId = getDependenceBeanId(aClass);
                    }
                    try {
                        Object bean = aClass.newInstance();
                        beansMap.get().put(beanId, bean);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            dependence(beansMap);
        }
    }

    /**
     * 依赖注入
     *
     * @param beansMap
     */
    private static void dependence(ThreadLocal<HashMap<String, Object>> beansMap) {
        Map<String, Object> beans = beansMap.get();
        beans.forEach((beanId, bean) -> {
            Field[] fields = bean.getClass().getDeclaredFields();
            try {
                for (Field field : fields) {
                    Annotation[] annotations = field.getAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Inject) {
                            String value = ((Inject) annotation).value();
                            field.setAccessible(true);
                            Class diClz = field.getType();
                            //注入bean,如果已定义value 则直接取value 作为注入的beanId
                            if (StringUtils.isEmpty(value)) {
                                //如果注入的是接口，先找到实现类，多个实现类处理
                                if (diClz.isInterface()) {
                                    Set<Class<?>> allInterfaceAchieveClass = ClassHelper.getAllInterfaceAchieveClass(diClz);
                                    if (allInterfaceAchieveClass.size() > 1) {
                                        log.error("多个实现类，@Inject 未设置value");
                                        throw new RuntimeException("多个实现类，@Inject 未设置value");
                                    } else {
                                        value = getDependenceBeanId(allInterfaceAchieveClass.iterator().next());
                                    }
                                } else {
                                    value = getDependenceBeanId(diClz);
                                }
                            }
                            field.set(bean, beans.get(value));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 通过xml初始化Bean
     */
    private static void initBeanFromXml() {
        if (beansMap.get() == null) {
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
                        if (annotation instanceof Inject) {
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

    public static Object getBean(String beanName) {
        return beansMap.get().get(beanName);
    }

    private static String getDependenceBeanId(Class clz) {
        String beanId = StringUtils.uncapitalize(clz.getSimpleName());
        return beanId;
    }

    public static void main(String[] args) {
        new BeanFactory();
        UserApiImpl userService = (UserApiImpl) BeanFactory.getBean("userApiImpl");
        userService.test();
    }
}
