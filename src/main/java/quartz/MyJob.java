package quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobDetailImpl;

public class MyJob implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new Date() + ": job 0 doing something...");
        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        String jobName = ((JobDetailImpl) jobDetail).getGroup();   //任务名称


        // The directory to scan is stored in the job map
        JobDataMap dataMap = jobDetail.getJobDataMap();     //任务所配置的数据映射表

        System.out.println(jobName);

    }
}
