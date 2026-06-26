import { Routes } from '@angular/router';
import { CompaniesPageComponent } from './companies/companies-page.component';
import { CvExplanationsPageComponent } from './cv-explanations/cv-explanations-page.component';
import { DashboardPageComponent } from './dashboard/dashboard-page.component';
import { CalendarPageComponent } from './interviews/calendar-page.component';
import { LinkedinExplanationsPageComponent } from './linkedin-explanations/linkedin-explanations-page.component';
import { TargetCompaniesPageComponent } from './target-companies/target-companies-page.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'dashboard', component: DashboardPageComponent, title: 'Dashboard' },
  { path: 'ofertas', component: CompaniesPageComponent, title: 'Ofertas' },
  { path: 'empresas', component: TargetCompaniesPageComponent, title: 'Empresas' },
  { path: 'calendario', component: CalendarPageComponent, title: 'Calendario' },
  { path: 'cv-explicacion', component: CvExplanationsPageComponent, title: 'CV' },
  { path: 'linkedin', component: LinkedinExplanationsPageComponent, title: 'LinkedIn' },
  { path: '**', redirectTo: 'dashboard' }
];
