import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DatePipe, NgClass } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { CompaniesService } from './companies.service';
import { Company, ContactType } from './company.model';
import { Interview, InterviewType } from '../interviews/interview.model';
import { InterviewsService } from '../interviews/interviews.service';

@Component({
  selector: 'app-companies-page',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass, DatePipe, RouterLink],
  templateUrl: './companies-page.component.html',
  styleUrl: './companies-page.component.css'
})
export class CompaniesPageComponent implements OnInit {
  private readonly companiesService = inject(CompaniesService);
  private readonly interviewsService = inject(InterviewsService);
  private readonly formBuilder = inject(FormBuilder);
  private readonly route = inject(ActivatedRoute);

  companies: Company[] = [];
  interviews: Interview[] = [];
  expandedCompanyIds = new Set<number>();
  expandedProcessIds = new Set<number>();
  loading = false;
  saving = false;
  deletingId: number | null = null;
  editingId: number | null = null;
  interviewCompanyId: number | null = null;
  savingInterview = false;
  highlightedCompanyId: number | null = null;
  errorMessage = '';
  successMessage = '';
  formExpanded = false;
  readonly contactTypeOptions: Array<{ value: ContactType; label: string }> = [
    { value: 'RECRUITER', label: 'Reclutador' },
    { value: 'COMPANY_MEMBER', label: 'Miembro de la empresa' }
  ];

  readonly form = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(120)]],
    offerTitle: ['', [Validators.required, Validators.maxLength(180)]],
    contactName: ['', [Validators.required, Validators.maxLength(120)]],
    contactType: ['RECRUITER' as ContactType, [Validators.required]],
    offerComment: [''],
    recruiterProcessNotes: [''],
    consultancyProcessNotes: [''],
    finalClientProcessNotes: ['']
  });

  readonly interviewTypeOptions: Array<{ value: InterviewType; label: string }> = [
    { value: 'RECRUITER', label: 'Recruiter' },
    { value: 'CONSULTORA', label: 'Consultora' },
    { value: 'CLIENTE_FINAL', label: 'Cliente final' }
  ];

  readonly interviewForm = this.formBuilder.nonNullable.group({
    type: ['RECRUITER' as InterviewType, [Validators.required]],
    interviewDate: ['', [Validators.required]],
    interviewTime: ['', [Validators.required]],
    notes: ['']
  });

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params) => {
      const offerId = Number(params.get('oferta'));
      this.highlightedCompanyId = Number.isFinite(offerId) && offerId > 0 ? offerId : null;
    });
    this.loadCompanies();
  }

  loadCompanies(): void {
    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    forkJoin({
      companies: this.companiesService.findAll(),
      interviews: this.interviewsService.findAll()
    }).subscribe({
      next: ({ companies, interviews }) => {
        this.companies = companies;
        this.interviews = interviews;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No se han podido cargar las empresas.';
        this.loading = false;
      }
    });
  }

  submitCompany(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving = true;
    this.errorMessage = '';
    this.successMessage = '';

    if (this.editingId !== null) {
      this.updateCompany();
      return;
    }

    this.companiesService.create(this.form.getRawValue()).subscribe({
      next: (company) => {
        this.companies = [company, ...this.companies];
        this.resetForm();
        this.saving = false;
      },
      error: () => {
        this.errorMessage = 'No se ha podido crear la empresa.';
        this.saving = false;
      }
    });
  }

  toggleForm(): void {
    this.formExpanded = !this.formExpanded;
    if (!this.formExpanded) {
      this.resetForm(false);
    }
  }

  startEdit(company: Company): void {
    this.editingId = company.id;
    this.formExpanded = true;
    this.form.reset({
      name: company.name,
      offerTitle: company.offerTitle ?? '',
      contactName: company.contactName ?? '',
      contactType: company.contactType ?? 'RECRUITER',
      offerComment: company.offerComment ?? '',
      recruiterProcessNotes: company.recruiterProcessNotes ?? '',
      consultancyProcessNotes: company.consultancyProcessNotes ?? '',
      finalClientProcessNotes: company.finalClientProcessNotes ?? ''
    });
  }

  cancelEdit(): void {
    this.resetForm();
  }

  getEditingTitle(): string {
    if (this.editingId === null) {
      return 'Nueva oferta';
    }

    const company = this.companies.find((currentCompany) => currentCompany.id === this.editingId);
    return company ? `Editando: ${company.name} - ${company.offerTitle || 'Oferta sin titulo'}` : 'Editando oferta';
  }

  getContactTypeLabel(contactType: ContactType | null): string {
    return this.contactTypeOptions.find((option) => option.value === contactType)?.label ?? 'Sin tipo';
  }

  isExpanded(companyId: number): boolean {
    return this.expandedCompanyIds.has(companyId);
  }

  isLongComment(comment: string | null): boolean {
    if (!comment) {
      return false;
    }

    return comment.length > 220 || comment.split(/\r?\n/).length > 3;
  }

  toggleComment(companyId: number): void {
    if (this.expandedCompanyIds.has(companyId)) {
      this.expandedCompanyIds.delete(companyId);
      return;
    }

    this.expandedCompanyIds.add(companyId);
  }

  isProcessExpanded(companyId: number): boolean {
    return this.expandedProcessIds.has(companyId);
  }

  toggleProcess(companyId: number): void {
    if (this.expandedProcessIds.has(companyId)) {
      this.expandedProcessIds.delete(companyId);
      return;
    }

    this.expandedProcessIds.add(companyId);
  }

  hasProcess(company: Company): boolean {
    return Boolean(
      company.recruiterProcessNotes?.trim()
      || company.consultancyProcessNotes?.trim()
      || company.finalClientProcessNotes?.trim()
      || this.hasFinishedInterview(company, 'RECRUITER')
      || this.hasFinishedInterview(company, 'CONSULTORA')
      || this.hasFinishedInterview(company, 'CLIENTE_FINAL')
    );
  }

  getProcessStageLabel(company: Company): string {
    if (company.finalClientProcessNotes?.trim()) {
      return 'Cliente final';
    }

    if (company.consultancyProcessNotes?.trim()) {
      return 'Consultora';
    }

    if (company.recruiterProcessNotes?.trim()) {
      return 'Recruiter';
    }

    return 'Sin proceso';
  }

  deleteCompany(company: Company): void {
    this.deletingId = company.id;
    this.errorMessage = '';
    this.successMessage = '';

    this.companiesService.deleteById(company.id).subscribe({
      next: () => {
        this.companies = this.companies.filter((currentCompany) => currentCompany.id !== company.id);
        this.deletingId = null;
      },
      error: () => {
        this.errorMessage = 'No se ha podido borrar la empresa.';
        this.deletingId = null;
      }
    });
  }

  startInterview(company: Company): void {
    const availableTypes = this.getAvailableInterviewTypeOptions(company);
    if (availableTypes.length === 0) {
      this.errorMessage = 'Esta oferta ya tiene cubiertas las entrevistas posibles para su fase actual.';
      return;
    }

    this.interviewCompanyId = company.id;
    this.interviewForm.reset({
      type: availableTypes[0].value,
      interviewDate: '',
      interviewTime: '',
      notes: ''
    });
  }

  cancelInterview(): void {
    this.interviewCompanyId = null;
    this.interviewForm.reset({
      type: 'RECRUITER',
      interviewDate: '',
      interviewTime: '',
      notes: ''
    });
  }

  createInterview(company: Company): void {
    if (this.interviewForm.invalid) {
      this.interviewForm.markAllAsTouched();
      return;
    }

    this.savingInterview = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.interviewsService.create({
      companyId: company.id,
      status: 'PENDIENTE',
      ...this.interviewForm.getRawValue()
    }).subscribe({
      next: (interview) => {
        this.interviews = [...this.interviews, interview];
        this.successMessage = 'Se ha creado la entrevista en el calendario.';
        this.savingInterview = false;
        this.cancelInterview();
      },
      error: (error) => {
        this.errorMessage = error?.error?.message ?? 'No se ha podido crear la entrevista.';
        this.savingInterview = false;
      }
    });
  }

  getAvailableInterviewTypeOptions(company: Company): Array<{ value: InterviewType; label: string }> {
    const currentStageIndex = this.getCurrentStageIndex(company);
    const existingTypes = new Set(
      this.interviews
        .filter((interview) => interview.companyId === company.id)
        .map((interview) => interview.type)
    );

    return this.interviewTypeOptions.filter((option) =>
      this.getInterviewTypeIndex(option.value) >= currentStageIndex && !existingTypes.has(option.value)
    );
  }

  hasAvailableInterviewTypes(company: Company): boolean {
    return this.getAvailableInterviewTypeOptions(company).length > 0;
  }

  getActiveInterviewLevel(company: Company): number | null {
    const activeInterviewTypes = this.interviews
      .filter((interview) =>
        interview.companyId === company.id
        && !this.isInterviewFinished(interview)
      )
      .map((interview) => this.getInterviewTypeIndex(interview.type));

    if (activeInterviewTypes.length === 0) {
      return null;
    }

    return Math.max(...activeInterviewTypes) + 1;
  }

  getInterviewActionLabel(company: Company): string {
    return this.getActiveInterviewLevel(company)?.toString() ?? '+';
  }

  getInterviewActionClass(company: Company): string {
    const level = this.getActiveInterviewLevel(company);

    if (level === 1) {
      return 'interview-level-recruiter';
    }

    if (level === 2) {
      return 'interview-level-consultancy';
    }

    if (level === 3) {
      return 'interview-level-final';
    }

    return 'interview-level-empty';
  }

  hasFinishedInterview(company: Company, type: InterviewType): boolean {
    return this.interviews.some((interview) =>
      interview.companyId === company.id
      && interview.type === type
      && this.isInterviewFinished(interview)
    );
  }

  getFinishedInterview(company: Company, type: InterviewType): Interview | undefined {
    return this.interviews.find((interview) =>
      interview.companyId === company.id
      && interview.type === type
      && this.isInterviewFinished(interview)
    );
  }

  hasProcessNotes(company: Company, type: InterviewType): boolean {
    const notes: Record<InterviewType, string | null> = {
      RECRUITER: company.recruiterProcessNotes,
      CONSULTORA: company.consultancyProcessNotes,
      CLIENTE_FINAL: company.finalClientProcessNotes
    };
    return Boolean(notes[type]?.trim());
  }

  private isInterviewFinished(interview: Interview): boolean {
    return new Date(`${interview.interviewDate}T${interview.interviewTime}`).getTime() < Date.now();
  }

  private getCurrentStageIndex(company: Company): number {
    if (company.finalClientProcessNotes?.trim()) {
      return 2;
    }

    if (company.consultancyProcessNotes?.trim()) {
      return 1;
    }

    return 0;
  }

  private getInterviewTypeIndex(type: InterviewType): number {
    const indexes: Record<InterviewType, number> = {
      RECRUITER: 0,
      CONSULTORA: 1,
      CLIENTE_FINAL: 2
    };
    return indexes[type];
  }

  private updateCompany(): void {
    if (this.editingId === null) {
      return;
    }

    this.companiesService.update(this.editingId, this.form.getRawValue()).subscribe({
      next: (company) => {
        this.companies = this.companies.map((currentCompany) =>
          currentCompany.id === company.id ? company : currentCompany
        );
        this.resetForm();
        this.saving = false;
      },
      error: () => {
        this.errorMessage = 'No se ha podido actualizar la oferta.';
        this.saving = false;
      }
    });
  }

  private resetForm(collapse = true): void {
    this.form.reset({
      name: '',
      offerTitle: '',
      contactName: '',
      contactType: 'RECRUITER',
      offerComment: '',
      recruiterProcessNotes: '',
      consultancyProcessNotes: '',
      finalClientProcessNotes: ''
    });
    this.editingId = null;
    if (collapse) {
      this.formExpanded = false;
    }
  }
}
