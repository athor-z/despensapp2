package cl.ciisa.despensapp2.model;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    //@Column(nullable = false)
    //private String description;
    
    @Enumerated(EnumType.STRING)
    private MeasureUnit measureUnit;
    
}