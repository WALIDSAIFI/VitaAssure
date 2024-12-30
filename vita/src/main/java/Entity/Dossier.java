package Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assure_id")
    private Assure assure;

    private String statut;

    @OneToMany(mappedBy = "dossier")
    private List<DemandeRemboursement> demandes;
}
