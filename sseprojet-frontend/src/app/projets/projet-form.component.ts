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
import { JwtService } from '../services/jwt.service';
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
    private jwtService: JwtService,
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
    // Debug: vérifier l'état de l'authentification
    this.debugAuthState();

    this.loadChefsDeProjet();

    const id = this.route.snapshot.paramMap.get('id');
    if (id && id !== 'nouveau') {
      this.isEditMode = true;
      this.projetId = +id;
      this.loadProjet();
    }
  }

  private debugAuthState(): void {
    console.log('=== DEBUG AUTH STATE ===');
    console.log('Has token:', this.jwtService.hasToken());
    console.log('Token expired:', this.jwtService.isTokenExpired());
    const authHeaders = this.jwtService.getAuthHeaders();
    console.log('Auth headers:', authHeaders);
    console.log('========================');
  }

  loadChefsDeProjet(): void {
    console.log('Chargement des chefs de projet...');

    this.userService.getUsersByType('CHEF_DE_PROJET').subscribe({
      next: (chefs) => {
        console.log('Chefs de projet reçus:', chefs);
        this.chefsDeProjet = chefs;
      },
      error: (err) => {
        console.error('Erreur chargement chefs de projet:', err);
        // En cas d'erreur, garder des données de test
        this.chefsDeProjet = [
          { id: 3, nom: 'Marie Chef', prenom: '', email: 'chef@exemple.com', type: 'CHEF_DE_PROJET' },
          { id: 10, nom: 'Ibrahima', prenom: '', email: 'ibrahima.chef@exemple.com', type: 'CHEF_DE_PROJET' },
          { id: 48, nom: 'Pierre Martin', prenom: '', email: 'pierre.martin@exemple.com', type: 'CHEF_DE_PROJET' }
        ];
      }
    });
  }

  loadProjet(): void {
    if (this.projetId) {
      this.loading = true;
      console.log('Chargement du projet ID:', this.projetId);

      this.projetService.getProjetById(this.projetId).subscribe({
        next: (projet) => {
          console.log('Projet chargé:', projet);

          this.projetForm.patchValue({
            titre: projet.titre,
            description: projet.description,
            dateDebut: new Date(projet.dateDebut),
            dateFin: new Date(projet.dateFin),
            budget: projet.budget,
            statut: projet.statut,
            chefDeProjetId: projet.chefDeProjetId
          });

          console.log('Formulaire mis à jour:', this.projetForm.value);
          this.loading = false;
        },
        error: (err) => {
          console.error('Erreur chargement projet détaillée:', err);
          this.error = `Erreur lors du chargement du projet: ${err.error?.message || err.message || 'Projet introuvable'}`;
          this.loading = false;
        }
      });
    }
  }

  onSubmit(): void {
    if (this.projetForm.valid) {
      this.loading = true;
      this.error = null;

      const formValue = this.projetForm.value;

      console.log('Valeurs du formulaire:', formValue);

      try {
        const projetData: CreateProjetRequest = {
          titre: formValue.titre,
          description: formValue.description,
          dateDebut: this.formatDate(formValue.dateDebut),
          dateFin: this.formatDate(formValue.dateFin),
          budget: +formValue.budget,
          statut: formValue.statut,
          chefDeProjetId: +formValue.chefDeProjetId
        };

        console.log('Données à envoyer:', projetData);

        const operation = this.isEditMode && this.projetId
          ? this.projetService.updateProjet(this.projetId, projetData)
          : this.projetService.createProjet(projetData);

        operation.subscribe({
          next: (response) => {
            console.log('Succès:', response);
            this.router.navigate(['/app/projets']);
          },
          error: (err) => {
            console.error('Erreur détaillée:', err);
            this.error = `Erreur lors de la sauvegarde du projet: ${err.error?.message || err.message || 'Erreur inconnue'}`;
            this.loading = false;
          }
        });
      } catch (error) {
        console.error('Erreur de formatage:', error);
        this.error = 'Erreur de formatage des données';
        this.loading = false;
      }
    } else {
      console.log('Formulaire invalide:', this.projetForm.errors);
      this.error = 'Veuillez corriger les erreurs dans le formulaire';
    }
  }

  private formatDate(date: Date | string): string {
    if (!date) {
      throw new Error('Date manquante');
    }

    let dateObj: Date;

    if (typeof date === 'string') {
      dateObj = new Date(date);
    } else if (date instanceof Date) {
      dateObj = date;
    } else {
      throw new Error('Format de date invalide');
    }

    if (isNaN(dateObj.getTime())) {
      throw new Error('Date invalide');
    }

    return dateObj.toISOString().split('T')[0];
  }

  onCancel(): void {
    this.router.navigate(['/app/projets']);
  }

  get titleControl() { return this.projetForm.get('titre'); }
  get descriptionControl() { return this.projetForm.get('description'); }
  get dateDebutControl() { return this.projetForm.get('dateDebut'); }
  get dateFinControl() { return this.projetForm.get('dateFin'); }
  get budgetControl() { return this.projetForm.get('budget'); }
}
