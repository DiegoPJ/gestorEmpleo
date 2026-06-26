import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Company, CreateCompanyRequest } from './company.model';

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
}
