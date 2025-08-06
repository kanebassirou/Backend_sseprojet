import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { JwtService } from '../services/jwt.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const jwtService = inject(JwtService);

  // Ajouter le token JWT aux requêtes (sauf pour les endpoints d'authentification)
  if (jwtService.hasToken() && !req.url.includes('/api/auth/login') && !req.url.includes('/api/auth/register')) {
    const authHeaders = jwtService.getAuthHeaders();

    const authReq = req.clone({
      setHeaders: authHeaders
    });

    return next(authReq);
  }

  return next(req);
};
