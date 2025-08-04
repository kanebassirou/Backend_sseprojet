import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Indicateur, CreateIndicateurRequest, TypeIndicateur } from '../models/indicateur.model';

@Injectable({
  providedIn: 'root'
})
export class IndicateurService {
  private readonly apiUrl = 'http://localhost:8080/api/indicateurs';

  constructor(private http: HttpClient) { }

  // CRUD Operations
  getAllIndicateurs(): Observable<Indicateur[]> {
    return this.http.get<Indicateur[]>(this.apiUrl);
  }

  getIndicateurById(id: number): Observable<Indicateur> {
    return this.http.get<Indicateur>(`${this.apiUrl}/${id}`);
  }

  createIndicateur(indicateur: CreateIndicateurRequest): Observable<Indicateur> {
    return this.http.post<Indicateur>(this.apiUrl, indicateur);
  }

  updateIndicateur(id: number, indicateur: Partial<CreateIndicateurRequest>): Observable<Indicateur> {
    return this.http.put<Indicateur>(`${this.apiUrl}/${id}`, indicateur);
  }

  deleteIndicateur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Filtres
  getIndicateursByProjet(projetId: number): Observable<Indicateur[]> {
    return this.http.get<Indicateur[]>(`${this.apiUrl}/projet/${projetId}`);
  }

  getIndicateursByType(type: TypeIndicateur): Observable<Indicateur[]> {
    return this.http.get<Indicateur[]>(`${this.apiUrl}/type/${type}`);
  }

  // Recherche
  searchIndicateurs(nom: string): Observable<Indicateur[]> {
    const params = new HttpParams().set('nom', nom);
    return this.http.get<Indicateur[]>(`${this.apiUrl}/recherche`, { params });
  }

  // Mise à jour des valeurs
  updateValeurReelle(id: number, valeurReelle: number): Observable<Indicateur> {
    const params = new HttpParams().set('valeurReelle', valeurReelle.toString());
    return this.http.patch<Indicateur>(`${this.apiUrl}/${id}/valeur-reelle`, {}, { params });
  }

  // Statistiques
  getIndicateursStats(projetId?: number): Observable<any> {
    const params = projetId ? new HttpParams().set('projetId', projetId.toString()) : new HttpParams();
    return this.http.get<any>(`${this.apiUrl}/stats`, { params });
  }
}
