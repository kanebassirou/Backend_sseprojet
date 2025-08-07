import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User, CreateUserRequest, UserType } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  // CRUD Operations
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  createUser(user: CreateUserRequest): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  updateUser(id: number, user: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Filtres spécifiques
  getUsersByType(type: UserType): Observable<User[]> {
    // Utiliser l'endpoint qui fonctionne et filtrer côté client
    return this.http.get<User[]>('http://localhost:8080/api/auth/list-users').pipe(
      map((users: any[]) => {
        // Mapper le rôle de la base de données vers le type frontend
        const mappedUsers = users.map(user => ({
          ...user,
          type: this.mapRoleToUserType(user.role || user.type)
        }));
        // Filtrer par type
        return mappedUsers.filter(user => user.type === type);
      })
    );
  }

  private mapRoleToUserType(role: string): UserType {
    switch(role) {
      case 'CHEF_PROJET': return 'CHEF_DE_PROJET';
      case 'ADMINISTRATEUR': return 'ADMINISTRATEUR';
      case 'DECIDEUR': return 'DECIDEUR';
      case 'EVALUATEUR': return 'EVALUATEUR';
      default: return role as UserType;
    }
  }

  searchUsersByName(nom: string): Observable<User[]> {
    const params = new HttpParams().set('nom', nom);
    return this.http.get<User[]>(`${this.apiUrl}/recherche`, { params });
  }

  getUserByEmail(email: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/email/${email}`);
  }
}
