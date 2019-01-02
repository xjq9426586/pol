package BeanHelper;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
 
import javax.servlet.http.HttpServletRequest;
 
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
 
/**
 * Bean辅助工具，可以从Form表单中直接读取bean对象，也可以为bean对象设置多级属性
 * @author Mr.Xia v1.0  Date：2017年1月14日02:43:37
 */
public class BeanHelper {
     
    static {
        DateConverter converter = new DateConverter();
        converter.setPatterns(new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"});
        ConvertUtils.register(converter, Date.class);
    }  
 
    /**
     * 使用表单数据构建bean对象（只注入名称匹配的属性）
     * @param request
     * @param prefix
     * @param c
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static <T> T bean(HttpServletRequest request,Class<T> c) throws IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchMethodException{
        return (T) bean(request, null, c);
    }
    /**<T> 可以理解为一个类型的声明,否则你的返回值和函数参数中突然出现了一个"T",编译器知道这是什么东西,肯定会报错,所以要从编译器的角度来理解这个问题.
		就跟int a;以后才能使用a这个变量一个道理
     * 使用表单数据构建bean对象（只注入名称匹配的属性）
     * prefix用于指定前缀,当值为a时会处理以a.开头的参数（例如a.name,a.age），null或""时代表无前缀
     * @param <T>
     * @param request
     * @param prefix
     * @param c
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static  <T> T bean(HttpServletRequest request,String prefix,Class<T> c) throws IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchMethodException{
        T obj = c.newInstance();
        Enumeration<?> pNames=request.getParameterNames();
        while(pNames.hasMoreElements()){
            String name=(String)pNames.nextElement();
            String[] value=request.getParameterValues(name);
            if(prefix!=null&&!prefix.isEmpty()){
                if(name.startsWith(prefix+".")){
                    name = name.substring(prefix.length()+1);
                }else{
                    break;
                }
            }
            setProperty(obj, name, value);
        }
        return (T) obj;
    }
    /**
     * 设置对象的属性,pname支持多级，value可以是数组或对象
     * 例1：setProperty(obj, "username","悟空");
     * 例2：setProperty(obj, "category.parent.id",12);
     * 例3：setProperty(obj, "title",12);
     * @param obj
     * @param pname
     * @param value
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     */
    static void setProperty(Object obj,String pname,Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException{
        if(pname.contains(".")){
            String fn1 = pname.substring(0,pname.indexOf("."));
            if(PropertyUtils.getPropertyType(obj, fn1)==null)return;
            String fn2 = pname.substring(pname.indexOf(".")+1,pname.length());
            BeanUtils.setProperty(obj, fn1, PropertyUtils.getPropertyType(obj, fn1).newInstance());
            setProperty(PropertyUtils.getProperty(obj, fn1), fn2,value);
        }else{
            BeanUtils.setProperty(obj, pname, value);
        }
    }
     
}