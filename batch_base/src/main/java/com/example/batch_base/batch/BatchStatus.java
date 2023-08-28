package com.example.batch_base.batch;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BatchStatus {

    STARTING,
    FAILED,
    COMPLETED
}
