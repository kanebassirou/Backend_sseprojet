import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest, UserType } from '../../models/user.model';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  registerForm: FormGroup;
  loading = false;
  error = '';
  success = '';
  isPasswordVisible = false;
  isConfirmPasswordVisible = false;

  userTypes = [
    { value: 'CHEF_DE_PROJET', label: 'Chef de Projet' },
    { value: 'DECIDEUR', label: 'Décideur' },
    { value: 'EVALUATEUR', label: 'Évaluateur' }
  ];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      type: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');

    if (password && confirmPassword && password.value !== confirmPassword.value) {
      return { passwordMismatch: true };
    }
    return null;
  }

  onSubmit(): void {
    if (this.registerForm.valid && !this.loading) {
      this.loading = true;
      this.error = '';
      this.success = '';

      const formValue = this.registerForm.value;
      const registerData: RegisterRequest = {
        nom: formValue.nom,
        email: formValue.email,
        motDePasse: formValue.password, // Backend utilise 'motDePasse'
        typeUtilisateur: this.mapUserTypeToBackend(formValue.type) // Backend utilise 'typeUtilisateur'
      };

      this.authService.register(registerData).subscribe({
        next: (user) => {
          this.success = `Inscription réussie ! Bienvenue ${user.nom}. Redirection...`;
          this.loading = false;
          // La redirection est automatiquement gérée par le service AuthService
        },
        error: (error) => {
          this.error = error.message || 'Erreur lors de l\'inscription';
          this.loading = false;
        }
      });
    }
  }

  togglePasswordVisibility(): void {
    this.isPasswordVisible = !this.isPasswordVisible;
  }

  toggleConfirmPasswordVisibility(): void {
    this.isConfirmPasswordVisible = !this.isConfirmPasswordVisible;
  }

  // 🔄 Mapper UserType frontend vers format backend
  private mapUserTypeToBackend(userType: UserType): string {
    switch (userType) {
      case 'ADMINISTRATEUR':
        return 'ADMINISTRATEUR';
      case 'CHEF_DE_PROJET':
        return 'CHEF_PROJET';
      case 'DECIDEUR':
        return 'DECIDEUR';
      case 'EVALUATEUR':
        return 'EVALUATEUR';
      default:
        return 'DECIDEUR';
    }
  }

  // Getters pour la validation
  get nomControl() { return this.registerForm.get('nom'); }
  get emailControl() { return this.registerForm.get('email'); }
  get passwordControl() { return this.registerForm.get('password'); }
  get confirmPasswordControl() { return this.registerForm.get('confirmPassword'); }
  get typeControl() { return this.registerForm.get('type'); }
}
