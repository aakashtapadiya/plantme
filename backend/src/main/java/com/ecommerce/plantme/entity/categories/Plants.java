package com.ecommerce.plantme.entity.categories;
import com.ecommerce.plantme.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_plants")
public class Plants extends Product {

    @Column(name = "plant_type", nullable = false)
    private String plantType;

    @Column(name = "light", nullable = false)
    private String light;

    @Column(name = "plant_location", nullable = false)
    private String plantLocation;

    @Column(name = "maintenance", nullable = false)
    private String maintenance;

    @Column(name = "water_schedule", nullable = false)
    private String waterSchedule;

    @Column(name = "plant_color", nullable = false)
    private String plantColor;

    @Column(name = "plant_size", nullable = false)
    private String plantSize;

}
