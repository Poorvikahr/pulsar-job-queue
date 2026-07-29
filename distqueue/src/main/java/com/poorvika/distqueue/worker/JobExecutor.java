package com.poorvika.distqueue.worker;

import com.poorvika.distqueue.model.Job;
import org.springframework.stereotype.Component;

@Component
public class JobExecutor {

    public void execute(Job job) throws InterruptedException {
        System.out.println("Starting job: " + job.getId() + " [" + job.getType() + "]");
        Thread.sleep(5000); // simulate work taking 5 seconds
        System.out.println("Finished job: " + job.getId());
    }
}
