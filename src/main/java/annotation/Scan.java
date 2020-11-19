package annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Author: xujunqian
 * @Date: 2020/11/19 18:00
 * @Description:
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scan {
    @AliasFor("basePackages")
    String[] value() default {};
}
