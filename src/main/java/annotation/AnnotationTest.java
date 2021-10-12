package annotation;

import java.lang.reflect.Method;

/**
 * @Auther: xujunqian
 * @Date: 2019/12/11 0011 16:19
 * @Description:
 */
public class AnnotationTest {
    @Route(name = "346", value = "abc")
    public String get() {
        return null;
    }

    public static void main(String[] args) {
        AnnotationTest at = new AnnotationTest();

        Method[] methods = at.getClass().getMethods();
        for (Method method : methods) {
            Route t = method.getAnnotation(Route.class);
            if (t == null) continue;
            System.out.println(t.name());
        }
    }
}
