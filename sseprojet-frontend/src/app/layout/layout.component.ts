import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationComponent } from '../shared/navigation.component';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    CommonModule,
    NavigationComponent
  ],
  template: `
    <app-navigation></app-navigation>
  `
})
export class LayoutComponent {
}
