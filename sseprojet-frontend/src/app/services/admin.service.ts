import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { User,Tache, Indicateur, Rapport } from '../models';
import { Projet } from '../models/projet.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // Utilise votre API users existante
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/users`);
  }

  // Utilise votre API projets existante
  getAllProjets(): Observable<Projet[]> {
    return this.http.get<Projet[]>(`${this.apiUrl}/projets`);
  }

  // Utilise votre API taches existante
  getAllTaches(): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.apiUrl}/taches`);
  }

  // Utilise votre API indicateurs existante
  getAllIndicateurs(): Observable<Indicateur[]> {
    return this.http.get<Indicateur[]>(`${this.apiUrl}/indicateurs`);
  }

  // Utilise votre API rapports existante
  getAllRapports(): Observable<Rapport[]> {
    return this.http.get<Rapport[]>(`${this.apiUrl}/rapports`);
  }

  // Statistiques factices en attendant l'API
  getGlobalStats(): Observable<any> {
    return of({
      totalUsers: 15,
      totalProjets: 8,
      totalTaches: 42,
      totalRapports: 12,
      projetsEnCours: 5,
      tachesTerminees: 28
    });
  }

  // CRUD utilisateurs
  createUser(user: any): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/users`, user);
  }

  updateUser(id: number, user: any): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/users/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/users/${id}`);
  }
}
