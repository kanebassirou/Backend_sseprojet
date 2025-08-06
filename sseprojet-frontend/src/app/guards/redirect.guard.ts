import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const redirectGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    // Si l'utilisateur est connecté, rediriger vers le dashboard
    router.navigate(['/app/dashboard']);
  } else {
    // Si l'utilisateur n'est pas connecté, rediriger vers la page de connexion
    router.navigate(['/sign-in']);
  }

  return false; // Empêche la navigation vers la route racine
};
