package beanHelper;

import annotation.Bean;
import annotation.Inject;

/**
 * @Author: xujunqian
 * @Date: 2020/11/19 17:19
 * @Description:
 */
@Bean
public class UserServiceImpl implements UserService{
    @Inject
    private UserDao userDao;

    @Override
    public void test(){
        userDao.say();
    }
}
