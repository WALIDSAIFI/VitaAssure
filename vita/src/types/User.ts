import { UserRole } from '../enums/UserRole';

interface User {
    id: string;
    nom: string;
    prenom: string;
    email: string;
    role: UserRole;
} 