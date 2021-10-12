package beanHelper;

import annotation.Bean;
import annotation.Inject;

/**
 * @Author: xujunqian
 * @Date: 2020/11/20 13:43
 * @Description:
 */
@Bean
public class UserApiImpl {
    @Inject
    private UserService userService;

    public void test() {
        userService.test();
    }
}
