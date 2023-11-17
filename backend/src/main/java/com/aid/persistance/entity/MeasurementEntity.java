package com.aid.persistance.entity;

import com.aid.enums.MeasurementType;
import lombok.*;

import javax.persistence.*;

/**
 * @author Kemal Acar
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEASUREMENTS")
public class MeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MeasurementType type;

    private int startValue;

    private int endValue;

    private int score;

}



