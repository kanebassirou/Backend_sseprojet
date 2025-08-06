import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-admin-test',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="admin-test-container">
      <h2>🔧 Test et Configuration de l'API</h2>

      <div class="test-section">
        <h3>Initialisation Admin</h3>
        <button
          (click)="initializeAdmin()"
          [disabled]="loading"
          class="btn btn-primary">
          {{ loading ? 'Initialisation...' : 'Initialiser Admin' }}
        </button>

        <div *ngIf="initResult" class="result">
          <h4>Résultat :</h4>
          <pre>{{ initResult | json }}</pre>
        </div>
      </div>

      <div class="test-section">
        <h3>Test API</h3>
        <button
          (click)="testApi()"
          [disabled]="loading"
          class="btn btn-secondary">
          {{ loading ? 'Test en cours...' : 'Tester API' }}
        </button>

        <div *ngIf="apiResult" class="result">
          <h4>Résultat :</h4>
          <pre>{{ apiResult | json }}</pre>
        </div>
      </div>

      <div class="test-section">
        <h3>Informations de connexion Admin par défaut</h3>
        <div class="info-box">
          <p><strong>Email :</strong> admin&#64;test.com</p>
          <p><strong>Mot de passe :</strong> password123</p>
          <p><strong>Rôle :</strong> ADMINISTRATEUR</p>
        </div>
      </div>

      <div *ngIf="error" class="error">
        {{ error }}
      </div>
    </div>
  `,
  styles: [`
    .admin-test-container {
      max-width: 800px;
      margin: 2rem auto;
      padding: 2rem;
      background: white;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }

    .test-section {
      margin-bottom: 2rem;
      padding: 1rem;
      border: 1px solid #e0e0e0;
      border-radius: 4px;
    }

    .btn {
      padding: 0.5rem 1rem;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-weight: 500;
    }

    .btn:disabled {
      opacity: 0.6;
      cursor: not-allowed;
    }

    .btn-primary {
      background-color: #2196F3;
      color: white;
    }

    .btn-secondary {
      background-color: #666;
      color: white;
    }

    .result {
      margin-top: 1rem;
      padding: 1rem;
      background-color: #f5f5f5;
      border-radius: 4px;
    }

    .info-box {
      padding: 1rem;
      background-color: #e3f2fd;
      border-radius: 4px;
      border-left: 4px solid #2196F3;
    }

    .error {
      padding: 1rem;
      background-color: #ffebee;
      border-radius: 4px;
      color: #c62828;
      border-left: 4px solid #f44336;
    }

    pre {
      white-space: pre-wrap;
      word-break: break-all;
    }
  `]
})
export class AdminTestComponent {
  loading = false;
  initResult: any = null;
  apiResult: any = null;
  error = '';

  constructor(private authService: AuthService) {}

  initializeAdmin(): void {
    this.loading = true;
    this.error = '';
    this.initResult = null;

    this.authService.initializeAdmin().subscribe({
      next: (result) => {
        this.initResult = result;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erreur lors de l\'initialisation : ' + error.message;
        this.loading = false;
      }
    });
  }

  testApi(): void {
    this.loading = true;
    this.error = '';
    this.apiResult = null;

    this.authService.testApi().subscribe({
      next: (result) => {
        this.apiResult = result;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erreur lors du test API : ' + error.message;
        this.loading = false;
      }
    });
  }
}
