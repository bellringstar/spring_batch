package com.example.batch_base.application;

import com.example.batch_base.EmailProvider;
import com.example.batch_base.batch.JobExecution;
import com.example.batch_base.batch.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchJobExecutionListener implements JobExecutionListener {

    private final EmailProvider emailProvider;

    public DormantBatchJobExecutionListener() {
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // no-op
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        emailProvider.send("admin@gmail.com", "배치 완료 알림",
        "DormantBatchJob이 수행됐습니다. status: " + jobExecution.getStatus());
    }
}
