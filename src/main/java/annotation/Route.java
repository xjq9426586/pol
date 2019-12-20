package annotation;

import java.lang.annotation.*;

/**
 * @Auther: xujunqian
 * @Date: 2019/12/11 0011 16:16
 * @Description:
 */
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    String name() default "";
    String value() default "";
}
