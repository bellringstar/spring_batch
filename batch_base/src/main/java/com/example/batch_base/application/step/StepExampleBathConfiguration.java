package com.example.batch_base.application.step;

import com.example.batch_base.batch.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class StepExampleBathConfiguration {

    @Bean
    public Job stepExampleBatchJob(
            Step step1,
            Step step2,
            Step step3
    ) {
        return new StepJobBuilder()
                .start(step1)
                .next(step2)
                .next(step3)
                .build();
    }

    @Bean
    public Step step1(){
        final Tasklet tasklet = () -> System.out.println("step1");
        return new Step(tasklet);
    }

    @Bean
    public Step step2(){
        return new Step(() -> System.out.println("step2"));
    }

    @Bean
    public Step step3(){
        return new Step(() -> System.out.println("step3"));

    }
}
