package log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonLogger {
    Logger logger = LoggerFactory.getLogger(CommonLogger.class);
    public static String strLog = null;

    /**
     * 前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点前的执行
     * 
     * @param jp
     *            连接点：程序执行过程中的某一行为，例如，AServiceImpl.barA()的调用或者抛出的异常行为
     */
    public void doBefore(JoinPoint jp) {
        strLog = "doBefore Begining method: " + jp.getTarget().getClass().getName()
                + "." + jp.getSignature().getName();
        logger.warn(strLog);
    }

    /**
     * 环绕通知：包围一个连接点的通知，可以在方法的调用前后完成自定义的行为，也可以选择不执行
     * 类似Web中Servlet规范中的Filter的doFilter方法。
     * 
     * @param pjp
     *            当前进程中的连接点
     * @return
     * @throws Throwable
     */
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        System.out.println("process time: " + time + " ms");
        strLog = "doAround processing method: "
                + pjp.getTarget().getClass().getName() + "."
                + pjp.getSignature().getName() + " process time: " + time + " ms";
        logger.warn(strLog);
        return retVal;
    }

    /**
     * 抛出异常后通知 ： 在方法抛出异常退出时执行的通知。
     * 
     * @param jp
     *            连接点：程序执行过程中的某一行为，例如，AServiceImpl.barA()的调用或者抛出的异常行为
     */
    public void doAfter(JoinPoint jp) {
//      org.apache.ibatis.logging.LogFactory.useLog4JLogging();
        strLog = "doAfter:log Ending method: "
                + jp.getTarget().getClass().getName() + "."
                + jp.getSignature().getName();
        logger.warn(strLog);
    }


    /**
     * 捕获异常
     * @param jp
     * @param e
     * @throws Throwable
     */
    public void doAfterThrowing(JoinPoint jp, Exception e) throws Throwable {
        String strLog = "doAfterThrowing Begining method: " + jp.getTarget().getClass().getName()
                + "." + jp.getSignature().getName();
        logger.error("异常抛出exception " + strLog, e);
//       logger.error(Level.ERROR, "异常抛出exception", e);
    }
}
