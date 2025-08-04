import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { RapportService } from '../services/rapport.service';
import { RapportDTO } from '../models/rapport.model';

@Component({
  selector: 'app-rapport-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatChipsModule
  ],
  templateUrl: './rapport-list.component.html',
  styleUrls: ['./rapport-list.component.css']
})
export class RapportListComponent implements OnInit {
  rapports: RapportDTO[] = [];
  loading = true;
  error: string | null = null;

  displayedColumns: string[] = ['titre', 'auteur', 'dateGeneration', 'typeRapport', 'projet', 'actions'];

  constructor(private rapportService: RapportService) { }

  ngOnInit(): void {
    this.loadRapports();
  }

  loadRapports(): void {
    this.loading = true;
    this.error = null;

    this.rapportService.getAllRapports().subscribe({
      next: (data) => {
        this.rapports = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des rapports';
        this.loading = false;
        console.error('Erreur chargement rapports:', err);
      }
    });
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }
}
