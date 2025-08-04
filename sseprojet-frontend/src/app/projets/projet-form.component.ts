import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ProjetService } from '../services/projet.service';
import { UserService } from '../services/user.service';
import { CreateProjetRequest, Projet, StatutProjet } from '../models/projet.model';
import { User } from '../models/user.model';

@Component({
  selector: 'app-projet-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  templateUrl: './projet-form.component.html',
  styleUrls: ['./projet-form.component.css']
})
export class ProjetFormComponent implements OnInit {
  projetForm: FormGroup;
  isEditMode = false;
  projetId: number | null = null;
  loading = false;
  error: string | null = null;
  chefsDeProjet: User[] = [];

  statutOptions: { value: StatutProjet, label: string }[] = [
    { value: 'PLANIFIE', label: 'Planifié' },
    { value: 'EN_COURS', label: 'En cours' },
    { value: 'TERMINE', label: 'Terminé' },
    { value: 'SUSPENDU', label: 'Suspendu' }
  ];

  constructor(
    private fb: FormBuilder,
    private projetService: ProjetService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.projetForm = this.fb.group({
      titre: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      dateDebut: ['', Validators.required],
      dateFin: ['', Validators.required],
      budget: ['', [Validators.required, Validators.min(0)]],
      statut: ['EN_COURS', Validators.required],
      chefDeProjetId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadChefsDeProjet();
    
    const id = this.route.snapshot.paramMap.get('id');
    if (id && id !== 'nouveau') {
      this.isEditMode = true;
      this.projetId = +id;
      this.loadProjet();
    }
  }

  loadChefsDeProjet(): void {
    this.userService.getUsersByType('CHEF_DE_PROJET').subscribe({
      next: (chefs) => {
        this.chefsDeProjet = chefs;
      },
      error: (err) => {
        console.error('Erreur chargement chefs de projet:', err);
      }
    });
  }

  loadProjet(): void {
    if (this.projetId) {
      this.loading = true;
      this.projetService.getProjetById(this.projetId).subscribe({
        next: (projet) => {
          this.projetForm.patchValue({
            titre: projet.titre,
            description: projet.description,
            dateDebut: new Date(projet.dateDebut),
            dateFin: new Date(projet.dateFin),
            budget: projet.budget,
            statut: projet.statut,
            chefDeProjetId: projet.chefDeProjetId
          });
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Erreur lors du chargement du projet';
          this.loading = false;
          console.error('Erreur chargement projet:', err);
        }
      });
    }
  }

  onSubmit(): void {
    if (this.projetForm.valid) {
      this.loading = true;
      this.error = null;

      const formValue = this.projetForm.value;
      const projetData: CreateProjetRequest = {
        titre: formValue.titre,
        description: formValue.description,
        dateDebut: this.formatDate(formValue.dateDebut),
        dateFin: this.formatDate(formValue.dateFin),
        budget: +formValue.budget,
        statut: formValue.statut,
        chefDeProjetId: +formValue.chefDeProjetId
      };

      const operation = this.isEditMode && this.projetId
        ? this.projetService.updateProjet(this.projetId, projetData)
        : this.projetService.createProjet(projetData);

      operation.subscribe({
        next: () => {
          this.router.navigate(['/projets']);
        },
        error: (err) => {
          this.error = 'Erreur lors de la sauvegarde du projet';
          this.loading = false;
          console.error('Erreur sauvegarde projet:', err);
        }
      });
    }
  }

  private formatDate(date: Date): string {
    return date.toISOString().split('T')[0];
  }

  onCancel(): void {
    this.router.navigate(['/projets']);
  }

  get titleControl() { return this.projetForm.get('titre'); }
  get descriptionControl() { return this.projetForm.get('description'); }
  get dateDebutControl() { return this.projetForm.get('dateDebut'); }
  get dateFinControl() { return this.projetForm.get('dateFin'); }
  get budgetControl() { return this.projetForm.get('budget'); }
}
