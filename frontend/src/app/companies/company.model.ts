export interface Company {
  id: number;
  name: string;
  sector: string | null;
  website: string | null;
}

export interface CreateCompanyRequest {
  name: string;
  sector: string;
  website: string;
}
