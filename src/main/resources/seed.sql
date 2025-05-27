delete from borrows;
delete from books;
delete from authors;
delete from users;

insert into authors (id, full_name, profile_picture) values
(999, 'J.K. Rolling', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiW9wNKQPmx8ufqxuKQ4CKg0Rrw8KC4D8Qog&s');

insert into users(id, full_name, username, tax_id, password, email, phone, address, role, joined_at) values
(1001, 'Salo Movs', 'salomovs95', '1234567890', '$2a$12$FKdEcyhr4/YamHDnNeg.LePhV3KBN5.v4W3leXVsRE5dmlvO9VFGS', 'salo_movs@booki.ero', '+95 555-555-5555', 'Book St., 1995, LA', 'USER_ADMIN','2024-12-31'),
(1002, 'Jhon Doe', 'jdoe@001', '1234509876', '$2a$12$FKdEcyhr4/YamHDnNeg.LePhV3KBN5.v4W3leXVsRE5dmlvO9VFGS', 'jdoe_001@booki.ero', '+95 555-777-6105', 'Book St., 1995, LA', 'USER_COMMON','2025-01-01'),
(1003, 'Janne Doe', 'jdoe@002', '1289354076', '$2a$12$FKdEcyhr4/YamHDnNeg.LePhV3KBN5.v4W3leXVsRE5dmlvO9VFGS', 'jdoe_002@booki.ero', '+95 555-105-1015', 'Book St., 1995, LA', 'USER_COMMON','2025-02-01'),
(1004, 'Juddy Doe', 'jdoe@003', '11958o2o76', '$2a$12$FKdEcyhr4/YamHDnNeg.LePhV3KBN5.v4W3leXVsRE5dmlvO9VFGS', 'jdoe_003@booki.ero', '+95 555-123-4560', 'Book St., 1995, LA', 'USER_COMMON','2025-01-02'),
(1005, 'Jeremy Doe', 'jdoe@004', '1229482993', '$2a$12$FKdEcyhr4/YamHDnNeg.LePhV3KBN5.v4W3leXVsRE5dmlvO9VFGS', 'jdoe_004@booki.ero', '+95 555-656-4025', 'Book St., 1995, LA', 'USER_COMMON','2025-03-01'),
(1006, 'Jimmy Doe', 'jdoe@005', '1498292949', '$2a$12$FKdEcyhr4/YamHDnNeg.LePhV3KBN5.v4W3leXVsRE5dmlvO9VFGS', 'jdoe_005@booki.ero', '+95 555-999-1010', 'Book St., 1995, LA', 'USER_COMMON','2025-01-03');

insert into books(id, title, author_id, page_count, publish_year, esbn, category, edition, editor, in_stock_amount) values
(1001, '1984', 999, 328, 1949, '0451524934', 'Dystopian', '1st', 'Secker & Warburg', 20),
(1002, 'To Kill a Mockingbird', 999, 281, 1960, '0061120081', 'Classic', '1st', 'J.B. Lippincott & Co.', 15),
(1003, 'The Great Gatsby', 999, 180, 1925, '0743273567', 'Classic', '1st', 'Charles Scribner`s Sons', 25),
(1004, 'Moby-Dick', 999, 585, 1851, '1503280780', 'Classic', '1st', 'Harper & Brothers', 10),
(1005, 'War and Peace', 999, 1225, 1869, '1400079985', 'Historical Fiction', '1st', 'The Russian Messenger', 8),
(1006, 'Pride and Prejudice', 999, 279, 1813, '1503290565', 'Romance', '1st', 'T. Egerton', 30),
(1007, 'The Catcher in the Rye', 999, 277, 1951, '0316769487', 'Classic', '1st', 'Little, Brown and Company', 18),
(1008, 'The Hobbit', 999, 310, 1937, '054792822X', 'Fantasy', '1st', 'George Allen & Unwin', 22),
(1009, 'Fahrenheit 451', 999, 158, 1953, '1451673310', 'Dystopian', '1st', 'Ballantine Books', 17),
(1010, 'Brave New World', 999, 311, 1932, '0060850523', 'Dystopian', '1st', 'Chatto & Windus', 19),
(1011, 'The Catcher in the Rye', 999, 277, 1951, '0316769487', 'Classic', '1st', 'Little, Brown and Company', 18),
(1012, 'Crime and Punishment', 999, 430, 1866, '0486454115', 'Classic', '1st', 'The Russian Messenger', 12),
(1013, 'Anna Karenina', 999, 864, 1877, '0143035002', 'Classic', '1st', 'The Russian Messenger', 9),
(1014, 'The Brothers Karamazov', 999, 796, 1880, '0374528375', 'Classic', '1st', 'The Russian Messenger', 7),
(1015, 'Don Quixote', 999, 1072, 1605, '006093434X', 'Classic', '1st', 'Francisco de Robles', 5),
(1016, 'Ulysses', 999, 730, 1922, '0199535674', 'Modernist', '1st', 'Sylvia Beach', 6),
(1017, 'The Odyssey', 999, 541, 1925, '0140268863', 'Epic', '1st', 'Various', 4),
(1018, 'The Iliad', 999, 683, 1925, '0140275363', 'Epic', '1st', 'Various', 4),
(1019, 'Les Misérables', 999, 1463, 1862, '0451419432', 'Historical Fiction', '1st', 'A. Lacroix, Verboeckhoven & Cie', 3),
(1020, 'The Divine Comedy', 999, 798, 1320, '0140448956', 'Epic', '1st', 'Various', 2),
(1021, 'The Catcher in the Rye', 999, 277, 1951, '978-0-316-76948-0', 'Classic', '1st', 'Little, Brown and Company', 30),
(1022, 'The Great Gatsby', 999, 180, 1925, '978-0-7432-7356-5', 'Classic', '1st', 'Charles Scribner`s Sons', 25),
(1023, '1984', 999, 328, 1949, '978-0-452-28423-4', 'Dystopian', '1st', 'Secker & Warburg', 20),
(1024, 'To Kill a Mockingbird', 999, 281, 1960, '978-0-06-112008-4', 'Classic', '1st', 'J.B. Lippincott & Co.', 15),
(1025, 'Pride and Prejudice', 999, 279, 1813, '978-0-14-143951-8', 'Romance', '1st', 'T. Egerton', 35),
(1026, 'The Hobbit', 999, 310, 1937, '978-0-618-00221-3', 'Fantasy', '1st', 'George Allen & Unwin', 40),
(1027, 'Moby-Dick', 999, 585, 1851, '978-0-14-243724-7', 'Adventure', '1st', 'Harper & Brothers', 10),
(1028, 'War and Peace', 999, 1225, 1869, '978-0-14-303999-0', 'Historical Fiction', '1st', 'The Russian Messenger', 5),
(1029, 'Crime and Punishment', 999, 430, 1866, '978-0-14-305814-4', 'Psychological Fiction', '1st', 'The Russian Messenger', 12),
(1030, 'Anna Karenina', 999, 864, 1877, '978-0-14-303500-2', 'Classic', '1st', 'The Russian Messenger', 8),
(1031, 'The Brothers Karamazov', 999, 796, 1880, '978-0-14-044924-2', 'Philosophical Fiction', '1st', 'The Russian Messenger', 7),
(1032, 'Don Quixote', 999, 1072, 1605, '978-0-14-243723-0', 'Classic', '1st', 'Francisco de Robles', 6),
(1033, 'Ulysses', 999, 730, 1922, '978-0-19-953567-5', 'Modernist', '1st', 'Sylvia Beach', 4),
(1034, 'The Odyssey', 999, 541, 1964, '978-0-14-026886-7', 'Epic', '1st', 'Various', 3),
(1035, 'The Iliad', 999, 683, 1864, '978-0-14-027536-3', 'Epic', '1st', 'Various', 3),
(1036, 'Les Misérables', 999, 1463, 1862, '978-0-451-41943-5', 'Historical Fiction', '1st', 'A. Lacroix, Verboeckhoven & Cie', 2),
(1037, 'The Divine Comedy', 999, 798, 1320, '978-0-14-044895-6', 'Epic Poetry', '1st', 'Various', 2),
(1038, 'The Catcher in the Rye', 999, 277, 1951, '978-0-316-76948-0', 'Classic', '1st', 'Little, Brown and Company', 30),
(1039, 'The Great Gatsby', 999, 180, 1925, '978-0-7432-7356-5', 'Classic', '1st', 'Charles Scribner`s Sons', 25),
(1040, '1984', 999, 328, 1949, '978-0-452-28423-4', 'Dystopian', '1st', 'Secker & Warburg', 20),
(1041, 'To Kill a Mockingbird', 999, 281, 1960, '978-0-06-112008-4', 'Classic', '1st', 'J.B. Lippincott & Co.', 15),
(1042, 'Pride and Prejudice', 999, 279, 1813, '978-0-14-143951-8', 'Romance', '1st', 'T. Egerton', 35),
(1043, 'The Hobbit', 999, 310, 1937, '978-0-618-00221-3', 'Fantasy', '1st', 'George Allen & Unwin', 40),
(1044, 'Moby-Dick', 999, 585, 1851, '978-0-14-243724-7', 'Adventure', '1st', 'Harper & Brothers', 10),
(1045, 'War and Peace', 999, 1225, 1869, '978-0-14-303999-0', 'Historical Fiction', '1st', 'The Russian Messenger', 5),
(1046, 'Crime and Punishment', 999, 430, 1866, '978-0-14-305814-4', 'Psychological Fiction', '1st', 'The Russian Messenger', 12),
(1047, 'Anna Karenina', 999, 864, 1877, '978-0-14-303500-2', 'Classic', '1st', 'The Russian Messenger', 8),
(1048, 'The Brothers Karamazov', 999, 796, 1880, '978-0-14-044924-2', 'Philosophical Fiction', '1st', 'The Russian Messenger', 7),
(1049, 'Don Quixote', 999, 1072, 1605, '978-0-14-243723-0', 'Classic', '1st', 'Francisco de Robles', 6),
(1050, 'Ulysses', 999, 730, 1922, '978-0-19-953567-5', 'Modernist', '1st', 'Sylvia Beach', 4);


insert into borrows (id, user_id, book_id, borrowed_at, returned_at) values
(1001, 1002, 1034, '2025-04-01', '2025-04-07'),
(1002, 1002, 1015, '2025-04-01', '2025-04-21'),
(1003, 1002, 1001, '2025-04-01', '2025-05-01'),
(1004, 1002, 1023, '2025-04-01', '2025-05-06'),
(1005, 1002, 1050, '2025-04-01', '2025-05-15'),
(1006, 1003, 1003, '2025-04-01', '2025-05-20'),
(1007, 1003, 1034, '2025-05-01', '2025-05-08'),
(1008, 1003, 1014, '2025-05-01', '2025-05-17'),
(1009, 1003, 1034, '2025-05-08', '2025-05-22'),
(1010, 1004, 1050, '2025-04-08', '2025-05-23');

insert into borrows (id, user_id, book_id, borrowed_at) values
(1011, 1004, 1010, '2025-05-01'),
(1012, 1005, 1050, '2025-05-22'),
(1013, 1005, 1012, '2025-05-21'),
(1014, 1006, 1050, '2025-05-23');
