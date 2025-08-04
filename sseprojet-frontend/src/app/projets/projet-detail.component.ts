import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-projet-detail',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule],
  template: `
    <div class="detail-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>Détails du Projet</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <p>Page de détail de projet en cours de développement...</p>
        </mat-card-content>
        <mat-card-actions>
          <button mat-button routerLink="/projets">
            <mat-icon>arrow_back</mat-icon>
            Retour
          </button>
        </mat-card-actions>
      </mat-card>
    </div>
  `,
  styles: [`
    .detail-container {
      padding: 20px;
      max-width: 1000px;
      margin: 0 auto;
    }
  `]
})
export class ProjetDetailComponent { }
