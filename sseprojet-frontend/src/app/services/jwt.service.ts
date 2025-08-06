import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  private readonly TOKEN_KEY = 'auth_token';

  constructor() {}

  // Sauvegarder le token
  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  // Récupérer le token
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  // Supprimer le token
  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  // Vérifier si un token existe
  hasToken(): boolean {
    return this.getToken() !== null;
  }

  // Décoder le payload du JWT (sans vérification de signature)
  decodeToken(token?: string): any {
    const tokenToUse = token || this.getToken();
    if (!tokenToUse) return null;

    try {
      const base64Url = tokenToUse.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join('')
      );
      return JSON.parse(jsonPayload);
    } catch (error) {
      console.error('Erreur lors du décodage du token:', error);
      return null;
    }
  }

  // Vérifier si le token est expiré
  isTokenExpired(token?: string): boolean {
    const payload = this.decodeToken(token);
    if (!payload || !payload.exp) return true;

    const currentTime = Math.floor(Date.now() / 1000);
    return payload.exp < currentTime;
  }

  // Récupérer les headers d'autorisation
  getAuthHeaders(): { [key: string]: string } {
    const token = this.getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
  }
}
