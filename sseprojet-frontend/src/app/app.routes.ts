import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { noAuthGuard } from './guards/no-auth.guard';
import { redirectGuard } from './guards/redirect.guard';

export const routes: Routes = [
  // Routes d'authentification directes pour compatibilité
  {
    path: 'sign-in',
    loadComponent: () => import('./authentication/sign-in/sign-in.component').then(m => m.SignInComponent),
    canActivate: [noAuthGuard]
  },
  {
    path: 'sign-up',
    loadComponent: () => import('./authentication/sign-up/sign-up.component').then(m => m.SignUpComponent),
    canActivate: [noAuthGuard]
  },

  // Routes d'authentification (avec noAuthGuard pour éviter l'accès si déjà connecté)
  {
    path: 'authentication',
    canActivate: [noAuthGuard],
    children: [
      {
        path: 'sign-in',
        loadComponent: () => import('./authentication/sign-in/sign-in.component').then(m => m.SignInComponent)
      },
      {
        path: 'sign-up',
        loadComponent: () => import('./authentication/sign-up/sign-up.component').then(m => m.SignUpComponent)
      },
      {
        path: '',
        redirectTo: 'sign-in',
        pathMatch: 'full'
      }
    ]
  },

  // Routes protégées avec layout (avec guard)
  {
    path: 'app',
    loadComponent: () => import('./layout/layout.component').then(m => m.LayoutComponent),
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent)
      },

      {
        path: 'projets',
        loadComponent: () => import('./projets/projet-list.component').then(m => m.ProjetListComponent)
      },
      {
        path: 'projets/nouveau',
        loadComponent: () => import('./projets/projet-form.component').then(m => m.ProjetFormComponent)
      },
      {
        path: 'projets/:id',
        loadComponent: () => import('./projets/projet-detail.component').then(m => m.ProjetDetailComponent)
      },
      {
        path: 'projets/:id/edit',
        loadComponent: () => import('./projets/projet-form.component').then(m => m.ProjetFormComponent)
      },
      {
        path: 'taches',
        loadComponent: () => import('./taches/tache-list.component').then(m => m.TacheListComponent)
      },
      {
        path: 'taches/nouveau',
        loadComponent: () => import('./taches/tache-form.component').then(m => m.TacheFormComponent)
      },
      {
        path: 'rapports',
        loadComponent: () => import('./rapports/rapport-list.component').then(m => m.RapportListComponent)
      },
      {
        path: 'rapports/nouveau',
        loadComponent: () => import('./rapports/rapport-form.component').then(m => m.RapportFormComponent)
      },
      {
        path: 'users',
        loadComponent: () => import('./users/user-list.component').then(m => m.UserListComponent)
      },
      {
        path: 'evaluateurs',
        loadComponent: () => import('./evaluateurs/evaluateur-list.component').then(m => m.EvaluateurListComponent)
      },
      {
        path: 'evaluations',
        loadComponent: () => import('./evaluations/evaluations.component').then(m => m.EvaluationsComponent)
      },
      {
        path: 'indicateurs',
        loadComponent: () => import('./indicateurs/indicateur-list.component').then(m => m.IndicateurListComponent)
      }
    ]
  },

  // Route racine avec redirection intelligente
  { path: '', canActivate: [redirectGuard], children: [] },

  // Route par défaut - redirection vers connexion
  { path: '**', redirectTo: '/sign-in' }
];
