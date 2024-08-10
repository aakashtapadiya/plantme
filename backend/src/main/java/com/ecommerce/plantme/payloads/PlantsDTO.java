package com.ecommerce.plantme.payloads;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PlantsDTO {

    private String plantType;
    private String light;
    private String plantLocation;
    private String maintenance;
    private String waterSchedule;
    private String plantColor;
    private String plantSize;

}