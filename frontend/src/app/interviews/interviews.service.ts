import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Interview, InterviewRequest } from './interview.model';

@Injectable({ providedIn: 'root' })
export class InterviewsService {
  private readonly apiUrl = '/api/interviews';

  constructor(private readonly http: HttpClient) {
  }

  findAll(): Observable<Interview[]> {
    return this.http.get<Interview[]>(this.apiUrl);
  }

  create(request: InterviewRequest): Observable<Interview> {
    return this.http.post<Interview>(this.apiUrl, request);
  }

  update(id: number, request: InterviewRequest): Observable<Interview> {
    return this.http.put<Interview>(`${this.apiUrl}/${id}`, request);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
