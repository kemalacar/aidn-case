package com.aid.dto;

import com.aid.enums.MeasurementType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {

    @NotNull(message = "cannot be null")
    private List<Measurement> measurements;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Measurement {

        @NotNull(message = "cannot be null")
        private MeasurementType type;

        private String title;

        @NotNull(message = "cannot be null")
        private Integer value;

    }


}

