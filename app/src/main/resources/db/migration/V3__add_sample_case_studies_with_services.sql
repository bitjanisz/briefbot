-- Insert sample services based on provided data
INSERT INTO services (account_id, name, description, base_price, vat_rate, currency, pricing_unit, min_price_threshold, is_active, created_at)
VALUES
    (1, 'Content Marketing Strategy', 'Comprehensive content marketing strategy development including content calendar, audience analysis, and channel strategy', 8000.00, 23.00, 'PLN', 'project', 6000.00, true, CURRENT_TIMESTAMP),
    (1, 'Google Ads Campaign', 'Professional Google Ads campaign setup and management including keyword research, ad creation, and performance optimization', 5000.00, 23.00, 'PLN', 'project', 3000.00, true, CURRENT_TIMESTAMP),
    (1, 'SEO Optimization', 'Complete SEO optimization service including technical SEO, content optimization, and link building strategy', 6000.00, 23.00, 'PLN', 'project', 4000.00, true, CURRENT_TIMESTAMP),
    (1, 'Social Media Management', 'Full social media management service including content creation, posting schedule, and community engagement', 4000.00, 23.00, 'PLN', 'project', 2500.00, true, CURRENT_TIMESTAMP),
    (1, 'Email Marketing Automation', 'Email marketing automation setup including email templates, segmentation, and automated workflows', 3000.00, 23.00, 'PLN', 'project', 2000.00, false, CURRENT_TIMESTAMP);

-- Insert sample case studies
INSERT INTO case_studies (account_id, project_name, client_industry, keywords, scope_summary, challenges_solved, budget_range_enum, status, created_at)
VALUES
    (1, 'E-commerce Platform Transformation', 'Retail', 'ecommerce,digital transformation,react,node.js,payment integration', 'Complete digital transformation of a traditional retail business into a modern e-commerce platform with integrated payment systems, inventory management, and customer analytics.', 'Successfully migrated from offline-only sales to a scalable online platform, increasing revenue by 300% within the first year through improved customer experience and operational efficiency.', 'LARGE', 'PUBLISHED', CURRENT_TIMESTAMP),

    (1, 'Healthcare Mobile App Development', 'Healthcare', 'mobile app,patient engagement,telemedicine,ios,android', 'Development of a comprehensive mobile application for patient management, appointment scheduling, telemedicine consultations, and health record access.', 'Improved patient engagement by 250%, reduced no-show rates by 40%, and enhanced healthcare delivery through digital accessibility and real-time communication.', 'LARGE', 'PUBLISHED', CURRENT_TIMESTAMP),

    (1, 'Financial Services Digital Strategy', 'Finance', 'digital banking,security,compliance,cloud migration,api integration', 'Strategic digital transformation for a traditional bank including secure online banking platform, mobile app development, and API integrations for third-party services.', 'Achieved 99.9% uptime, reduced operational costs by 35%, and increased customer satisfaction through modern digital banking experience with enhanced security measures.', 'LARGE', 'PUBLISHED', CURRENT_TIMESTAMP),

    (1, 'SaaS Product Launch & Growth', 'Technology', 'saas,b2b,subscription model,user acquisition,growth hacking', 'End-to-end development and launch of a B2B SaaS product including market research, MVP development, user acquisition strategies, and growth optimization.', 'Successfully launched product reaching 500+ paying customers within 18 months, achieving product-market fit and establishing strong market position.', 'MEDIUM', 'PUBLISHED', CURRENT_TIMESTAMP),

    (1, 'Manufacturing Industry 4.0 Initiative', 'Manufacturing', 'industry 4.0,iot,automation,data analytics,predictive maintenance', 'Implementation of Industry 4.0 technologies including IoT sensors, predictive maintenance systems, and data analytics platform for operational optimization.', 'Reduced downtime by 45%, improved production efficiency by 30%, and enabled data-driven decision making across all operational levels.', 'LARGE', 'PUBLISHED', CURRENT_TIMESTAMP),

    (1, 'Restaurant Chain Digital Presence', 'Hospitality', 'restaurant,digital marketing,online ordering,social media,local seo', 'Comprehensive digital marketing strategy for a restaurant chain including website development, online ordering system, social media management, and local SEO optimization.', 'Increased online orders by 180%, improved brand visibility, and enhanced customer loyalty through integrated digital experience.', 'MEDIUM', 'PUBLISHED', CURRENT_TIMESTAMP);

-- Link case studies with services (case_study_services table)
-- E-commerce Platform Transformation (case study 1) - Web-focused services
INSERT INTO case_study_services (case_study_id, service_id, discount_percentage, created_at)
VALUES
    (1, 1, 15.00, CURRENT_TIMESTAMP), -- Content Marketing Strategy with 15% discount
    (1, 2, 10.00, CURRENT_TIMESTAMP), -- Google Ads Campaign with 10% discount
    (1, 3, 12.00, CURRENT_TIMESTAMP), -- SEO Optimization with 12% discount
    (1, 4, 8.00, CURRENT_TIMESTAMP);  -- Social Media Management with 8% discount

-- Healthcare Mobile App Development (case study 2) - Mobile and engagement services
INSERT INTO case_study_services (case_study_id, service_id, discount_percentage, created_at)
VALUES
    (2, 4, 20.00, CURRENT_TIMESTAMP), -- Social Media Management with 20% discount
    (2, 1, 18.00, CURRENT_TIMESTAMP); -- Content Marketing Strategy with 18% discount

-- Financial Services Digital Strategy (case study 3) - Digital transformation services
INSERT INTO case_study_services (case_study_id, service_id, discount_percentage, created_at)
VALUES
    (3, 3, 15.00, CURRENT_TIMESTAMP), -- SEO Optimization with 15% discount
    (3, 4, 12.00, CURRENT_TIMESTAMP), -- Social Media Management with 12% discount
    (3, 1, 10.00, CURRENT_TIMESTAMP); -- Content Marketing Strategy with 10% discount

-- SaaS Product Launch & Growth (case study 4) - Growth and marketing services
INSERT INTO case_study_services (case_study_id, service_id, discount_percentage, created_at)
VALUES
    (4, 2, 25.00, CURRENT_TIMESTAMP), -- Google Ads Campaign with 25% discount
    (4, 3, 20.00, CURRENT_TIMESTAMP), -- SEO Optimization with 20% discount
    (4, 4, 15.00, CURRENT_TIMESTAMP); -- Social Media Management with 15% discount

-- Manufacturing Industry 4.0 Initiative (case study 5) - Technical and optimization services
INSERT INTO case_study_services (case_study_id, service_id, discount_percentage, created_at)
VALUES
    (5, 3, 18.00, CURRENT_TIMESTAMP), -- SEO Optimization with 18% discount
    (5, 1, 15.00, CURRENT_TIMESTAMP); -- Content Marketing Strategy with 15% discount

-- Restaurant Chain Digital Presence (case study 6) - Local marketing services
INSERT INTO case_study_services (case_study_id, service_id, discount_percentage, created_at)
VALUES
    (6, 4, 22.00, CURRENT_TIMESTAMP), -- Social Media Management with 22% discount
    (6, 3, 18.00, CURRENT_TIMESTAMP), -- SEO Optimization with 18% discount
    (6, 2, 15.00, CURRENT_TIMESTAMP); -- Google Ads Campaign with 15% discount
