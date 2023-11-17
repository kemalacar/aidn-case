package com.aid;

import com.aid.log.LogDto;
import com.aid.log.LogThreadLocal;
import com.aid.persistance.repository.MeasurementRepository;
import com.aid.service.CalculateScoreService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BeforeAllTest {

    @Autowired
    protected MeasurementRepository measurementRepository;

    @Autowired
    protected CalculateScoreService calculateScoreService;


    @BeforeAll
    public void setUp() {
        LogThreadLocal.set(LogDto.builder().startTime(System.currentTimeMillis()).build());
        MockitoAnnotations.openMocks(this);
        System.out.println("before all");
    }


    @AfterAll
    public void cleanUp() {
    }

}

