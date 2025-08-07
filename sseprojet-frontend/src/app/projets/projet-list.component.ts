import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProjetService } from '../services/projet.service';
import { ProjetResponse, StatutProjet } from '../models/projet.model';

@Component({
  selector: 'app-projet-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatChipsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    RouterModule,
    FormsModule
  ],
  templateUrl: './projet-list.component.html',
  styleUrls: ['./projet-list.component.css']
})
export class ProjetListComponent implements OnInit {
  projets: ProjetResponse[] = [];
  filteredProjets: ProjetResponse[] = [];
  loading = true;
  error: string | null = null;

  // Filtres
  selectedStatut: StatutProjet | 'TOUS' = 'TOUS';
  searchTitre = '';

  displayedColumns: string[] = [
    'titre',
    'statut',
    'chefDeProjet',
    'dateDebut',
    'dateFin',
    'budget',
    'indicateurs',
    'taches',
    'actions'
  ];

  statutOptions: { value: StatutProjet | 'TOUS', label: string }[] = [
    { value: 'TOUS', label: 'Tous les statuts' },
    { value: 'EN_COURS', label: 'En cours' },
    { value: 'TERMINE', label: 'Terminé' },
    { value: 'SUSPENDU', label: 'Suspendu' },
    { value: 'PLANIFIE', label: 'Planifié' }
  ];

  constructor(private projetService: ProjetService) { }

  ngOnInit(): void {
    this.loadProjets();
  }

  loadProjets(): void {
    this.loading = true;
    this.error = null;

    this.projetService.getAllProjets().subscribe({
      next: (data) => {
        this.projets = data;
        this.applyFilters();
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des projets';
        this.loading = false;
        console.error('Erreur chargement projets:', err);
      }
    });
  }

  applyFilters(): void {
    this.filteredProjets = this.projets.filter(projet => {
      const matchStatut = this.selectedStatut === 'TOUS' || projet.statut === this.selectedStatut;
      const matchTitre = !this.searchTitre ||
        projet.titre.toLowerCase().includes(this.searchTitre.toLowerCase()) ||
        projet.description.toLowerCase().includes(this.searchTitre.toLowerCase());

      return matchStatut && matchTitre;
    });
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  deleteProjet(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce projet ?')) {
      this.projetService.deleteProjet(id).subscribe({
        next: () => {
          this.loadProjets();
        },
        error: (err) => {
          console.error('Erreur suppression projet:', err);
          alert('Erreur lors de la suppression du projet');
        }
      });
    }
  }

  getStatutColor(statut: StatutProjet): string {
    switch (statut) {
      case 'EN_COURS': return 'primary';
      case 'TERMINE': return 'accent';
      case 'SUSPENDU': return 'warn';
      case 'PLANIFIE': return '';
      default: return '';
    }
  }

  getStatutLabel(statut: StatutProjet): string {
    switch (statut) {
      case 'EN_COURS': return 'En cours';
      case 'TERMINE': return 'Terminé';
      case 'SUSPENDU': return 'Suspendu';
      case 'PLANIFIE': return 'Planifié';
      default: return statut;
    }
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }

  formatBudget(budget: number): string {
    return new Intl.NumberFormat('fr-FR', {
      style: 'currency',
      currency: 'CFA'
    }).format(budget);
  }
}
