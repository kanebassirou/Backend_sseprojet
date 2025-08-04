import { Component } from '@angular/core';
import { NavigationComponent } from './shared/navigation.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NavigationComponent],
  template: '<app-navigation></app-navigation>',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'sseprojet-frontend';
}
