import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { UserService } from '../services/user.service';
import { User, UserType } from '../models/user.model';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatChipsModule
  ],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading = true;
  error: string | null = null;

  displayedColumns: string[] = ['nom', 'email', 'type', 'dateCreation', 'actions'];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    this.error = null;

    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des utilisateurs';
        this.loading = false;
        console.error('Erreur chargement utilisateurs:', err);
      }
    });
  }

  deleteUser(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.loadUsers();
        },
        error: (err) => {
          console.error('Erreur suppression utilisateur:', err);
          alert('Erreur lors de la suppression de l\'utilisateur');
        }
      });
    }
  }

  getTypeColor(type: UserType): string {
    switch (type) {
      case 'ADMINISTRATEUR': return 'warn';
      case 'CHEF_DE_PROJET': return 'primary';
      case 'DECIDEUR': return 'accent';
      case 'EVALUATEUR': return '';
      default: return '';
    }
  }

  getTypeLabel(type: UserType): string {
    switch (type) {
      case 'ADMINISTRATEUR': return 'Administrateur';
      case 'CHEF_DE_PROJET': return 'Chef de Projet';
      case 'DECIDEUR': return 'Décideur';
      case 'EVALUATEUR': return 'Évaluateur';
      default: return type;
    }
  }

  formatDate(date: Date | undefined): string {
    return date ? new Date(date).toLocaleDateString('fr-FR') : 'N/A';
  }
}
