import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgClass } from '@angular/common';
import { CompaniesService } from './companies.service';
import { Company } from './company.model';

@Component({
  selector: 'app-companies-page',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass],
  templateUrl: './companies-page.component.html',
  styleUrl: './companies-page.component.css'
})
export class CompaniesPageComponent implements OnInit {
  private readonly companiesService = inject(CompaniesService);
  private readonly formBuilder = inject(FormBuilder);

  companies: Company[] = [];
  loading = false;
  saving = false;
  errorMessage = '';

  readonly form = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(120)]],
    sector: [''],
    website: ['']
  });

  ngOnInit(): void {
    this.loadCompanies();
  }

  loadCompanies(): void {
    this.loading = true;
    this.errorMessage = '';

    this.companiesService.findAll().subscribe({
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

    this.companiesService.create(this.form.getRawValue()).subscribe({
      next: (company) => {
        this.companies = [company, ...this.companies];
        this.form.reset();
        this.saving = false;
      },
      error: () => {
        this.errorMessage = 'No se ha podido crear la empresa.';
        this.saving = false;
      }
    });
  }
}
