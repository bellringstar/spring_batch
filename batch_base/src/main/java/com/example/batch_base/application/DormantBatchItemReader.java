package com.example.batch_base.application;

import com.example.batch_base.batch.ItemReader;
import com.example.batch_base.customer.Customer;
import com.example.batch_base.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DormantBatchItemReader implements ItemReader<Customer> {

    private final CustomerRepository customerRepository;
    private int pageNo = 0;


    @Override
    public Customer read() {

        final PageRequest pageRequest= PageRequest.of(pageNo, 1, Sort.by("id").ascending());
        final Page<Customer> page = customerRepository.findAll(pageRequest);

        final Customer customer;

        if (page.isEmpty()) {
            pageNo = 0;
            return null;
        } else {
            pageNo++;
            return page.getContent().get(0);
        }
    }
}
