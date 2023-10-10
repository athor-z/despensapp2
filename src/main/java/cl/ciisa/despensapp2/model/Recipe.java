package cl.ciisa.despensapp2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String image;

    private String shortDescription;
    
    private int portions; //Cantidad de porciones
    
    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private RecipeDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    private FoodRestriction restriction;

    // constructores
    public Recipe(String name, String image, String shortDescription, int portions, String description, RecipeDifficulty difficulty, FoodRestriction restriction) {
        this.name = name;
        this.image = image;
        this.shortDescription = shortDescription;
        this.portions = portions;
        this.description = description;
        this.difficulty = difficulty;
        this.restriction = restriction;
    }

}