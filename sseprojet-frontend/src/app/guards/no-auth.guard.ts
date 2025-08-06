import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const noAuthGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    // Si l'utilisateur est déjà connecté, rediriger vers le dashboard
    router.navigate(['/dashboard']);
    return false;
  } else {
    // Si l'utilisateur n'est pas connecté, autoriser l'accès à la page d'authentification
    return true;
  }
};
