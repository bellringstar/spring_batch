package com.example.batch_base;

import com.example.batch_base.batch.BatchStatus;
import com.example.batch_base.batch.Job;
import com.example.batch_base.batch.JobExecution;
import com.example.batch_base.customer.Customer;
import com.example.batch_base.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DormantBatchJobTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Job dormantBatchJob;

    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 시간이 일년을 경과한 고객이 세명이고, 일년 이내에 로그인한 고객이 다섯명이면 3명의 고객이 휴면전환대상")
    void test1() {
        //given
        saveCustomer(366);
        saveCustomer(366);
        saveCustomer(366);

        saveCustomer(344);
        saveCustomer(344);
        saveCustomer(344);
        saveCustomer(344);
        saveCustomer(344);

        //when
        final JobExecution result = dormantBatchJob.execute();

        //then
        final long dormantCount = customerRepository.findAll()
                .stream().filter(it -> it.getStatus() == Customer.Status.DORMANT).count();

        assertThat(dormantCount).isEqualTo(3);
        assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }



    @Test
    @DisplayName("고객이 10명이 있지만 모두 다 휴면전환대상이 아니면 휴면전환 대상은 0명이다.")
    void test2() {
        //given
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        //when
        final JobExecution result = dormantBatchJob.execute();


        //then
        final long dormantCount = customerRepository.findAll()
                .stream().filter(it -> it.getStatus() == Customer.Status.DORMANT).count();

        assertThat(dormantCount).isEqualTo(0);
        assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    }

    @Test
    @DisplayName("고객이 없는 경우에도 배치는 정상동작해야한다.")
    void test3() {
        //when
        final JobExecution result = dormantBatchJob.execute();


        //then
        final long dormantCount = customerRepository.findAll()
                .stream().filter(it -> it.getStatus() == Customer.Status.DORMANT).count();

        assertThat(dormantCount).isEqualTo(0);
        assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    }

    @Test
    @DisplayName("배치가 실패하면 BatchStatus는 FAILED를 반환해야한다.")
    void test4() {
        final Job dormantBatchJob = new Job(null, null);

        final JobExecution result = dormantBatchJob.execute();

        assertThat(result.getStatus()).isEqualTo(BatchStatus.FAILED);

    }

    private void saveCustomer(long loginMinusDays) {
        final String uuid = UUID.randomUUID().toString();
        final Customer test = new Customer(uuid, uuid + "@gmail.com");
        test.setLoginAt(LocalDateTime.now().minusDays(loginMinusDays));
        customerRepository.save(test);
    }

}