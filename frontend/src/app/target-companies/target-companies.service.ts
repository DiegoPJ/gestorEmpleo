import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateTargetCompanyRequest, TargetCompany, UpdateTargetCompanyRequest } from './target-company.model';

@Injectable({ providedIn: 'root' })
export class TargetCompaniesService {
  private readonly apiUrl = '/api/target-companies';

  constructor(private readonly http: HttpClient) {
  }

  findAll(): Observable<TargetCompany[]> {
    return this.http.get<TargetCompany[]>(this.apiUrl);
  }

  create(request: CreateTargetCompanyRequest): Observable<TargetCompany> {
    return this.http.post<TargetCompany>(this.apiUrl, request);
  }

  update(id: number, request: UpdateTargetCompanyRequest): Observable<TargetCompany> {
    return this.http.put<TargetCompany>(`${this.apiUrl}/${id}`, request);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
