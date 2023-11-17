package com.aid;

import com.aid.dto.RequestDto;
import com.aid.enums.MeasurementType;
import com.aid.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Kemal Acar
 */
@SpringBootTest
@ActiveProfiles("test")
public class ControllerTest extends BeforeAllTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "TEMP,39,HR,43,RR,19,2",
                    "TEMP,41,HR,121,RR,8,7"
            })
    void test_with_correct_data(MeasurementType type1, int value1,MeasurementType type2, int value2,MeasurementType type3, int value3, int expectedScore) throws Exception {

        RequestDto dto = new RequestDto();
        dto.setMeasurements(new ArrayList<>());

        dto.getMeasurements().add(new RequestDto.Measurement(type1,"",value1));
        dto.getMeasurements().add(new RequestDto.Measurement(type2,"",value2));
        dto.getMeasurements().add(new RequestDto.Measurement(type3,"",value3));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(Constants.API_REQUEST_MAPPING+"/calculate-score")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.score", is(expectedScore)));
    }

}
