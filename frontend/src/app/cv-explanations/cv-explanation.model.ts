export interface CvExplanation {
  id: number;
  question: string;
  answer: string;
  createdAt: string | null;
}

export interface CvExplanationRequest {
  question: string;
  answer: string;
}
