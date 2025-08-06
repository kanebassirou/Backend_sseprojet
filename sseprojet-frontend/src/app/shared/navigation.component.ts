import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router'; // <-- AJOUTEZ cet import
import { RouterModule, RouterOutlet } from '@angular/router';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable, Subscription } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

import { AuthService } from '../services/auth.service';
import { User, UserType } from '../models/user.model';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    RouterModule,
    RouterOutlet
  ],
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit, OnDestroy {

  isHandset$: Observable<boolean>;
  currentUser: User | null = null;
  menuItems: any[] = [];
  private userSubscription: Subscription = new Subscription();

  // Définition des menus par rôle
  private menusByRole = {
    'ADMINISTRATEUR': [
      { path: '/app/dashboard', icon: 'dashboard', label: 'Tableau de Bord' },
      { path: '/app/users', icon: 'people', label: 'Gestion Utilisateurs' },
      { path: '/app/projets', icon: 'folder', label: 'Projets' },
      { path: '/app/taches', icon: 'task', label: 'Tâches' },
      { path: '/app/indicateurs', icon: 'analytics', label: 'Indicateurs' },
      { path: '/app/rapports', icon: 'description', label: 'Rapports' },
      { path: '/app/evaluateurs', icon: 'assignment_ind', label: 'Évaluateurs' }
    ],
    'CHEF_DE_PROJET': [
      { path: '/app/dashboard', icon: 'dashboard', label: 'Tableau de Bord' },
      { path: '/app/projets', icon: 'folder', label: 'Projets Assignés' },
      { path: '/app/taches', icon: 'task', label: 'Tâches' },
      { path: '/app/rapports', icon: 'description', label: 'Rapports' }
    ],
    'DECIDEUR': [
      { path: '/app/dashboard', icon: 'dashboard', label: 'Tableau de Bord' },
      { path: '/app/projets', icon: 'folder', label: 'Consultation Projets' },
      { path: '/app/rapports', icon: 'description', label: 'Rapports' },
      { path: '/app/indicateurs', icon: 'analytics', label: 'Indicateurs' }
    ],
    'EVALUATEUR': [
      { path: '/app/dashboard', icon: 'dashboard', label: 'Tableau de Bord' },
      { path: '/app/evaluations', icon: 'assessment', label: 'Évaluations' },
      { path: '/app/rapports', icon: 'description', label: 'Rapports' },
      { path: '/app/indicateurs', icon: 'analytics', label: 'Indicateurs' }
    ]
  };

  constructor(
    private breakpointObserver: BreakpointObserver,
    private authService: AuthService
  ) {
    this.isHandset$ = this.breakpointObserver.observe(Breakpoints.Handset)
      .pipe(
        map(result => result.matches),
        shareReplay()
      );
  }

  ngOnInit(): void {
    // S'abonner aux changements d'utilisateur depuis le service d'authentification
    this.userSubscription = this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
      this.updateMenuItems();
    });
  }

  ngOnDestroy(): void {
    // Nettoyer les abonnements pour éviter les fuites mémoire
    this.userSubscription.unsubscribe();
  }

  private updateMenuItems(): void {
    if (this.currentUser && this.currentUser.type) {
      this.menuItems = this.menusByRole[this.currentUser.type as UserType] || [];
    } else {
      this.menuItems = [];
    }
  }

  getUserRoleLabel(type: UserType): string {
    const roleLabels = {
      'ADMINISTRATEUR': 'Administrateur',
      'CHEF_DE_PROJET': 'Chef de Projet',
      'DECIDEUR': 'Décideur',
      'EVALUATEUR': 'Évaluateur'
    };
    return roleLabels[type] || type;
  }

  logout(): void {
    // Confirmer la déconnexion
    if (confirm('Êtes-vous sûr de vouloir vous déconnecter ?')) {
      // Appeler la méthode de déconnexion du service
      this.authService.logout();
    }
  }
}
