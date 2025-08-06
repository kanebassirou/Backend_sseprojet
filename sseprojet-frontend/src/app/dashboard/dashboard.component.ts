import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { RouterModule } from '@angular/router';
import { ProjetService, TacheService, RapportService, UserService } from '../services';
import { AuthService } from '../services/auth.service';
import { UserType, User } from '../models/user.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatGridListModule,
    MatProgressSpinnerModule,
    RouterModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  currentUser: User | null = null;
  userType: UserType | null = null;

  stats = {
    totalProjets: 0,
    projetsEnCours: 0,
    totalTaches: 0,
    tachesEnRetard: 0,
    totalRapports: 0
  };

  // Stats spécifiques par rôle
  adminStats = {
    totalUtilisateurs: 0,
    nouveauxUtilisateurs: 0
  };

  chefStats = {
    projetsAssignes: 0,
    tachesAFaire: 0
  };

  evaluateurStats = {
    evaluationsEnAttente: 0,
    rapportsARevoir: 0
  };

  loading = true;
  error: string | null = null;

  constructor(
    private projetService: ProjetService,
    private tacheService: TacheService,
    private rapportService: RapportService,
    private userService: UserService,
    private authService: AuthService
  ) {
    // Récupérer l'utilisateur connecté depuis le service d'authentification
    this.authService.currentUser$.subscribe((user: User | null) => {
      this.currentUser = user;
      this.userType = user?.type || null;
    });
  }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.loading = true;
    this.error = null;

    // Données simulées pour éviter les erreurs en attendant l'implémentation des services
    setTimeout(() => {
      // Simuler un délai de chargement
      try {
        // Stats générales simulées
        this.stats.totalProjets = 12;
        this.stats.projetsEnCours = 8;
        this.stats.totalTaches = 45;
        this.stats.tachesEnRetard = 3;
        this.stats.totalRapports = 25;

        // Charger des stats spécifiques selon le rôle
        this.loadRoleSpecificStats();

        this.loading = false;
      } catch (err) {
        this.error = 'Erreur lors du chargement des données du tableau de bord';
        this.loading = false;
        console.error('Erreur dashboard:', err);
      }
    }, 1000); // Simuler 1 seconde de chargement

    /* Version originale avec vrais services (à réactiver plus tard)
    forkJoin({
      projets: this.projetService.getAllProjets(),
      taches: this.tacheService.getAllTaches(),
      rapports: this.rapportService.getAllRapports()
    }).subscribe({
      next: (data) => {
        this.stats.totalProjets = data.projets.length;
        this.stats.projetsEnCours = data.projets.filter(p => p.statut === 'EN_COURS').length;
        this.stats.totalTaches = data.taches.length;
        this.stats.tachesEnRetard = data.taches.filter(t =>
          t.dateFin && new Date(t.dateFin) < new Date() && t.statut !== 'TERMINEE'
        ).length;
        this.stats.totalRapports = data.rapports.length;

        // Charger des stats spécifiques selon le rôle
        this.loadRoleSpecificStats();

        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des données du tableau de bord';
        this.loading = false;
        console.error('Erreur dashboard:', err);
      }
    });
    */
  }

  loadRoleSpecificStats(): void {
    // Données simulées pour éviter les erreurs avec les services
    switch(this.userType) {
      case 'ADMINISTRATEUR':
        // Stats simulées pour administrateur
        this.adminStats.totalUtilisateurs = 28;
        this.adminStats.nouveauxUtilisateurs = 4;
        break;

      case 'CHEF_DE_PROJET':
        // Stats simulées pour chef de projet
        this.chefStats.projetsAssignes = 6;
        this.chefStats.tachesAFaire = 15;
        break;

      case 'EVALUATEUR':
        // Stats simulées pour évaluateur
        this.evaluateurStats.evaluationsEnAttente = 3;
        this.evaluateurStats.rapportsARevoir = 8;
        break;

      case 'DECIDEUR':
        // Pas de stats spécifiques pour décideur
        break;
    }

    /* Version originale avec vrais services (à réactiver plus tard)
    switch(this.userType) {
      case 'ADMINISTRATEUR':
        this.userService.getAllUsers().subscribe(users => {
          this.adminStats.totalUtilisateurs = users.length;
          this.adminStats.nouveauxUtilisateurs = users.filter(u => {
            const created = new Date(u.dateCreation || new Date());
            const weekAgo = new Date();
            weekAgo.setDate(weekAgo.getDate() - 7);
            return created > weekAgo;
          }).length;
        });
        break;

      case 'CHEF_DE_PROJET':
        // Stats pour chef de projet (projets assignés)
        this.chefStats.projetsAssignes = this.stats.totalProjets; // Temporaire
        this.chefStats.tachesAFaire = this.stats.totalTaches; // Temporaire
        break;

      case 'EVALUATEUR':
        // Stats pour évaluateur
        this.evaluateurStats.evaluationsEnAttente = 5; // Temporaire
        this.evaluateurStats.rapportsARevoir = this.stats.totalRapports;
        break;
    }
    */
  }

  // Méthodes de permission pour l'affichage conditionnel
  canViewProjets(): boolean {
    return ['ADMINISTRATEUR', 'CHEF_DE_PROJET', 'DECIDEUR'].includes(this.userType || '');
  }

  canViewTaches(): boolean {
    return ['ADMINISTRATEUR', 'CHEF_DE_PROJET'].includes(this.userType || '');
  }

  getProjetTitle(): string {
    switch(this.userType) {
      case 'CHEF_DE_PROJET': return 'Mes Projets';
      case 'DECIDEUR': return 'Projets à Consulter';
      default: return 'Projets';
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
}
