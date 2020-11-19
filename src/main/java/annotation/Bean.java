package annotation;

import java.lang.annotation.*;

/**
 * @Author: xujunqian
 * @Date: 2020/11/19 20:02
 * @Description:
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
}
