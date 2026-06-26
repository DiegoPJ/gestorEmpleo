import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Company, CreateCompanyRequest, UpdateCompanyRequest } from './company.model';

@Injectable({ providedIn: 'root' })
export class CompaniesService {
  private readonly apiUrl = '/api/companies';

  constructor(private readonly http: HttpClient) {
  }

  findAll(): Observable<Company[]> {
    return this.http.get<Company[]>(this.apiUrl);
  }

  create(request: CreateCompanyRequest): Observable<Company> {
    return this.http.post<Company>(this.apiUrl, request);
  }

  update(id: number, request: UpdateCompanyRequest): Observable<Company> {
    return this.http.put<Company>(`${this.apiUrl}/${id}`, request);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
