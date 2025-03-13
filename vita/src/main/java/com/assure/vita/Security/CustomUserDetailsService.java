package com.assure.vita.Security;

import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {


    private final UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
log.info(utilisateur.toString());

        return UserPrincipal.create(utilisateur);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'id : " + id));
        log.info("Loading user by ID: {}, Role: {}", id, utilisateur.getRole().name());
        return UserPrincipal.create(utilisateur);
    }
} 