import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  readonly navItems = [
    { label: 'Dashboard', path: '/dashboard' },
    { label: 'Empresas', path: '/empresas' },
    { label: 'Candidaturas', path: '/dashboard' },
    { label: 'Contactos', path: '/dashboard' },
    { label: 'Recordatorios', path: '/dashboard' }
  ];
}
