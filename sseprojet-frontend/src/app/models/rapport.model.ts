import { Projet } from './projet.model';

export interface Rapport {
  id?: number;
  titre: string;
  contenu: string;
  dateGeneration: Date | string;
  auteur: string;
  typeRapport?: TypeRapport;
  format?: FormatRapport;
  projet?: Projet;
  projetId: number;
}

export type TypeRapport = 'MENSUEL' | 'TRIMESTRIEL' | 'ANNUEL' | 'PONCTUEL' | 'FINAL';
export type FormatRapport = 'PDF' | 'WORD' | 'EXCEL' | 'HTML';

export interface CreateRapportRequest {
  titre: string;
  contenu: string;
  dateGeneration: string;
  auteur: string;
  typeRapport?: TypeRapport;
  format?: FormatRapport;
  projetId: number;
}

export interface RapportDTO {
  id: number;
  titre: string;
  dateGeneration: string;
  auteur: string;
  typeRapport: TypeRapport;
  projetTitre: string;
  tailleContenu: number;
}
