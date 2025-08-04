import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Projet, CreateProjetRequest, ProjetResponse, StatutProjet } from '../models/projet.model';

@Injectable({
  providedIn: 'root'
})
export class ProjetService {
  private readonly apiUrl = 'http://localhost:8080/api/projets';

  constructor(private http: HttpClient) { }

  // CRUD Operations
  getAllProjets(): Observable<ProjetResponse[]> {
    return this.http.get<ProjetResponse[]>(this.apiUrl);
  }

  getProjetById(id: number): Observable<Projet> {
    return this.http.get<Projet>(`${this.apiUrl}/${id}`);
  }

  createProjet(projet: CreateProjetRequest): Observable<Projet> {
    return this.http.post<Projet>(this.apiUrl, projet);
  }

  updateProjet(id: number, projet: Partial<CreateProjetRequest>): Observable<Projet> {
    return this.http.put<Projet>(`${this.apiUrl}/${id}`, projet);
  }

  deleteProjet(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Filtres
  getProjetsByStatut(statut: StatutProjet): Observable<ProjetResponse[]> {
    return this.http.get<ProjetResponse[]>(`${this.apiUrl}/statut/${statut}`);
  }

  getProjetsByChef(chefId: number): Observable<ProjetResponse[]> {
    return this.http.get<ProjetResponse[]>(`${this.apiUrl}/chef/${chefId}`);
  }

  searchProjets(titre?: string, statut?: StatutProjet): Observable<ProjetResponse[]> {
    let params = new HttpParams();
    if (titre) params = params.set('titre', titre);
    if (statut) params = params.set('statut', statut);
    
    return this.http.get<ProjetResponse[]>(`${this.apiUrl}/recherche`, { params });
  }

  // Statistiques
  getProjetStats(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/stats`);
  }

  getAvancementProjet(projetId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${projetId}/avancement`);
  }
}
