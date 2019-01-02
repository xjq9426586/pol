package quartz;

import java.util.Date;

public class MyJob2{

    public void doSomething(){
        System.out.println(new Date() + ": job 2 doing something...");
    }
}