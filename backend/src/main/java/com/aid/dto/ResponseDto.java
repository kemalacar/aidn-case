package com.aid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Kemal Acar
 */

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ResponseDto {
    private Integer score;
}

