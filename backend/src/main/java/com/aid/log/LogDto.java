package com.aid.log;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogDto {

    private String service;

    private Long startTime;

    private String txId;
}


