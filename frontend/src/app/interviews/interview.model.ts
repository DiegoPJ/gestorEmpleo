export type InterviewType = 'RECRUITER' | 'CONSULTORA' | 'CLIENTE_FINAL';
export type InterviewStatus = 'PENDIENTE' | 'HECHA' | 'CANCELADA';

export interface Interview {
  id: number;
  companyId: number;
  type: InterviewType;
  interviewDate: string;
  interviewTime: string;
  status: InterviewStatus;
  notes: string | null;
  createdAt: string | null;
}

export interface InterviewRequest {
  companyId: number;
  type: InterviewType;
  interviewDate: string;
  interviewTime: string;
  status: InterviewStatus;
  notes: string;
}
