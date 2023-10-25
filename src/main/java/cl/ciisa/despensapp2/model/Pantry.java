package cl.ciisa.despensapp2.model;

import lombok.*;

import java.io.Serializable;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pantry")
public class Pantry implements Serializable{

	private static final long serialVersionUID = -9118982565949964300L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
/*
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
*/
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}