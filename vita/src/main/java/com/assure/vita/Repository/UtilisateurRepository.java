package com.assure.vita.Repository;

import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Enum.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>, 
    JpaSpecificationExecutor<Utilisateur> {
    Optional<Utilisateur> findByEmail(String email);

    Page<Utilisateur> findByRole(Role role, Pageable pageable);

}
