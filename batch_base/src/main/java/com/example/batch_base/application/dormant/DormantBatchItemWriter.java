package com.example.batch_base.application.dormant;

import com.example.batch_base.EmailProvider;
import com.example.batch_base.batch.ItemWriter;
import com.example.batch_base.customer.Customer;
import com.example.batch_base.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
public class DormantBatchItemWriter implements ItemWriter<Customer> {

    private final CustomerRepository customerRepository;
    private final EmailProvider emailProvider;

    public DormantBatchItemWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void write(Customer item) {
        customerRepository.save(item);
        emailProvider.send(item.getEmail(), "휴면전환 안내메일입니다.", "내용");

    }
}
