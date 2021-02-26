package classLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class HotClassLoaderMain {

    private URLClassLoader classLoader;
    private Object worker;
    private long lastTime;
    private String classDir = "D:\\work1\\pol\\target\\classes\\classLoader\\";

    public static void main(String[] args) throws Exception {
        HotClassLoaderMain helloMain = new HotClassLoaderMain();
        helloMain.execute();
    }

    private void execute() throws Exception {
        while (true) {
            //监测是否需要加载
            if (checkIsNeedLoad()) {
                System.out.println("检测到新版本，准备重新加载");
                load();
                System.out.println("重新加载完成");
            }
            //一秒
            invokeMethod();
            Thread.sleep(1000);
        }
    }

    private void invokeMethod() throws Exception {
        //通过反射方式调用
        //使用反射的主要原因是：不想Work被appclassloader加载，
        //如果被appclassloader加载的话，再通过自定义加载器加载会有点问题
        //load();
        Method method = worker.getClass().getDeclaredMethod("sayHello", null);
        method.invoke(worker, null);
    }

    private void load() throws Exception {
        classLoader = new HotClassLoader(new URL[]{new URL(
                "file:D:\\work1\\pol\\target\\classes\\")});
        worker = classLoader.loadClass("classLoader.Worker")
                .newInstance();

    }

    //检查文件是否被更新
    private boolean checkIsNeedLoad() {
        File file = new File(classDir + "Worker.class");
        long newTime = file.lastModified();
        if (lastTime < newTime) {
            lastTime = newTime;
            return true;
        }
        return false;
    }
}