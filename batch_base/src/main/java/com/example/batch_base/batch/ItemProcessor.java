package com.example.batch_base.batch;

public interface ItemProcessor<I, O> {

    O process(I item);
}
