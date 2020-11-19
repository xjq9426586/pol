package beanHelper;

import java.lang.reflect.InvocationTargetException;

public class test {
public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchMethodException {
	UserDao u=BeanHelper.bean(null, UserDao.class);
}
}
