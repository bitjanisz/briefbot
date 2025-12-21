-- 1. MODULE: IDENTITY
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    oidc_sub VARCHAR(100) UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    given_name VARCHAR(100),
    family_name VARCHAR(100),
    picture_url VARCHAR(255),
    password_hash VARCHAR(255),
    last_login_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_accounts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_accounts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_accounts_account FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    CONSTRAINT uq_user_account UNIQUE(user_id, account_id)
);

CREATE TABLE account_user_roles (
    user_account_id BIGINT NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_account_id, role_name),
    CONSTRAINT fk_user_roles_account FOREIGN KEY (user_account_id) REFERENCES user_accounts(id) ON DELETE CASCADE
);


-- 2. MODULE: KNOWLEDGE BASE
CREATE TABLE company_profiles (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    company_legal_name VARCHAR(255),
    tax_id VARCHAR(50),
    address_line TEXT,
    contact_email VARCHAR(150),
    contact_phone VARCHAR(50),
    short_value_proposition TEXT,
    core_values JSONB,
    ai_tone_style VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_company_profiles_account FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE case_studies (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    project_name VARCHAR(255) NOT NULL,
    client_industry VARCHAR(100),
    keywords JSONB,
    scope_summary TEXT,
    challenges_solved TEXT,
    budget_range_enum VARCHAR(50),
    is_public BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_case_studies_account FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- 3. MODULE: CATALOG
CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    base_price DECIMAL(12, 2) NOT NULL,
    vat_rate DECIMAL(5, 2) DEFAULT 23.00,
    currency VARCHAR(3) DEFAULT 'PLN',
    pricing_unit VARCHAR(50) NOT NULL,
    min_price_threshold DECIMAL(12, 2),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_services_account FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE service_relations (
    id BIGSERIAL PRIMARY KEY,
    parent_service_id BIGINT NOT NULL,
    related_service_id BIGINT NOT NULL,
    relation_type VARCHAR(50) NOT NULL,
    impact_description VARCHAR(255),
    CONSTRAINT fk_rel_parent FOREIGN KEY (parent_service_id) REFERENCES services(id) ON DELETE CASCADE,
    CONSTRAINT fk_rel_related FOREIGN KEY (related_service_id) REFERENCES services(id) ON DELETE CASCADE
);

-- 4. MODULE: WORKFLOW
CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    company_name VARCHAR(255),
    industry VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_clients_account FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE briefings (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    client_id BIGINT NOT NULL,
    status VARCHAR(50) DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_briefings_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    CONSTRAINT fk_briefings_client FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE briefing_versions (
    id BIGSERIAL PRIMARY KEY,
    briefing_id BIGINT NOT NULL,
    version_number INT NOT NULL,
    form_structure JSONB,
    client_responses JSONB,
    created_by_user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_briefing_versions_briefing FOREIGN KEY (briefing_id) REFERENCES briefings(id) ON DELETE CASCADE
);

-- WAŻNE: To jest tabela od której zależy offer_versions
CREATE TABLE offers (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    client_id BIGINT NOT NULL,
    briefing_id BIGINT,
    current_status VARCHAR(50) DEFAULT 'DRAFT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_offers_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    CONSTRAINT fk_offers_client FOREIGN KEY (client_id) REFERENCES clients(id),
    CONSTRAINT fk_offers_briefing FOREIGN KEY (briefing_id) REFERENCES briefings(id)
);

-- Tutaj pojawiał się Twój błąd. Teraz zadziała, bo offers już istnieje.
CREATE TABLE offer_versions (
    id BIGSERIAL PRIMARY KEY,
    offer_id BIGINT NOT NULL,
    version_number INT NOT NULL,
    introduction_content TEXT,
    scope_content TEXT,
    methodology_content TEXT,
    summary_content TEXT,
    total_netto DECIMAL(12, 2),
    total_brutto DECIMAL(12, 2),
    currency VARCHAR(3) DEFAULT 'PLN',
    has_spelling_errors BOOLEAN DEFAULT FALSE,
    ai_suggestions JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_offer_versions_offer FOREIGN KEY (offer_id) REFERENCES offers(id) ON DELETE CASCADE
);

CREATE TABLE offer_version_items (
    id BIGSERIAL PRIMARY KEY,
    offer_version_id BIGINT NOT NULL,
    original_service_id BIGINT,
    service_name VARCHAR(255) NOT NULL,
    description TEXT,
    quantity DECIMAL(10, 2) DEFAULT 1.00,
    price DECIMAL(12, 2) NOT NULL,
    vat_rate DECIMAL(5, 2) NOT NULL,
    CONSTRAINT fk_offer_items_version FOREIGN KEY (offer_version_id) REFERENCES offer_versions(id) ON DELETE CASCADE,
    CONSTRAINT fk_offer_items_service FOREIGN KEY (original_service_id) REFERENCES services(id) ON DELETE SET NULL
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    offer_version_id BIGINT NOT NULL UNIQUE,
    contract_status VARCHAR(50) DEFAULT 'PENDING_SIGNATURE',
    contract_file_url VARCHAR(255),
    signed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    CONSTRAINT fk_orders_offer_version FOREIGN KEY (offer_version_id) REFERENCES offer_versions(id)
);

-- INDEKSY (Tworzone na końcu, gdy tabele już istnieją)
CREATE INDEX idx_user_accounts_user ON user_accounts(user_id);
CREATE INDEX idx_user_accounts_account ON user_accounts(account_id);
CREATE INDEX idx_services_account ON services(account_id);
CREATE INDEX idx_clients_account ON clients(account_id);
CREATE INDEX idx_briefings_client ON briefings(client_id);
CREATE INDEX idx_offers_client ON offers(client_id);
CREATE INDEX idx_offer_versions_offer ON offer_versions(offer_id);
CREATE INDEX idx_orders_offer_version ON orders(offer_version_id);
