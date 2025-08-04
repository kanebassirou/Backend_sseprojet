import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { 
    path: 'dashboard', 
    loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  { 
    path: 'projets', 
    loadComponent: () => import('./projets/projet-list.component').then(m => m.ProjetListComponent)
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
    path: 'projets/nouveau', 
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
    path: 'indicateurs', 
    loadComponent: () => import('./indicateurs/indicateur-list.component').then(m => m.IndicateurListComponent)
  },
  { path: '**', redirectTo: '/dashboard' }
];
