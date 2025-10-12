INSERT INTO user_account (id, version, first_name, last_name, orientation)
VALUES (1, 0, 'Edward', 'Legaspi', 'MALE'),
       (2, 0, 'Bob', 'Smith', 'MALE'),
       (3, 0, 'Carol', 'Williams', 'FEMALE'),
       (4, 0, 'David', 'Brown', 'MALE'),
       (5, 0, 'Eve', 'Jones', 'FEMALE'),
       (6, 0, 'Frank', 'Garcia', 'MALE'),
       (7, 0, 'Grace', 'Miller', 'FEMALE'),
       (8, 0, 'Hank', 'Davis', 'MALE'),
       (9, 0, 'Ivy', 'Rodriguez', 'FEMALE'),
       (10, 0, 'Jack', 'Martinez', 'MALE'),
       (11, 0, 'Karen', 'Hernandez', 'FEMALE'),
       (12, 0, 'Leo', 'Lopez', 'MALE'),
       (13, 0, 'Mia', 'Gonzalez', 'FEMALE'),
       (14, 0, 'Noah', 'Wilson', 'MALE'),
       (15, 0, 'Olivia', 'Anderson', 'FEMALE'),
       (16, 0, 'Paul', 'Thomas', 'MALE'),
       (17, 0, 'Quinn', 'Taylor', 'MALE'),
       (18, 0, 'Ruby', 'Moore', 'FEMALE'),
       (19, 0, 'Sam', 'Jackson', 'MALE'),
       (20, 0, 'Tina', 'Martin', 'FEMALE'),
       (21, 0, 'Alice', 'Johnson', 'FEMALE')
;

INSERT INTO user_account (id, version, first_name, last_name, birth_date, orientation)
VALUES (22, 0, 'Larry', 'Johnson', '1984-10-13 14:0:00', 'MALE'),
       (23, 0, 'Edward', 'CzetsuyaTech', '1984-10-13 14:0:00', 'MALE');

INSERT INTO user_hobby (user_id, hobby)
VALUES (1, 'Chess'),
       (20, 'Manga'),
       (23, 'Anime');

INSERT INTO user_address (user_id, city, country)
VALUES (1, 'Victoria', 'PH'),
       (23, 'Los Banos', 'PH'),
       (21, 'Tokyo', 'JP');
