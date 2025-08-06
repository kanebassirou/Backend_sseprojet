export interface Evaluation {
  id?: number;
  projetId: number;
  evaluateurId: number;
  dateEvaluation?: Date;
  statut: StatutEvaluation;
  note?: number;
  commentaire?: string;
  criteres?: CritereEvaluation[];
}

export type StatutEvaluation = 'EN_ATTENTE' | 'EN_COURS' | 'TERMINEE';

export interface CritereEvaluation {
  id?: number;
  nom: string;
  description?: string;
  note: number;
  poids: number;
  commentaire?: string;
}

export interface CreateEvaluationRequest {
  projetId: number;
  evaluateurId: number;
  note?: number;
  commentaire?: string;
  criteres?: CritereEvaluation[];
}

export interface UpdateEvaluationRequest {
  note?: number;
  commentaire?: string;
  statut?: StatutEvaluation;
  criteres?: CritereEvaluation[];
}
