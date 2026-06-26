import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.css'
})
export class DashboardPageComponent {
  readonly metrics = [
    { label: 'Candidaturas activas', value: '0' },
    { label: 'Entrevistas pendientes', value: '0' },
    { label: 'Recordatorios', value: '0' },
    { label: 'Empresas guardadas', value: '0' }
  ];
}
