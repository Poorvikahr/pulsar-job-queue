package com.poorvika.distqueue.worker;

import com.poorvika.distqueue.model.Job;
import com.poorvika.distqueue.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Worker {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExecutor jobExecutor;

    @Scheduled(fixedDelay = 3000) // runs every 3 seconds
    public void pollAndProcess() {
        List<Job> queuedJobs = jobRepository.findAll().stream()
                .filter(job -> "QUEUED".equals(job.getStatus()))
                .toList();

        if (queuedJobs.isEmpty()) {
            return; // nothing to do
        }

        Job job = queuedJobs.get(0); // oldest first (simple version for now)

        try {
            job.setStatus("IN_PROGRESS");
            jobRepository.save(job);

            jobExecutor.execute(job);

            job.setStatus("COMPLETED");
            jobRepository.save(job);
        } catch (Exception e) {
            job.setStatus("FAILED");
            jobRepository.save(job);
        }
    }
}