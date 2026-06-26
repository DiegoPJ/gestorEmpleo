import { DatePipe, NgClass } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { CompaniesService } from '../companies/companies.service';
import { Company } from '../companies/company.model';
import { Interview, InterviewType } from './interview.model';
import { InterviewsService } from './interviews.service';

@Component({
  selector: 'app-calendar-page',
  standalone: true,
  imports: [DatePipe, NgClass, RouterLink],
  templateUrl: './calendar-page.component.html',
  styleUrl: './calendar-page.component.css'
})
export class CalendarPageComponent implements OnInit {
  private readonly interviewsService = inject(InterviewsService);
  private readonly companiesService = inject(CompaniesService);

  interviews: Interview[] = [];
  companies = new Map<number, Company>();
  loading = false;
  deletingId: number | null = null;
  errorMessage = '';

  ngOnInit(): void {
    this.loadCalendar();
  }

  get activeInterviews(): Interview[] {
    return this.interviews.filter((interview) => !this.isPast(interview));
  }

  get finishedInterviews(): Interview[] {
    return this.interviews.filter((interview) => this.isPast(interview));
  }

  loadCalendar(): void {
    this.loading = true;
    this.errorMessage = '';

    forkJoin({
      interviews: this.interviewsService.findAll(),
      companies: this.companiesService.findAll()
    }).subscribe({
      next: ({ interviews, companies }) => {
        this.interviews = [...interviews].sort((firstInterview, secondInterview) =>
          this.toTimestamp(firstInterview) - this.toTimestamp(secondInterview)
        );
        this.companies = new Map(companies.map((company) => [company.id, company]));
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No se ha podido cargar el calendario.';
        this.loading = false;
      }
    });
  }

  getCompany(interview: Interview): Company | undefined {
    return this.companies.get(interview.companyId);
  }

  getTypeLabel(type: InterviewType): string {
    const labels: Record<InterviewType, string> = {
      RECRUITER: 'Recruiter',
      CONSULTORA: 'Consultora',
      CLIENTE_FINAL: 'Cliente final'
    };
    return labels[type] ?? type;
  }

  getInterviewTypeClass(type: InterviewType): string {
    const classes: Record<InterviewType, string> = {
      RECRUITER: 'type-recruiter',
      CONSULTORA: 'type-consultancy',
      CLIENTE_FINAL: 'type-final'
    };
    return classes[type];
  }

  isPast(interview: Interview): boolean {
    return this.toTimestamp(interview) < Date.now();
  }

  getComputedStatus(interview: Interview): string {
    return this.isPast(interview) ? 'Hecha' : 'Activa';
  }

  deleteInterview(interview: Interview): void {
    this.deletingId = interview.id;
    this.errorMessage = '';

    this.interviewsService.deleteById(interview.id).subscribe({
      next: () => {
        this.interviews = this.interviews.filter((currentInterview) => currentInterview.id !== interview.id);
        this.deletingId = null;
      },
      error: () => {
        this.errorMessage = 'No se ha podido borrar la entrevista.';
        this.deletingId = null;
      }
    });
  }

  private toTimestamp(interview: Interview): number {
    return new Date(`${interview.interviewDate}T${interview.interviewTime}`).getTime();
  }
}
