import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CvExplanation, CvExplanationRequest } from './cv-explanation.model';

@Injectable({ providedIn: 'root' })
export class CvExplanationsService {
  private readonly apiUrl = '/api/cv-explanations';

  constructor(private readonly http: HttpClient) {
  }

  findAll(): Observable<CvExplanation[]> {
    return this.http.get<CvExplanation[]>(this.apiUrl);
  }

  create(request: CvExplanationRequest): Observable<CvExplanation> {
    return this.http.post<CvExplanation>(this.apiUrl, request);
  }

  update(id: number, request: CvExplanationRequest): Observable<CvExplanation> {
    return this.http.put<CvExplanation>(`${this.apiUrl}/${id}`, request);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
