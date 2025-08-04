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
import { TacheService } from '../services/tache.service';
import { Tache, StatutTache } from '../models/tache.model';

@Component({
  selector: 'app-tache-list',
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
  templateUrl: './tache-list.component.html',
  styleUrls: ['./tache-list.component.css']
})
export class TacheListComponent implements OnInit {
  taches: Tache[] = [];
  filteredTaches: Tache[] = [];
  loading = true;
  error: string | null = null;

  // Filtres
  selectedStatut: StatutTache | 'TOUS' = 'TOUS';
  searchIntitule = '';
  showOnlyRetard = false;

  displayedColumns: string[] = [
    'intitule', 
    'statut', 
    'dateDebut', 
    'dateFin', 
    'projet', 
    'retard',
    'actions'
  ];

  statutOptions: { value: StatutTache | 'TOUS', label: string }[] = [
    { value: 'TOUS', label: 'Tous les statuts' },
    { value: 'A_FAIRE', label: 'À faire' },
    { value: 'EN_COURS', label: 'En cours' },
    { value: 'TERMINEE', label: 'Terminée' },
    { value: 'EN_ATTENTE', label: 'En attente' }
  ];

  constructor(private tacheService: TacheService) { }

  ngOnInit(): void {
    this.loadTaches();
  }

  loadTaches(): void {
    this.loading = true;
    this.error = null;

    this.tacheService.getAllTaches().subscribe({
      next: (data) => {
        this.taches = data;
        this.applyFilters();
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des tâches';
        this.loading = false;
        console.error('Erreur chargement tâches:', err);
      }
    });
  }

  applyFilters(): void {
    this.filteredTaches = this.taches.filter(tache => {
      const matchStatut = this.selectedStatut === 'TOUS' || tache.statut === this.selectedStatut;
      const matchIntitule = !this.searchIntitule || 
        tache.intitule.toLowerCase().includes(this.searchIntitule.toLowerCase());
      const matchRetard = !this.showOnlyRetard || tache.estEnRetard;
      
      return matchStatut && matchIntitule && matchRetard;
    });
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  updateStatut(tache: Tache, newStatut: StatutTache): void {
    this.tacheService.updateTacheStatut(tache.id!, newStatut).subscribe({
      next: () => {
        tache.statut = newStatut;
        this.applyFilters();
      },
      error: (err) => {
        console.error('Erreur mise à jour statut:', err);
        alert('Erreur lors de la mise à jour du statut');
      }
    });
  }

  deleteTache(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette tâche ?')) {
      this.tacheService.deleteTache(id).subscribe({
        next: () => {
          this.loadTaches();
        },
        error: (err) => {
          console.error('Erreur suppression tâche:', err);
          alert('Erreur lors de la suppression de la tâche');
        }
      });
    }
  }

  getStatutColor(statut: StatutTache): string {
    switch (statut) {
      case 'A_FAIRE': return '';
      case 'EN_COURS': return 'primary';
      case 'TERMINEE': return 'accent';
      case 'EN_ATTENTE': return 'warn';
      default: return '';
    }
  }

  getStatutLabel(statut: StatutTache): string {
    switch (statut) {
      case 'A_FAIRE': return 'À faire';
      case 'EN_COURS': return 'En cours';
      case 'TERMINEE': return 'Terminée';
      case 'EN_ATTENTE': return 'En attente';
      default: return statut;
    }
  }

  formatDate(date: string | Date): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }
}
