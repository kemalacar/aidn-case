package com.aid.persistance.repository;

import com.aid.enums.MeasurementType;
import com.aid.persistance.entity.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Kemal Acar
 */

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long>  {
    @Query("Select m  from MeasurementEntity m where m.type=:type and m.startValue<:measurementValue and m.endValue>=:measurementValue")
    Optional<MeasurementEntity> findByTypeAndValue(MeasurementType type, int measurementValue);
}

