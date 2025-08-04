import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { EvaluateurService } from '../services/evaluateur.service';
import { ProjetService } from '../services/projet.service';
import { Evaluateur } from '../models/user.model';
import { ProjetResponse } from '../models/projet.model';

@Component({
  selector: 'app-evaluateur-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    FormsModule
  ],
  template: `
    <div class="evaluateur-container">
      <div class="header">
        <h1>Gestion des Évaluateurs</h1>
        <button mat-raised-button color="primary" (click)="openCreateDialog()">
          <mat-icon>add</mat-icon>
          Nouvel Évaluateur
        </button>
      </div>

      <!-- Filtres -->
      <mat-card class="filters-card">
        <mat-card-content>
          <div class="filters">
            <mat-form-field>
              <mat-label>Rechercher par nom</mat-label>
              <input matInput 
                     [(ngModel)]="searchNom" 
                     (ngModelChange)="onSearch()"
                     placeholder="Nom de l'évaluateur">
              <mat-icon matSuffix>search</mat-icon>
            </mat-form-field>
          </div>
        </mat-card-content>
      </mat-card>

      <!-- Loading -->
      <div *ngIf="loading" class="loading">
        <p>Chargement des évaluateurs...</p>
      </div>

      <!-- Error -->
      <div *ngIf="error" class="error">
        <p>{{ error }}</p>
        <button mat-raised-button color="primary" (click)="loadEvaluateurs()">
          <mat-icon>refresh</mat-icon>
          Réessayer
        </button>
      </div>

      <!-- Table -->
      <mat-card *ngIf="!loading && !error">
        <mat-card-content>
          <div class="table-container">
            <table mat-table [dataSource]="evaluateurs" class="evaluateurs-table">

              <!-- Colonne Nom -->
              <ng-container matColumnDef="nom">
                <th mat-header-cell *matHeaderCellDef>Nom</th>
                <td mat-cell *matCellDef="let evaluateur">{{ evaluateur.nom }}</td>
              </ng-container>

              <!-- Colonne Email -->
              <ng-container matColumnDef="email">
                <th mat-header-cell *matHeaderCellDef>Email</th>
                <td mat-cell *matCellDef="let evaluateur">{{ evaluateur.email }}</td>
              </ng-container>

              <!-- Colonne Projet Assigné -->
              <ng-container matColumnDef="projetAssigne">
                <th mat-header-cell *matHeaderCellDef>Projet Assigné</th>
                <td mat-cell *matCellDef="let evaluateur">
                  <span *ngIf="evaluateur.projet; else noProjet">
                    {{ evaluateur.projet.titre }}
                  </span>
                  <ng-template #noProjet>
                    <span class="no-projet">Aucun projet</span>
                  </ng-template>
                </td>
              </ng-container>

              <!-- Colonne Actions -->
              <ng-container matColumnDef="actions">
                <th mat-header-cell *matHeaderCellDef>Actions</th>
                <td mat-cell *matCellDef="let evaluateur">
                  <div class="actions">
                    <button mat-icon-button 
                            (click)="assignerProjet(evaluateur)"
                            matTooltip="Assigner un projet"
                            *ngIf="!evaluateur.projet">
                      <mat-icon>assignment</mat-icon>
                    </button>
                    <button mat-icon-button 
                            (click)="retirerProjet(evaluateur)"
                            matTooltip="Retirer du projet"
                            color="warn"
                            *ngIf="evaluateur.projet">
                      <mat-icon>assignment_return</mat-icon>
                    </button>
                    <button mat-icon-button 
                            (click)="deleteEvaluateur(evaluateur.id!)"
                            matTooltip="Supprimer"
                            color="warn">
                      <mat-icon>delete</mat-icon>
                    </button>
                  </div>
                </td>
              </ng-container>

              <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
              <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
            </table>

            <div *ngIf="evaluateurs.length === 0" class="no-data">
              <p>Aucun évaluateur trouvé</p>
            </div>
          </div>
        </mat-card-content>
      </mat-card>

      <!-- Dialogue d'assignation de projet -->
      <div *ngIf="showAssignDialog" class="dialog-overlay" (click)="closeAssignDialog()">
        <div class="dialog-content" (click)="$event.stopPropagation()">
          <h2>Assigner un projet</h2>
          <mat-form-field>
            <mat-label>Sélectionner un projet</mat-label>
            <mat-select [(ngModel)]="selectedProjetId">
              <mat-option *ngFor="let projet of projets" [value]="projet.id">
                {{ projet.titre }}
              </mat-option>
            </mat-select>
          </mat-form-field>
          <div class="dialog-actions">
            <button mat-button (click)="closeAssignDialog()">Annuler</button>
            <button mat-raised-button color="primary" 
                    (click)="confirmAssignProjet()"
                    [disabled]="!selectedProjetId">
              Assigner
            </button>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .evaluateur-container {
      padding: 20px;
      max-width: 1200px;
      margin: 0 auto;
    }

    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px;
    }

    .header h1 {
      color: #333;
      font-weight: 300;
      margin: 0;
    }

    .filters-card {
      margin-bottom: 20px;
    }

    .filters {
      display: grid;
      grid-template-columns: 1fr;
      gap: 20px;
    }

    .loading, .error {
      text-align: center;
      padding: 40px;
    }

    .error {
      color: #f44336;
    }

    .table-container {
      overflow-x: auto;
    }

    .evaluateurs-table {
      width: 100%;
    }

    .no-projet {
      color: #666;
      font-style: italic;
    }

    .actions {
      display: flex;
      gap: 8px;
    }

    .no-data {
      text-align: center;
      padding: 40px;
      color: #666;
    }

    .dialog-overlay {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0.5);
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 1000;
    }

    .dialog-content {
      background: white;
      padding: 24px;
      border-radius: 8px;
      max-width: 400px;
      width: 90%;
    }

    .dialog-content h2 {
      margin-top: 0;
    }

    .dialog-actions {
      display: flex;
      gap: 16px;
      justify-content: flex-end;
      margin-top: 20px;
    }

    @media (max-width: 768px) {
      .header {
        flex-direction: column;
        gap: 16px;
        align-items: stretch;
      }
      
      .evaluateurs-table {
        font-size: 14px;
      }
      
      .actions {
        flex-direction: column;
      }
    }
  `]
})
export class EvaluateurListComponent implements OnInit {
  evaluateurs: Evaluateur[] = [];
  projets: ProjetResponse[] = [];
  loading = true;
  error: string | null = null;
  searchNom = '';

  // Dialog pour assigner un projet
  showAssignDialog = false;
  selectedEvaluateur?: Evaluateur;
  selectedProjetId?: number;

  displayedColumns: string[] = ['nom', 'email', 'projetAssigne', 'actions'];

  constructor(
    private evaluateurService: EvaluateurService,
    private projetService: ProjetService
  ) { }

  ngOnInit(): void {
    this.loadEvaluateurs();
    this.loadProjets();
  }

  loadEvaluateurs(): void {
    this.loading = true;
    this.error = null;

    this.evaluateurService.getAllEvaluateurs().subscribe({
      next: (data) => {
        this.evaluateurs = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des évaluateurs';
        this.loading = false;
        console.error('Erreur chargement évaluateurs:', err);
      }
    });
  }

  loadProjets(): void {
    this.projetService.getAllProjets().subscribe({
      next: (data) => {
        this.projets = data;
      },
      error: (err) => {
        console.error('Erreur chargement projets:', err);
      }
    });
  }

  onSearch(): void {
    if (this.searchNom.trim()) {
      this.evaluateurService.searchEvaluateursByName(this.searchNom).subscribe({
        next: (data) => {
          this.evaluateurs = data;
        },
        error: (err) => {
          console.error('Erreur recherche:', err);
        }
      });
    } else {
      this.loadEvaluateurs();
    }
  }

  openCreateDialog(): void {
    // TODO: Implémenter la création d'évaluateur
    alert('Fonctionnalité de création en cours de développement');
  }

  assignerProjet(evaluateur: Evaluateur): void {
    this.selectedEvaluateur = evaluateur;
    this.selectedProjetId = undefined;
    this.showAssignDialog = true;
  }

  closeAssignDialog(): void {
    this.showAssignDialog = false;
    this.selectedEvaluateur = undefined;
    this.selectedProjetId = undefined;
  }

  confirmAssignProjet(): void {
    if (this.selectedEvaluateur && this.selectedProjetId) {
      this.evaluateurService.assignerProjet(this.selectedEvaluateur.id!, this.selectedProjetId).subscribe({
        next: () => {
          this.loadEvaluateurs();
          this.closeAssignDialog();
        },
        error: (err) => {
          console.error('Erreur assignation projet:', err);
          alert('Erreur lors de l\'assignation du projet');
        }
      });
    }
  }

  retirerProjet(evaluateur: Evaluateur): void {
    if (confirm('Êtes-vous sûr de vouloir retirer cet évaluateur du projet ?')) {
      this.evaluateurService.retirerProjet(evaluateur.id!).subscribe({
        next: () => {
          this.loadEvaluateurs();
        },
        error: (err) => {
          console.error('Erreur retirer projet:', err);
          alert('Erreur lors du retrait du projet');
        }
      });
    }
  }

  deleteEvaluateur(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet évaluateur ?')) {
      this.evaluateurService.deleteEvaluateur(id).subscribe({
        next: () => {
          this.loadEvaluateurs();
        },
        error: (err) => {
          console.error('Erreur suppression évaluateur:', err);
          alert('Erreur lors de la suppression de l\'évaluateur');
        }
      });
    }
  }
}
