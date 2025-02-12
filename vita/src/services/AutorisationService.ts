import { UserRole } from '../enums/UserRole';
import { User } from '../types/User';

class AutorisationService {
    peutGererUtilisateurs(user: User): boolean {
        return user.role === UserRole.ADMINISTRATEUR;
    }

    peutTraiterDossiers(user: User): boolean {
        return user.role === UserRole.FONCTIONNAIRE;
    }

    peutSoumettreDemandeAssurance(user: User): boolean {
        return user.role === UserRole.ADHERENT;
    }

    peutAjouterFonctionnaire(user: User): boolean {
        return user.role === UserRole.ADMINISTRATEUR;
    }
} 