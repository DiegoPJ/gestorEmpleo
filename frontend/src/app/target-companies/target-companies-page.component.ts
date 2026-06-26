import { Component, inject, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { TargetCompany } from './target-company.model';
import { TargetCompaniesService } from './target-companies.service';

type TargetCompanyFlag = 'recruiterContacted' | 'companyMemberContacted' | 'cvSent' | 'replied' | 'waiting';

@Component({
  selector: 'app-target-companies-page',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, DatePipe],
  templateUrl: './target-companies-page.component.html',
  styleUrl: './target-companies-page.component.css'
})
export class TargetCompaniesPageComponent implements OnInit {
  private readonly targetCompaniesService = inject(TargetCompaniesService);
  private readonly formBuilder = inject(FormBuilder);

  companies: TargetCompany[] = [];
  loading = false;
  saving = false;
  deletingId: number | null = null;
  editingId: number | null = null;
  editingName = '';
  errorMessage = '';

  readonly form = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(140)]]
  });

  ngOnInit(): void {
    this.loadCompanies();
  }

  loadCompanies(): void {
    this.loading = true;
    this.errorMessage = '';

    this.targetCompaniesService.findAll().subscribe({
      next: (companies) => {
        this.companies = companies;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No se han podido cargar las empresas.';
        this.loading = false;
      }
    });
  }

  createCompany(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving = true;
    this.errorMessage = '';

    this.targetCompaniesService.create(this.form.getRawValue()).subscribe({
      next: (company) => {
        this.companies = [company, ...this.companies];
        this.form.reset({ name: '' });
        this.saving = false;
      },
      error: () => {
        this.errorMessage = 'No se ha podido crear la empresa.';
        this.saving = false;
      }
    });
  }

  toggleFlag(company: TargetCompany, flag: TargetCompanyFlag, checked: boolean): void {
    const updatedCompany = { ...company, [flag]: checked };
    this.companies = this.companies.map((currentCompany) =>
      currentCompany.id === company.id ? updatedCompany : currentCompany
    );

    this.targetCompaniesService.update(company.id, {
      name: updatedCompany.name,
      recruiterContacted: updatedCompany.recruiterContacted,
      companyMemberContacted: updatedCompany.companyMemberContacted,
      cvSent: updatedCompany.cvSent,
      replied: updatedCompany.replied,
      waiting: updatedCompany.waiting
    }).subscribe({
      next: (savedCompany) => {
        this.companies = this.companies.map((currentCompany) =>
          currentCompany.id === savedCompany.id ? savedCompany : currentCompany
        );
      },
      error: () => {
        this.errorMessage = 'No se ha podido actualizar la empresa.';
        this.companies = this.companies.map((currentCompany) =>
          currentCompany.id === company.id ? company : currentCompany
        );
      }
    });
  }

  startEdit(company: TargetCompany): void {
    this.editingId = company.id;
    this.editingName = company.name;
    this.errorMessage = '';
  }

  cancelEdit(): void {
    this.editingId = null;
    this.editingName = '';
  }

  saveEdit(company: TargetCompany): void {
    const name = this.editingName.trim();
    if (!name) {
      this.errorMessage = 'El nombre de la empresa es obligatorio.';
      return;
    }

    const updatedCompany = { ...company, name };
    this.targetCompaniesService.update(company.id, {
      name: updatedCompany.name,
      recruiterContacted: updatedCompany.recruiterContacted,
      companyMemberContacted: updatedCompany.companyMemberContacted,
      cvSent: updatedCompany.cvSent,
      replied: updatedCompany.replied,
      waiting: updatedCompany.waiting
    }).subscribe({
      next: (savedCompany) => {
        this.companies = this.companies.map((currentCompany) =>
          currentCompany.id === savedCompany.id ? savedCompany : currentCompany
        );
        this.cancelEdit();
      },
      error: () => {
        this.errorMessage = 'No se ha podido actualizar la empresa.';
      }
    });
  }

  deleteCompany(company: TargetCompany): void {
    this.deletingId = company.id;
    this.errorMessage = '';

    this.targetCompaniesService.deleteById(company.id).subscribe({
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
}
