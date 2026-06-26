export type ContactType = 'RECRUITER' | 'COMPANY_MEMBER';

export interface Company {
  id: number;
  name: string;
  offerTitle: string | null;
  contactName: string | null;
  contactType: ContactType | null;
  offerComment: string | null;
  recruiterProcessNotes: string | null;
  consultancyProcessNotes: string | null;
  finalClientProcessNotes: string | null;
  createdAt: string | null;
}

export interface CreateCompanyRequest {
  name: string;
  offerTitle: string;
  contactName: string;
  contactType: ContactType;
  offerComment: string;
  recruiterProcessNotes: string;
  consultancyProcessNotes: string;
  finalClientProcessNotes: string;
}

export type UpdateCompanyRequest = CreateCompanyRequest;
