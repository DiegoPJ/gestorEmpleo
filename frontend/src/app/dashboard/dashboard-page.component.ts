import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { CompaniesService } from '../companies/companies.service';
import { Company } from '../companies/company.model';
import { Interview } from '../interviews/interview.model';
import { InterviewsService } from '../interviews/interviews.service';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.css'
})
export class DashboardPageComponent implements OnInit {
  private readonly companiesService = inject(CompaniesService);
  private readonly interviewsService = inject(InterviewsService);

  metrics = [
    { label: 'Entrevistas pendientes', value: '0' },
    { label: 'Ofertas totales', value: '0' },
    { label: 'Ofertas realizadas', value: '0' },
    { label: 'Ofertas activas', value: '0' }
  ];

  ngOnInit(): void {
    this.loadMetrics();
  }

  private loadMetrics(): void {
    forkJoin({
      offers: this.companiesService.findAll(),
      interviews: this.interviewsService.findAll()
    }).subscribe({
      next: ({ offers, interviews }) => {
        const pendingInterviews = interviews.filter((interview) => !this.isInterviewFinished(interview)).length;
        const completedOffers = offers.filter((offer) => this.isCompletedOffer(offer, interviews)).length;
        const activeOffers = offers.filter((offer) =>
          !this.isCompletedOffer(offer, interviews) && this.isActiveOffer(offer, interviews)
        ).length;

        this.metrics = [
          { label: 'Entrevistas pendientes', value: String(pendingInterviews) },
          { label: 'Ofertas totales', value: String(offers.length) },
          { label: 'Ofertas realizadas', value: String(completedOffers) },
          { label: 'Ofertas activas', value: String(activeOffers) }
        ];
      }
    });
  }

  private isCompletedOffer(offer: Company, interviews: Interview[]): boolean {
    return Boolean(offer.finalClientProcessNotes?.trim())
      || interviews.some((interview) =>
        interview.companyId === offer.id
        && interview.type === 'CLIENTE_FINAL'
        && this.isInterviewFinished(interview)
      );
  }

  private isActiveOffer(offer: Company, interviews: Interview[]): boolean {
    return Boolean(
      offer.recruiterProcessNotes?.trim()
      || offer.consultancyProcessNotes?.trim()
      || offer.finalClientProcessNotes?.trim()
    ) || interviews.some((interview) =>
      interview.companyId === offer.id && !this.isInterviewFinished(interview)
    );
  }

  private isInterviewFinished(interview: Interview): boolean {
    return new Date(`${interview.interviewDate}T${interview.interviewTime}`).getTime() < Date.now();
  }
}
