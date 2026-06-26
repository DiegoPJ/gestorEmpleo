ALTER TABLE IF EXISTS companies
    ALTER COLUMN offer_comment TYPE TEXT;

ALTER TABLE IF EXISTS companies
    ADD COLUMN IF NOT EXISTS offer_title VARCHAR(180);

ALTER TABLE IF EXISTS companies
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;

ALTER TABLE IF EXISTS companies
    ADD COLUMN IF NOT EXISTS recruiter_process_notes TEXT;

ALTER TABLE IF EXISTS companies
    ADD COLUMN IF NOT EXISTS consultancy_process_notes TEXT;

ALTER TABLE IF EXISTS companies
    ADD COLUMN IF NOT EXISTS final_client_process_notes TEXT;

UPDATE companies
SET created_at = CURRENT_TIMESTAMP
WHERE created_at IS NULL;

CREATE TABLE IF NOT EXISTS cv_explanations (
    id BIGSERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS linkedin_explanations (
    id BIGSERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS interviews (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL,
    type VARCHAR(80) NOT NULL,
    interview_date DATE NOT NULL,
    interview_time TIME NOT NULL,
    status VARCHAR(40),
    notes TEXT,
    created_at TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_interviews_company_type
    ON interviews (company_id, type);
