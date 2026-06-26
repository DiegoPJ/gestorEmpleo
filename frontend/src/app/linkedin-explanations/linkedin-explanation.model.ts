export interface LinkedinExplanation {
  id: number;
  question: string;
  answer: string;
  createdAt: string | null;
}

export interface LinkedinExplanationRequest {
  question: string;
  answer: string;
}
