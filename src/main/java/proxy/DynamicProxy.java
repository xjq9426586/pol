package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {
    private Object obj; //obj为委托类对象；
 
    public DynamicProxy(Object obj) {
        this.obj = obj;
    }
 
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object result = method.invoke(obj, args);
        System.out.println("after");
        return result;
    }
    public static void main(String[] args) {
    	//创建真实类实例
    	Vendor vendor= new Vendor();
        //创建代理类实例
        DynamicProxy  inter = new DynamicProxy(vendor);
        //加上这句将会产生一个$Proxy0.class文件，这个文件即为动态生成的代理类文件
       // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true"); 
        /*在newProxyInstance这个方法的第二个参数上，给这个代理对象提供了一组什么接口，那么这个代理对象就会实现了这组接口，
       	可以将这个代理对象强制类型转化为这组接口中的任意一个*/
        Sell sell = (Sell)(Proxy.newProxyInstance(Sell.class.getClassLoader(),
        new Class[] {Sell.class}, inter));
        //调用方法，实际上会转到代理类invoke方法调用
        sell.sell();
        sell.ad();
       
        System.out.println(sell.getClass().getName());
    }
    
}
