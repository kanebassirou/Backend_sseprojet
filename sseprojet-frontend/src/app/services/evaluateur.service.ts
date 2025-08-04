import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Evaluateur, EvaluateurDTO } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class EvaluateurService {
  private readonly apiUrl = 'http://localhost:8080/api/evaluateurs';

  constructor(private http: HttpClient) { }

  // CRUD Operations
  getAllEvaluateurs(): Observable<Evaluateur[]> {
    return this.http.get<Evaluateur[]>(this.apiUrl);
  }

  getEvaluateurById(id: number): Observable<Evaluateur> {
    return this.http.get<Evaluateur>(`${this.apiUrl}/${id}`);
  }

  createEvaluateur(evaluateur: EvaluateurDTO): Observable<Evaluateur> {
    return this.http.post<Evaluateur>(this.apiUrl, evaluateur);
  }

  updateEvaluateur(id: number, evaluateur: Partial<EvaluateurDTO>): Observable<Evaluateur> {
    return this.http.put<Evaluateur>(`${this.apiUrl}/${id}`, evaluateur);
  }

  deleteEvaluateur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Recherche
  getEvaluateurByEmail(email: string): Observable<Evaluateur> {
    return this.http.get<Evaluateur>(`${this.apiUrl}/email/${email}`);
  }

  searchEvaluateursByName(nom: string): Observable<Evaluateur[]> {
    const params = new HttpParams().set('nom', nom);
    return this.http.get<Evaluateur[]>(`${this.apiUrl}/recherche`, { params });
  }

  // Gestion des projets
  assignerProjet(evaluateurId: number, projetId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${evaluateurId}/assigner-projet/${projetId}`, {});
  }

  retirerProjet(evaluateurId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${evaluateurId}/retirer-projet`);
  }

  getEvaluateursLibres(): Observable<Evaluateur[]> {
    const params = new HttpParams().set('libre', 'true');
    return this.http.get<Evaluateur[]>(this.apiUrl, { params });
  }
}
