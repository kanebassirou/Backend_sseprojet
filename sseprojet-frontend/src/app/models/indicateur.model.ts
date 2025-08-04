import { Projet } from './projet.model';

export interface Indicateur {
  id?: number;
  nom: string;
  type: TypeIndicateur;
  objectif: string;
  valeurCible?: number;
  valeurReelle?: number;
  unite?: string;
  dateCreation?: Date;
  projet?: Projet;
  projetId?: number;
}

export type TypeIndicateur = 'QUANTITATIF' | 'QUALITATIF' | 'PERFORMANCE' | 'QUALITE' | 'FINANCIER' | 'TEMPOREL';

export interface CreateIndicateurRequest {
  nom: string;
  type: TypeIndicateur;
  objectif: string;
  valeurCible?: number;
  unite?: string;
  projetId: number;
}
