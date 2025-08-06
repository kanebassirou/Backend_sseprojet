import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { User, UserType, LoginRequest, LoginResponse, RegisterRequest } from '../models/user.model';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_BASE_URL = 'http://localhost:8080/api/auth';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router,
    private jwtService: JwtService
  ) {
    this.initializeAuthState();
  }

  private initializeAuthState(): void {
    // 🧪 TEMPORAIRE: Utilisateur de test pour déboguer l'interface
    const testUser: User = {
      id: 999,
      nom: 'Pierre Martin',
      email: 'pierre.martin@test.com',
      type: 'CHEF_DE_PROJET' as UserType
    };
    
    // Simuler une connexion de test
    this.currentUserSubject.next(testUser);
    localStorage.setItem('currentUser', JSON.stringify(testUser));
    localStorage.setItem('access_token', 'test-token-123');
    console.log('🧪 Utilisateur de test connecté:', testUser.nom);
  }

  // 🔑 Connexion avec l'API JWT
  login(credentials: LoginRequest): Observable<User> {
    const url = `${this.API_BASE_URL}/login`;

    return this.http.post<LoginResponse>(url, credentials).pipe(
      tap(response => {
        // Sauvegarder le token
        this.jwtService.setToken(response.token);

        // Créer un objet User à partir de la réponse backend
        const user: User = {
          id: response.id,
          nom: response.nom,
          email: response.email,
          type: this.mapRoleToUserType(response.role) // Convertir le rôle backend vers UserType
        };

        // Sauvegarder l'utilisateur dans localStorage
        localStorage.setItem('currentUser', JSON.stringify(user));

        // Mettre à jour l'utilisateur
        this.currentUserSubject.next(user);

        console.log('Connexion réussie:', user.nom, '-', user.type);
      }),
      map(response => {
        // Créer et retourner l'objet User
        return {
          id: response.id,
          nom: response.nom,
          email: response.email,
          type: this.mapRoleToUserType(response.role)
        } as User;
      }),
      tap(user => {
        // Redirection selon le rôle
        this.redirectByRole(user.type);
      }),
      catchError(error => {
        console.error('Erreur de connexion:', error);
        let errorMessage = 'Erreur de connexion';

        if (error.status === 400) {
          errorMessage = 'Email ou mot de passe incorrect';
        } else if (error.status === 0) {
          errorMessage = 'Impossible de contacter le serveur';
        } else if (error.error?.message) {
          errorMessage = error.error.message;
        }

        return throwError(() => new Error(errorMessage));
      })
    );
  }

  // 👤 Récupérer le profil de l'utilisateur connecté
  getProfile(): Observable<User> {
    const url = `${this.API_BASE_URL}/me`;
    const headers = new HttpHeaders(this.jwtService.getAuthHeaders());

    return this.http.get<User>(url, { headers }).pipe(
      catchError(error => {
        console.error('Erreur lors de la récupération du profil:', error);
        return throwError(() => error);
      })
    );
  }

  // 📝 Inscription d'un nouvel utilisateur
  register(userData: RegisterRequest): Observable<User> {
    const url = `${this.API_BASE_URL}/register`;

    return this.http.post<LoginResponse>(url, userData).pipe(
      tap(response => {
        // Sauvegarder le token (connexion automatique après inscription)
        this.jwtService.setToken(response.token);

        // Créer un objet User à partir de la réponse backend
        const user: User = {
          id: response.id,
          nom: response.nom,
          email: response.email,
          type: this.mapRoleToUserType(response.role)
        };

        this.currentUserSubject.next(user);
      }),
      map(response => {
        return {
          id: response.id,
          nom: response.nom,
          email: response.email,
          type: this.mapRoleToUserType(response.role)
        } as User;
      }),
      catchError(error => {
        console.error('Erreur d\'inscription:', error);
        let errorMessage = 'Erreur lors de l\'inscription';

        if (error.status === 400) {
          errorMessage = 'Données invalides ou email déjà utilisé';
        } else if (error.error?.message) {
          errorMessage = error.error.message;
        }

        return throwError(() => new Error(errorMessage));
      })
    );
  }

  // 🚪 Déconnexion
  logout(): void {
    console.log('Déconnexion en cours...');
    this.clearAuthData();
    this.router.navigate(['/sign-in']);
  }

  // 🧹 Nettoyer toutes les données d'authentification
  private clearAuthData(): void {
    this.jwtService.removeToken();
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  // ✅ Vérifier si l'utilisateur est connecté
  isLoggedIn(): boolean {
    return this.currentUserSubject.value !== null &&
           this.jwtService.hasToken() &&
           !this.jwtService.isTokenExpired();
  }

  // 👤 Obtenir l'utilisateur actuel
  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  // 🔐 Vérifier le rôle
  hasRole(role: UserType): boolean {
    const user = this.getCurrentUser();
    return user ? user.type === role : false;
  }

  // 👑 Vérifier si admin
  isAdmin(): boolean {
    return this.hasRole('ADMINISTRATEUR');
  }

  // 🎯 Redirection selon le rôle
  private redirectByRole(userType: UserType): void {
    console.log('Redirection pour le rôle:', userType);

    setTimeout(() => {
      switch (userType) {
        case 'ADMINISTRATEUR':
          this.router.navigate(['/app/dashboard']);
          break;
        case 'CHEF_DE_PROJET':
          this.router.navigate(['/app/projets']);
          break;
        case 'DECIDEUR':
          this.router.navigate(['/app/dashboard']);
          break;
        case 'EVALUATEUR':
          this.router.navigate(['/app/evaluations']);
          break;
        default:
          this.router.navigate(['/app/dashboard']);
      }
    }, 1000);
  }

  // 🧪 Méthode de test pour initialiser l'admin (dev only)
  initializeAdmin(): Observable<any> {
    const url = `${this.API_BASE_URL}/init-admin`;
    return this.http.post(url, {}).pipe(
      catchError(error => {
        console.error('Erreur lors de l\'initialisation admin:', error);
        return throwError(() => error);
      })
    );
  }

  // 🔍 Test de l'API
  testApi(): Observable<any> {
    const url = `${this.API_BASE_URL}/test`;
    return this.http.get(url);
  }

  // 📋 Lister tous les utilisateurs (admin only)
  getAllUsers(): Observable<User[]> {
    const url = `${this.API_BASE_URL}/list-users`;
    const headers = new HttpHeaders(this.jwtService.getAuthHeaders());

    return this.http.get<User[]>(url, { headers }).pipe(
      catchError(error => {
        console.error('Erreur lors de la récupération des utilisateurs:', error);
        return throwError(() => error);
      })
    );
  }

  // 🔄 Mapper les rôles backend vers UserType frontend
  private mapRoleToUserType(backendRole: string): UserType {
    switch (backendRole) {
      case 'ADMINISTRATEUR':
        return 'ADMINISTRATEUR';
      case 'CHEF_PROJET':
        return 'CHEF_DE_PROJET';
      case 'DECIDEUR':
        return 'DECIDEUR';
      case 'EVALUATEUR':
        return 'EVALUATEUR';
      default:
        console.warn('Rôle inconnu:', backendRole);
        return 'DECIDEUR'; // Valeur par défaut
    }
  }

  // 🔄 Mapper UserType frontend vers rôle backend
  private mapUserTypeToRole(userType: UserType): string {
    switch (userType) {
      case 'ADMINISTRATEUR':
        return 'ADMINISTRATEUR';
      case 'CHEF_DE_PROJET':
        return 'CHEF_PROJET';
      case 'DECIDEUR':
        return 'DECIDEUR';
      case 'EVALUATEUR':
        return 'EVALUATEUR';
      default:
        return 'DECIDEUR';
    }
  }
}
