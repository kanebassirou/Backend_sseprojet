import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { IndicateurService } from '../services/indicateur.service';
import { Indicateur } from '../models/indicateur.model';

@Component({
  selector: 'app-indicateur-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatChipsModule
  ],
  templateUrl: './indicateur-list.component.html',
  styleUrls: ['./indicateur-list.component.css']
})
export class IndicateurListComponent implements OnInit {
  indicateurs: Indicateur[] = [];
  loading = true;
  error: string | null = null;

  displayedColumns: string[] = ['nom', 'type', 'objectif', 'valeurCible', 'valeurReelle', 'projet', 'actions'];

  constructor(private indicateurService: IndicateurService) { }

  ngOnInit(): void {
    this.loadIndicateurs();
  }

  loadIndicateurs(): void {
    this.loading = true;
    this.error = null;

    this.indicateurService.getAllIndicateurs().subscribe({
      next: (data) => {
        this.indicateurs = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des indicateurs';
        this.loading = false;
        console.error('Erreur chargement indicateurs:', err);
      }
    });
  }
}
