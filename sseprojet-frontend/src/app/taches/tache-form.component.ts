import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-tache-form',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule],
  template: `
    <div class="form-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>Nouvelle Tâche</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <p>Formulaire de création de tâche en cours de développement...</p>
        </mat-card-content>
        <mat-card-actions>
          <button mat-button routerLink="/taches">
            <mat-icon>arrow_back</mat-icon>
            Retour
          </button>
        </mat-card-actions>
      </mat-card>
    </div>
  `,
  styles: [`
    .form-container {
      padding: 20px;
      max-width: 800px;
      margin: 0 auto;
    }
  `]
})
export class TacheFormComponent { }
