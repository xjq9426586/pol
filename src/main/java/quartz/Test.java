package quartz;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test{

	public static void main(String[] args) throws BeansException {
		int w=1;
		int j=2;
		String b = "5";
		String k=b+w+j;
		
       ApplicationContext ctx = new ClassPathXmlApplicationContext("quartz/spring-mvc.xml");  
       QuartzManager quartzManager = (QuartzManager) ctx.getBean("quartzManager");
        try {  
          //  System.out.println("【系统启动】开始(每1秒输出一次 job2)...");    
//
//            Thread.sleep(5000); 
//            System.out.println("【增加job1启动】开始(每1秒输出一次)...");  
//            quartzManager.addJob("test", "test", "test", "test", MyJob.class, "0/1 * * * * ?");    
//
//            Thread.sleep(5000);    
//            System.out.println("【修改job1时间】开始(每2秒输出一次)...");    
//            quartzManager.modifyJobTime("test", "test", "test", "test", "0/3 * * * * ?");    
//
//            Thread.sleep(10000);    
//            System.out.println("【移除job1定时】开始...");    
//            quartzManager.removeJob("test", "test", "test", "test");    
        	String time="09:25,16:23";
        	String days="1,2,3,4,5";
        	String t[]=time.split(",");
        	String d[]=days.split(",");
        	StringBuffer msb=new StringBuffer();
        	StringBuffer hsb=new StringBuffer();
        	StringBuffer dsb=new StringBuffer();
        	int n=0;
        	for (int i=0;i<t.length ;i++) {
				String m[]=t[i].split(":");
				
				
				msb.append(m[1]);
				if(n!=0){
					Integer mt=Integer.valueOf(m[0])+n;
					hsb.append(mt.toString());
				}else{
					hsb.append(m[0]);
				}
				
				if(i<t.length-1){
					msb.append(",");
					hsb.append(",");
				}
			}
        	for (int i=0;i<d.length ;i++) {
        		dsb.append(d[i]);
        		if(i<d.length-1){
        			dsb.append(",");
				}
			}
        	
        	StringBuffer cron=new StringBuffer();
        	cron.append("0 ");
        	cron.append(msb);
        	cron.append(" ");
        	cron.append(hsb);
        	cron.append(" ? * ");
        	cron.append(dsb);
			System.out.println(cron.toString());
			
			
			quartzManager.addJob("test", "test", "test", "test", MyJob.class, "0 50,51 18,11 * * ?");
			quartzManager.addJob("test1", "test", "test1", "test", MyJob1.class, "0 59 15 * * ?");
			
			
			/*quartzManager.modifyJobTime("test", "test", "test", "test", "0 11 16 * * ?");
			
			
			
			quartzManager.modifyJobTime("test1", "test", "test1", "test", "0 13 16 * * ?");*/
            // 关掉任务调度容器
            // quartzManager.shutdownJobs();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
}
