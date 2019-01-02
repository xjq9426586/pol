package BeanHelper;

import java.lang.reflect.InvocationTargetException;

public class test {
public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchMethodException {
	user u=BeanHelper.bean(null, user.class);
}
}
