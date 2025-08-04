export interface Projet {
  id?: number;
  titre: string;
  description: string;
  dateDebut: Date | string;
  dateFin: Date | string;
  budget: number;
  statut: StatutProjet;
  chefDeProjetId: number;
  chefDeProjet?: any;
  indicateurs?: Indicateur[];
  taches?: Tache[];
  rapports?: Rapport[];
  // evaluations supprimé car non implémenté dans le backend
}

export type StatutProjet = 'EN_COURS' | 'TERMINE' | 'SUSPENDU' | 'PLANIFIE';

export interface CreateProjetRequest {
  titre: string;
  description: string;
  dateDebut: string;
  dateFin: string;
  budget: number;
  statut: StatutProjet;
  chefDeProjetId: number;
}

export interface ProjetResponse {
  id: number;
  titre: string;
  description: string;
  dateDebut: string;
  dateFin: string;
  budget: number;
  statut: StatutProjet;
  chefDeProjetNom: string;
  chefDeProjetEmail: string;
  nombreIndicateurs: number;
  nombreTaches: number;
  nombreRapports: number;
}

// Imports pour éviter les erreurs
import { Indicateur } from './indicateur.model';
import { Tache } from './tache.model';
import { Rapport } from './rapport.model';
// evaluation.model supprimé car non implémenté
