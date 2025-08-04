import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { RouterModule } from '@angular/router';
import { ProjetService, TacheService, RapportService } from '../services';
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
    RouterModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  stats = {
    totalProjets: 0,
    projetsEnCours: 0,
    totalTaches: 0,
    tachesEnRetard: 0,
    totalRapports: 0
  };

  loading = true;
  error: string | null = null;

  constructor(
    private projetService: ProjetService,
    private tacheService: TacheService,
    private rapportService: RapportService
  ) { }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.loading = true;
    this.error = null;

    forkJoin({
      projets: this.projetService.getAllProjets(),
      totalTaches: this.tacheService.getTotalCount(),
      tachesEnRetard: this.tacheService.getTachesEnRetard(),
      rapports: this.rapportService.getAllRapports()
    }).subscribe({
      next: (data) => {
        this.stats.totalProjets = data.projets.length;
        this.stats.projetsEnCours = data.projets.filter(p => p.statut === 'EN_COURS').length;
        this.stats.totalTaches = data.totalTaches;
        this.stats.tachesEnRetard = data.tachesEnRetard.length;
        this.stats.totalRapports = data.rapports.length;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des données du tableau de bord';
        this.loading = false;
        console.error('Erreur dashboard:', err);
      }
    });
  }
}
