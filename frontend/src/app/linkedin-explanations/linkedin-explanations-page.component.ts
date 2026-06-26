import { Component, inject, OnInit } from '@angular/core';
import { NgClass } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { LinkedinExplanation } from './linkedin-explanation.model';
import { LinkedinExplanationsService } from './linkedin-explanations.service';

@Component({
  selector: 'app-linkedin-explanations-page',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass],
  templateUrl: './linkedin-explanations-page.component.html',
  styleUrl: '../cv-explanations/cv-explanations-page.component.css'
})
export class LinkedinExplanationsPageComponent implements OnInit {
  private readonly linkedinExplanationsService = inject(LinkedinExplanationsService);
  private readonly formBuilder = inject(FormBuilder);

  explanations: LinkedinExplanation[] = [];
  loading = false;
  saving = false;
  deletingId: number | null = null;
  editingId: number | null = null;
  errorMessage = '';
  formExpanded = false;

  readonly form = this.formBuilder.nonNullable.group({
    question: ['', [Validators.required]],
    answer: ['', [Validators.required]]
  });

  ngOnInit(): void {
    this.loadExplanations();
  }

  loadExplanations(): void {
    this.loading = true;
    this.errorMessage = '';

    this.linkedinExplanationsService.findAll().subscribe({
      next: (explanations) => {
        this.explanations = explanations;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No se han podido cargar las preguntas.';
        this.loading = false;
      }
    });
  }

  submitExplanation(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving = true;
    this.errorMessage = '';

    if (this.editingId !== null) {
      this.updateExplanation();
      return;
    }

    this.linkedinExplanationsService.create(this.form.getRawValue()).subscribe({
      next: (explanation) => {
        this.explanations = [explanation, ...this.explanations];
        this.resetForm();
        this.formExpanded = false;
        this.saving = false;
      },
      error: () => {
        this.errorMessage = 'No se ha podido guardar la pregunta.';
        this.saving = false;
      }
    });
  }

  startEdit(explanation: LinkedinExplanation): void {
    this.editingId = explanation.id;
    this.formExpanded = true;
    this.form.reset({
      question: explanation.question,
      answer: explanation.answer
    });
  }

  cancelEdit(): void {
    this.resetForm();
    this.formExpanded = false;
  }

  toggleForm(): void {
    this.formExpanded = !this.formExpanded;
    if (!this.formExpanded) {
      this.resetForm();
    }
  }

  deleteExplanation(explanation: LinkedinExplanation): void {
    this.deletingId = explanation.id;
    this.errorMessage = '';

    this.linkedinExplanationsService.deleteById(explanation.id).subscribe({
      next: () => {
        this.explanations = this.explanations.filter((currentExplanation) => currentExplanation.id !== explanation.id);
        this.deletingId = null;
      },
      error: () => {
        this.errorMessage = 'No se ha podido borrar la pregunta.';
        this.deletingId = null;
      }
    });
  }

  private updateExplanation(): void {
    if (this.editingId === null) {
      return;
    }

    this.linkedinExplanationsService.update(this.editingId, this.form.getRawValue()).subscribe({
      next: (explanation) => {
        this.explanations = this.explanations.map((currentExplanation) =>
          currentExplanation.id === explanation.id ? explanation : currentExplanation
        );
        this.resetForm();
        this.formExpanded = false;
        this.saving = false;
      },
      error: () => {
        this.errorMessage = 'No se ha podido actualizar la pregunta.';
        this.saving = false;
      }
    });
  }

  private resetForm(): void {
    this.form.reset({ question: '', answer: '' });
    this.editingId = null;
  }
}
