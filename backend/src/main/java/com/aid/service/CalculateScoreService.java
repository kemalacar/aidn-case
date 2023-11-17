package com.aid.service;

import com.aid.dto.RequestDto;

/**
 * @author Kemal Acar
 */

public interface CalculateScoreService {
    int calculateScore(RequestDto.Measurement measurement);
}

