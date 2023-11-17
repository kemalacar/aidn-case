package com.aid.service;

import com.aid.dto.RequestDto;
import com.aid.enums.MeasurementType;
import com.aid.persistance.entity.MeasurementEntity;
import com.aid.persistance.repository.MeasurementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Optional;

/**
 * @author Kemal Acar
 */
@AllArgsConstructor
@Slf4j
@Service
public class CalculateScoreServiceImpl implements CalculateScoreService {

    private final MeasurementRepository measurementRepository;

    @Cacheable(value="scoreCache",  key="{ #measurement.type, #measurement.value } ")
    public int calculateScore(RequestDto.Measurement measurement) {

        if (measurement.getValue() == null) {
            log.error(MessageFormat.format("{0} input is null  ", measurement.getTitle()));
            throw new InvalidParameterException(MessageFormat.format("<b>{0}</b> input is null  ", measurement.getTitle()));
        }

        Optional<MeasurementEntity> optional = measurementRepository.findByTypeAndValue(measurement.getType(), measurement.getValue());
        if (optional.isPresent()) {
            log.info(MessageFormat.format("score found  = {0}  ", optional.get().getScore()));
            return  optional.get().getScore();
        } else {
            log.error(MessageFormat.format("{0} input is invalid  ", measurement.getTitle()));
            throw new InvalidParameterException(MessageFormat.format("<b>{0}</b> input is invalid  ", measurement.getTitle()));
        }
    }


    @PostConstruct
    public void initiateData() {
        measurementRepository.deleteAll();
        //TEMP
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.TEMP).startValue(31).endValue(35).score(3).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.TEMP).startValue(35).endValue(36).score(1).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.TEMP).startValue(36).endValue(38).score(0).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.TEMP).startValue(38).endValue(39).score(1).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.TEMP).startValue(39).endValue(42).score(2).build());

//HR
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.HR).startValue(25).endValue(40).score(3).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.HR).startValue(40).endValue(50).score(1).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.HR).startValue(50).endValue(90).score(0).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.HR).startValue(90).endValue(110).score(1).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.HR).startValue(110).endValue(130).score(2).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.HR).startValue(130).endValue(220).score(3).build());

//RR
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.RR).startValue(3).endValue(8).score(3).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.RR).startValue(8).endValue(11).score(1).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.RR).startValue(11).endValue(20).score(0).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.RR).startValue(20).endValue(24).score(2).build());
        measurementRepository.save(MeasurementEntity.builder().type(MeasurementType.RR).startValue(24).endValue(60).score(3).build());
    }
}

