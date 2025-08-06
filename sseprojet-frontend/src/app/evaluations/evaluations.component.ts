import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { RouterModule } from '@angular/router';

interface Evaluation {
  id: number;
  projetId: number;
  projetNom: string;
  dateEvaluation?: Date;
  statut: 'EN_ATTENTE' | 'EN_COURS' | 'TERMINEE';
  note?: number;
  commentaire?: string;
}

@Component({
  selector: 'app-evaluations',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatChipsModule,
    RouterModule
  ],
  template: `
    <div class="evaluations-container">
      <div class="header">
        <h1>Mes Évaluations</h1>
        <p>Projets à évaluer et évaluations en cours</p>
      </div>

      <div class="stats-cards">
        <mat-card class="stat-card">
          <mat-card-header>
            <mat-icon mat-card-avatar class="pending">pending</mat-icon>
            <mat-card-title>En Attente</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <div class="stat-number">{{ getEvaluationsByStatus('EN_ATTENTE').length }}</div>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card">
          <mat-card-header>
            <mat-icon mat-card-avatar class="in-progress">rate_review</mat-icon>
            <mat-card-title>En Cours</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <div class="stat-number">{{ getEvaluationsByStatus('EN_COURS').length }}</div>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card">
          <mat-card-header>
            <mat-icon mat-card-avatar class="completed">check_circle</mat-icon>
            <mat-card-title>Terminées</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <div class="stat-number">{{ getEvaluationsByStatus('TERMINEE').length }}</div>
          </mat-card-content>
        </mat-card>
      </div>

      <mat-card class="evaluations-table-card">
        <mat-card-header>
          <mat-card-title>Liste des Évaluations</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <table mat-table [dataSource]="evaluations" class="evaluations-table">

            <ng-container matColumnDef="projet">
              <th mat-header-cell *matHeaderCellDef>Projet</th>
              <td mat-cell *matCellDef="let evaluation">{{ evaluation.projetNom }}</td>
            </ng-container>

            <ng-container matColumnDef="statut">
              <th mat-header-cell *matHeaderCellDef>Statut</th>
              <td mat-cell *matCellDef="let evaluation">
                <mat-chip [class]="getStatusClass(evaluation.statut)">
                  {{ getStatusLabel(evaluation.statut) }}
                </mat-chip>
              </td>
            </ng-container>

            <ng-container matColumnDef="dateEvaluation">
              <th mat-header-cell *matHeaderCellDef>Date d'Évaluation</th>
              <td mat-cell *matCellDef="let evaluation">
                {{ evaluation.dateEvaluation ? (evaluation.dateEvaluation | date:'short') : '-' }}
              </td>
            </ng-container>

            <ng-container matColumnDef="note">
              <th mat-header-cell *matHeaderCellDef>Note</th>
              <td mat-cell *matCellDef="let evaluation">
                <span *ngIf="evaluation.note" class="note">{{ evaluation.note }}/20</span>
                <span *ngIf="!evaluation.note" class="no-note">-</span>
              </td>
            </ng-container>

            <ng-container matColumnDef="actions">
              <th mat-header-cell *matHeaderCellDef>Actions</th>
              <td mat-cell *matCellDef="let evaluation">
                <button
                  mat-button
                  color="primary"
                  (click)="startEvaluation(evaluation)"
                  *ngIf="evaluation.statut === 'EN_ATTENTE'">
                  <mat-icon>rate_review</mat-icon>
                  Évaluer
                </button>
                <button
                  mat-button
                  color="accent"
                  (click)="continueEvaluation(evaluation)"
                  *ngIf="evaluation.statut === 'EN_COURS'">
                  <mat-icon>edit</mat-icon>
                  Continuer
                </button>
                <button
                  mat-stroked-button
                  (click)="viewEvaluation(evaluation)"
                  *ngIf="evaluation.statut === 'TERMINEE'">
                  <mat-icon>visibility</mat-icon>
                  Voir
                </button>
              </td>
            </ng-container>

            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
          </table>

          <div *ngIf="evaluations.length === 0" class="no-evaluations">
            <mat-icon>assignment</mat-icon>
            <p>Aucune évaluation assignée pour le moment</p>
          </div>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .evaluations-container {
      padding: 1rem;
      max-width: 1200px;
      margin: 0 auto;
    }

    .header {
      margin-bottom: 2rem;
    }

    .header h1 {
      font-size: 2rem;
      font-weight: 300;
      color: #333;
      margin: 0 0 0.5rem 0;
    }

    .header p {
      color: #666;
      margin: 0;
    }

    .stats-cards {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 1rem;
      margin-bottom: 2rem;
    }

    .stat-card mat-card-header mat-icon {
      width: 2.5rem;
      height: 2.5rem;
      font-size: 1.5rem;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
    }

    .stat-card mat-icon.pending {
      background-color: #FF9800;
    }

    .stat-card mat-icon.in-progress {
      background-color: #2196F3;
    }

    .stat-card mat-icon.completed {
      background-color: #4CAF50;
    }

    .stat-number {
      font-size: 2rem;
      font-weight: 300;
      color: #333;
    }

    .evaluations-table-card {
      margin-bottom: 2rem;
    }

    .evaluations-table {
      width: 100%;
    }

    .mat-chip.status-pending {
      background-color: #FFF3E0;
      color: #F57C00;
    }

    .mat-chip.status-in-progress {
      background-color: #E3F2FD;
      color: #1976D2;
    }

    .mat-chip.status-completed {
      background-color: #E8F5E8;
      color: #388E3C;
    }

    .note {
      font-weight: 500;
      color: #4CAF50;
    }

    .no-note {
      color: #666;
      font-style: italic;
    }

    .no-evaluations {
      text-align: center;
      padding: 3rem;
      color: #666;
    }

    .no-evaluations mat-icon {
      font-size: 3rem;
      height: 3rem;
      width: 3rem;
      margin-bottom: 1rem;
    }

    @media (max-width: 768px) {
      .stats-cards {
        grid-template-columns: 1fr;
      }
    }
  `]
})
export class EvaluationsComponent implements OnInit {

  evaluations: Evaluation[] = [
    {
      id: 1,
      projetId: 1,
      projetNom: "Système de Gestion de Projets",
      statut: 'EN_ATTENTE'
    },
    {
      id: 2,
      projetId: 2,
      projetNom: "Application Mobile",
      statut: 'EN_COURS',
      dateEvaluation: new Date('2024-01-15')
    },
    {
      id: 3,
      projetId: 3,
      projetNom: "Site Web E-commerce",
      statut: 'TERMINEE',
      dateEvaluation: new Date('2024-01-10'),
      note: 16,
      commentaire: "Excellent travail sur l'interface utilisateur"
    }
  ];

  displayedColumns: string[] = ['projet', 'statut', 'dateEvaluation', 'note', 'actions'];

  constructor() { }

  ngOnInit(): void {
  }

  getEvaluationsByStatus(statut: string): Evaluation[] {
    return this.evaluations.filter(e => e.statut === statut);
  }

  getStatusLabel(statut: string): string {
    const labels = {
      'EN_ATTENTE': 'En Attente',
      'EN_COURS': 'En Cours',
      'TERMINEE': 'Terminée'
    };
    return labels[statut as keyof typeof labels] || statut;
  }

  getStatusClass(statut: string): string {
    const classes = {
      'EN_ATTENTE': 'status-pending',
      'EN_COURS': 'status-in-progress',
      'TERMINEE': 'status-completed'
    };
    return classes[statut as keyof typeof classes] || '';
  }

  startEvaluation(evaluation: Evaluation): void {
    // TODO: Naviguer vers le formulaire d'évaluation
    console.log('Démarrer évaluation:', evaluation);
  }

  continueEvaluation(evaluation: Evaluation): void {
    // TODO: Naviguer vers le formulaire d'évaluation en mode édition
    console.log('Continuer évaluation:', evaluation);
  }

  viewEvaluation(evaluation: Evaluation): void {
    // TODO: Naviguer vers la vue détaillée de l'évaluation
    console.log('Voir évaluation:', evaluation);
  }
}
