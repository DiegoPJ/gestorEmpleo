import { Routes } from '@angular/router';
import { CompaniesPageComponent } from './companies/companies-page.component';
import { DashboardPageComponent } from './dashboard/dashboard-page.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'dashboard', component: DashboardPageComponent, title: 'Dashboard' },
  { path: 'empresas', component: CompaniesPageComponent, title: 'Empresas' },
  { path: '**', redirectTo: 'dashboard' }
];
