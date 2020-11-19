package beanHelper;

import annotation.Bean;
import annotation.Inject;

/**
 * @Author: xujunqian
 * @Date: 2020/11/19 17:19
 * @Description:
 */
@Bean
public class UserService {
    @Inject
    private UserDao userDao;

    public void test(){
        userDao.say();
    }
}
