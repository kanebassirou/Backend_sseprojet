import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true;
  } else {
    // Si l'utilisateur n'est pas connecté, rediriger vers la page de connexion
    router.navigate(['/authentication/sign-in']);
    return false;
  }
};
