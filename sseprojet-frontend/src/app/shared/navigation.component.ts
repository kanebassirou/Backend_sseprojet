import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule, RouterOutlet } from '@angular/router';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    RouterModule,
    RouterOutlet
  ],
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent {
  
  isHandset$: Observable<boolean>;

  menuItems = [
    { path: '/dashboard', icon: 'dashboard', label: 'Tableau de Bord' },
    { path: '/projets', icon: 'folder', label: 'Projets' },
    { path: '/taches', icon: 'task', label: 'Tâches' },
    { path: '/indicateurs', icon: 'analytics', label: 'Indicateurs' },
    { path: '/rapports', icon: 'description', label: 'Rapports' },
    { path: '/users', icon: 'people', label: 'Utilisateurs' },
    { path: '/evaluateurs', icon: 'assignment_ind', label: 'Évaluateurs' }
  ];

  constructor(private breakpointObserver: BreakpointObserver) {
    this.isHandset$ = this.breakpointObserver.observe(Breakpoints.Handset)
      .pipe(
        map(result => result.matches),
        shareReplay()
      );
  }
}
