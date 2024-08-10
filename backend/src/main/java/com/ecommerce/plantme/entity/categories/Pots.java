package com.ecommerce.plantme.entity.categories;
import com.ecommerce.plantme.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pots")
public class Pots extends Product {

    @Column(name = "pot_type")
    private String potType;

    @Column(name = "pot_size")
    private String potSize;

}
