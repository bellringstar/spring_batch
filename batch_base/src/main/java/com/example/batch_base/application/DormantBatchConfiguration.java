package com.example.batch_base.application;

import com.example.batch_base.batch.Job;
import com.example.batch_base.batch.SimpleTasklet;
import com.example.batch_base.customer.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DormantBatchConfiguration {

    @Bean
    public Job dormantBatchJob(
            DormantBatchItemReader itemReader,
            DormantBatchItemProcessor itemProcessor,
            DormantBatchItemWriter itemWriter,
            DormantBatchJobExecutionListener dormantBatchJobExecutionListener
    ) {
        SimpleTasklet<Customer, Customer> tasklet = new SimpleTasklet<>(
                itemReader,
                itemProcessor,
                itemWriter
        );
        return new Job(
                tasklet,
                dormantBatchJobExecutionListener
        );
    }
}
