package com.aid.controller;

import com.aid.dto.ResponseDto;
import com.aid.dto.RequestDto;
import com.aid.service.CalculateScoreService;
import com.aid.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Kemal Acar
 */
@RestController
@AllArgsConstructor
@RequestMapping(path = Constants.API_REQUEST_MAPPING)
public class CalculateScoreController {

    private final CalculateScoreService calculateScoreService;

    @PostMapping(path = "/calculate-score")
    public ResponseEntity<ResponseDto> calculate(@Valid @RequestBody RequestDto requestDto) {
        ResponseDto responseDto = ResponseDto.builder().score(requestDto.getMeasurements().stream().mapToInt(calculateScoreService::calculateScore).sum()).build();
        return ResponseEntity.ok(responseDto);
    }


}

