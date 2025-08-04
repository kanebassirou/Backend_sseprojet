import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rapport, CreateRapportRequest, TypeRapport, RapportDTO } from '../models/rapport.model';

@Injectable({
  providedIn: 'root'
})
export class RapportService {
  private readonly apiUrl = 'http://localhost:8080/api/rapports';

  constructor(private http: HttpClient) { }

  // CRUD Operations
  getAllRapports(): Observable<RapportDTO[]> {
    return this.http.get<RapportDTO[]>(this.apiUrl);
  }

  getRapportById(id: number): Observable<Rapport> {
    return this.http.get<Rapport>(`${this.apiUrl}/${id}`);
  }

  createRapport(rapport: CreateRapportRequest): Observable<Rapport> {
    return this.http.post<Rapport>(this.apiUrl, rapport);
  }

  updateRapport(id: number, rapport: Partial<CreateRapportRequest>): Observable<Rapport> {
    return this.http.put<Rapport>(`${this.apiUrl}/${id}`, rapport);
  }

  deleteRapport(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Filtres
  getRapportsByProjet(projetId: number): Observable<RapportDTO[]> {
    return this.http.get<RapportDTO[]>(`${this.apiUrl}/projet/${projetId}`);
  }

  getRapportsByAuteur(auteur: string): Observable<RapportDTO[]> {
    return this.http.get<RapportDTO[]>(`${this.apiUrl}/auteur/${auteur}`);
  }

  getRapportsByProjetAndAuteur(projetId: number, auteur: string): Observable<RapportDTO[]> {
    return this.http.get<RapportDTO[]>(`${this.apiUrl}/projet/${projetId}/auteur/${auteur}`);
  }

  // Recherche
  searchRapports(titre: string): Observable<RapportDTO[]> {
    const params = new HttpParams().set('titre', titre);
    return this.http.get<RapportDTO[]>(`${this.apiUrl}/recherche`, { params });
  }

  getRapportsByPeriode(dateDebut: string, dateFin: string): Observable<RapportDTO[]> {
    const params = new HttpParams()
      .set('dateDebut', dateDebut)
      .set('dateFin', dateFin);
    return this.http.get<RapportDTO[]>(`${this.apiUrl}/periode`, { params });
  }

  // Génération automatique
  genererRapportAuto(projetId: number): Observable<Rapport> {
    return this.http.post<Rapport>(`${this.apiUrl}/generer-auto/${projetId}`, {});
  }

  // Statistiques
  getCountByProjet(projetId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/projet/${projetId}`);
  }

  // Test endpoint
  testApi(): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/test`);
  }

  // Export (si disponible dans l'API)
  exportRapport(id: number, format: string): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${id}/export/${format}`, { 
      responseType: 'blob' 
    });
  }
}
