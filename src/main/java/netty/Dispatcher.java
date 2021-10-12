package netty;

import annotation.Route;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: xujunqian
 * @Date: 2019/12/11 0011 17:32
 * @Description:
 */
public class Dispatcher {
    private static final Map<String, Object> map = new HashMap<>();
    private static final TestRoute testRoute = new TestRoute();
    private static final Dispatcher dispatcher = new Dispatcher();

    public static final Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void init() {
        System.out.println("Dispatcher-init=======start");
        Method[] methods = testRoute.getClass().getMethods();
        for (Method method : methods) {
            Route route = method.getAnnotation(Route.class);
            if (route == null) continue;
            map.put(route.name(), method);
        }
        System.out.println("Dispatcher-init=======end");
    }

    public Object dispatcherRoute(String key) throws InvocationTargetException, IllegalAccessException {
        Method method = (Method) map.get(key);
        if (method != null) {
            return method.invoke(testRoute, null);
        }
        return null;
    }
}
