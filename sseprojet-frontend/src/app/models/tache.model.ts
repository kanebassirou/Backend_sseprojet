import { Projet } from './projet.model';

export interface Tache {
  id?: number;
  intitule: string;
  description?: string;
  dateDebut: Date | string;
  dateFin: Date | string;
  statut: StatutTache;
  priorite?: Priorite;
  assigneA?: string;
  projet?: Projet;
  projetId: number;
  estEnRetard?: boolean;
}

export type StatutTache = 'A_FAIRE' | 'EN_COURS' | 'TERMINEE' | 'EN_ATTENTE';
export type Priorite = 'BASSE' | 'MOYENNE' | 'HAUTE' | 'CRITIQUE';

export interface CreateTacheRequest {
  intitule: string;
  description?: string;
  dateDebut: string;
  dateFin: string;
  statut: StatutTache;
  priorite?: Priorite;
  assigneA?: string;
  projetId: number;
}

export interface TacheDTO {
  id?: number;
  intitule: string;
  dateDebut: string;
  dateFin: string;
  statut: StatutTache;
  projetTitre: string;
  estEnRetard: boolean;
}

export interface StatsTaches {
  total: number;
  aFaire: number;
  enCours: number;
  terminees: number;
  enAttente: number;
  enRetard: number;
}
