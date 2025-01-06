package com.assure.vita.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToMany(mappedBy = "roles")
    private List<Utilisateur> utilisateurs;
}
