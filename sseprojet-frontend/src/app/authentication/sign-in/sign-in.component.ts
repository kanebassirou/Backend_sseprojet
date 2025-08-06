import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/user.model';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  loginForm: FormGroup;
  loading = false;
  error = '';
  success = '';
  isPasswordVisible = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid && !this.loading) {
      this.loading = true;
      this.error = '';
      this.success = '';

      const formValue = this.loginForm.value;
      const credentials: LoginRequest = {
        email: formValue.email,
        motDePasse: formValue.password // Backend utilise 'motDePasse'
      };

      // Utilise l'API JWT backend
      this.authService.login(credentials).subscribe({
        next: (user) => {
          this.success = `Connexion réussie ! Redirection vers votre espace ${user.type}...`;
          this.loading = false;
          // La redirection est automatiquement gérée par le service AuthService
        },
        error: (error) => {
          this.error = error.message || 'Erreur de connexion';
          this.loading = false;
        }
      });
    }
  }

  togglePasswordVisibility(): void {
    this.isPasswordVisible = !this.isPasswordVisible;
  }

  // Getters pour la validation
  get emailControl() { return this.loginForm.get('email'); }
  get passwordControl() { return this.loginForm.get('password'); }
}
