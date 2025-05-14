SET MODE MySQL;

INSERT IGNORE  INTO authors (id, full_name, profile_picture) VALUES (95, 'Salo Movs', 'https://github.com/salomovs95.png');

INSERT IGNORE INTO users (id, full_name, username, tax_id, phone, email, password, address, role, joined_at) VALUES (61, 'user-001', 'user-001', 'user-001-tax-id', 'user-001-phone', 'user-001-email', 'user-001@password', 'user-001 st., ivalice', 'USER_COMMON', '2025-01-01');

INSERT IGNORE INTO users (id, full_name, username, tax_id, phone, email, password, address, role, joined_at) VALUES (62, 'user-002', 'user-002', 'user-002-tax-id', 'user-002-phone', 'user-002-email', 'user-002@password', 'user-002 st., ivalice', 'USER_COMMON', '2025-01-01');

INSERT IGNORE INTO users (id, full_name, username, tax_id, phone, email, password, address, role, joined_at) VALUES (63, 'user-003', 'user-003', 'user-003-tax-id', 'user-003-phone', 'user-003-email', 'user-003@password', 'user-003 st., ivalice', 'USER_COMMON', '2025-01-01');

INSERT IGNORE INTO books (id, title, author_id, page_count, esbn, edition, editor, publish_year, category, in_stock_amount, book_cover) VALUES (11, 'book-001', 95, 468, 'book-001-esbn-code', '1st ed.', 'salomovs inc.', 2025, 'dev, sci-fi', 2, 'https://github.com/salomovs95.png');
