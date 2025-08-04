export interface User {
  id?: number;
  nom: string;
  email: string;
  motDePasseHash?: string;
  type: UserType;
  dateCreation?: Date;
}

export interface Administrateur extends User {
  type: 'ADMINISTRATEUR';
}

export interface ChefDeProjet extends User {
  type: 'CHEF_DE_PROJET';
  projets?: Projet[];
}

export interface Decideur extends User {
  type: 'DECIDEUR';
}

export interface Evaluateur extends User {
  type: 'EVALUATEUR';
  projet?: Projet;
}

export type UserType = 'ADMINISTRATEUR' | 'CHEF_DE_PROJET' | 'DECIDEUR' | 'EVALUATEUR';

export interface CreateUserRequest {
  nom: string;
  email: string;
  motDePasseHash: string;
  type: UserType;
}

export interface EvaluateurDTO {
  nom: string;
  email: string;
  motDePasseHash: string;
  projetId?: number;
}

// Import pour éviter les erreurs de type
import { Projet } from './projet.model';
