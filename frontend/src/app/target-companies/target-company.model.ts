export interface TargetCompany {
  id: number;
  name: string;
  recruiterContacted: boolean;
  companyMemberContacted: boolean;
  cvSent: boolean;
  replied: boolean;
  waiting: boolean;
  createdAt: string | null;
}

export interface CreateTargetCompanyRequest {
  name: string;
}

export interface UpdateTargetCompanyRequest {
  name: string;
  recruiterContacted: boolean;
  companyMemberContacted: boolean;
  cvSent: boolean;
  replied: boolean;
  waiting: boolean;
}
