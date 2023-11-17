package com.aid;

import com.aid.dto.RequestDto;
import com.aid.enums.MeasurementType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.MessageFormat;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Kemal Acar
 */
@SpringBootTest
@ActiveProfiles("test")
public class ServiceTest extends BeforeAllTest {

    @ParameterizedTest
    @CsvSource(
            {
                    "TEMP,39,1",
                    "HR,43,1",
                    "RR,19,0"
            })
    void test_each_measurement(MeasurementType type, int value, int expectedScore) {
        RequestDto.Measurement measurement = new RequestDto.Measurement();
        measurement.setValue(value);
        measurement.setType(type);

        int score = calculateScoreService.calculateScore(measurement);
        Assertions.assertEquals(score, expectedScore);

    }


}
