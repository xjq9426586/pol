package annotation;

import java.lang.annotation.*;

/**
 * @Author: xujunqian
 * @Date: 2020/11/19 17:21
 * @Description:
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
    String value() default "";
}
