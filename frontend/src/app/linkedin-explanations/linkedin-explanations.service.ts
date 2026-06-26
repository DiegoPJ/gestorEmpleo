import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LinkedinExplanation, LinkedinExplanationRequest } from './linkedin-explanation.model';

@Injectable({ providedIn: 'root' })
export class LinkedinExplanationsService {
  private readonly apiUrl = '/api/linkedin-explanations';

  constructor(private readonly http: HttpClient) {
  }

  findAll(): Observable<LinkedinExplanation[]> {
    return this.http.get<LinkedinExplanation[]>(this.apiUrl);
  }

  create(request: LinkedinExplanationRequest): Observable<LinkedinExplanation> {
    return this.http.post<LinkedinExplanation>(this.apiUrl, request);
  }

  update(id: number, request: LinkedinExplanationRequest): Observable<LinkedinExplanation> {
    return this.http.put<LinkedinExplanation>(`${this.apiUrl}/${id}`, request);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
