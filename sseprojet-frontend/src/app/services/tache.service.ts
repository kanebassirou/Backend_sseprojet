import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tache, CreateTacheRequest, StatutTache, TacheDTO, StatsTaches } from '../models/tache.model';

@Injectable({
  providedIn: 'root'
})
export class TacheService {
  private readonly apiUrl = 'http://localhost:8080/api/taches';

  constructor(private http: HttpClient) { }

  // CRUD Operations
  getAllTaches(): Observable<Tache[]> {
    return this.http.get<Tache[]>(this.apiUrl);
  }

  getTacheById(id: number): Observable<Tache> {
    return this.http.get<Tache>(`${this.apiUrl}/${id}`);
  }

  createTache(tache: CreateTacheRequest): Observable<Tache> {
    return this.http.post<Tache>(this.apiUrl, tache);
  }

  updateTache(id: number, tache: Partial<CreateTacheRequest>): Observable<Tache> {
    return this.http.put<Tache>(`${this.apiUrl}/${id}`, tache);
  }

  updateTacheStatut(id: number, statut: StatutTache): Observable<Tache> {
    const params = new HttpParams().set('statut', statut);
    return this.http.patch<Tache>(`${this.apiUrl}/${id}/statut`, {}, { params });
  }

  deleteTache(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Filtres par projet
  getTachesByProjet(projetId: number): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.apiUrl}/projet/${projetId}`);
  }

  getTachesByStatut(statut: StatutTache): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.apiUrl}/statut/${statut}`);
  }

  getTachesByProjetAndStatut(projetId: number, statut: StatutTache): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.apiUrl}/projet/${projetId}/statut/${statut}`);
  }

  // Recherche
  searchTaches(intitule: string): Observable<Tache[]> {
    const params = new HttpParams().set('intitule', intitule);
    return this.http.get<Tache[]>(`${this.apiUrl}/recherche`, { params });
  }

  // Tâches en retard
  getTachesEnRetard(): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.apiUrl}/en-retard`);
  }

  getTachesEnRetardByProjet(projetId: number): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.apiUrl}/projet/${projetId}/en-retard`);
  }

  // Statistiques
  getStatsProjet(projetId: number): Observable<StatsTaches> {
    return this.http.get<StatsTaches>(`${this.apiUrl}/stats/projet/${projetId}`);
  }

  getTotalCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count`);
  }

  getCountByProjet(projetId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/projet/${projetId}`);
  }

  // Test endpoint
  testApi(): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/test`);
  }
}
