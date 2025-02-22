-- CREATE DATABASE greatreads;

USE greatreads;

DROP TABLE IF EXISTS `user_books`;
DROP TABLE IF EXISTS `reviews`;
DROP TABLE IF EXISTS `books`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `genres`;
DROP TABLE IF EXISTS `roles`;

-- ROLES table
CREATE TABLE `roles`(
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `name` VARCHAR(50) NOT NULL UNIQUE,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- GENRES table
CREATE TABLE `genres` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `name` VARCHAR(75) NOT NULL UNIQUE,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- USERS Table
CREATE TABLE `users`(
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `email` VARCHAR(255) UNIQUE NOT NULL,
                        `password` VARCHAR(255) NOT NULL,
                        `first_name` VARCHAR(255) NOT NULL,
                        `last_name` VARCHAR(255) NOT NULL,
                        `role_id` INTEGER NOT NULL,
                        `is_blocked` BOOLEAN DEFAULT FALSE,
                        `avatar` VARCHAR(255),
                        `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY(`id`),
                        FOREIGN KEY(`role_id`) REFERENCES roles(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- BOOKS Table
CREATE TABLE `books`(
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `title` VARCHAR(255) NOT NULL,
                        `description` TEXT,
                        `genre_id` INTEGER NOT NULL,
                        `ISBN` VARCHAR(50) UNIQUE NOT NULL,
                        `user_id` INTEGER NOT NULL,
                        `published_date` DATE NOT NULL,
                        `url_link` VARCHAR(255),
                        `is_approved` BOOLEAN DEFAULT FALSE,
                        `page_cover` VARCHAR(255),
                        `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY(`id`),
                        FOREIGN KEY(`user_id`) REFERENCES users(`id`),
                        FOREIGN KEY(`genre_id`) REFERENCES genres(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- REVIEWS Table
CREATE TABLE `reviews` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `rating` INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                           `comment` TEXT,
                           `published_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                           `book_id` INT NOT NULL,
                           `user_id` INT NOT NULL,
                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY(`id`),
                           FOREIGN KEY (`book_id`) REFERENCES books(`id`),
                           FOREIGN KEY (`user_id`) REFERENCES users(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- USER_BOOKS table
CREATE TABLE `user_books`(
                             `id` INT NOT NULL AUTO_INCREMENT,
                             `user_id` INT NOT NULL,
                             `book_id` INT NOT NULL,
                             `is_read` BOOLEAN DEFAULT FALSE,
                             `is_wishlist` BOOLEAN DEFAULT FALSE,
                             `read_date` DATETIME,
                             `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                             `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY(`id`),
                             FOREIGN KEY (`book_id`) REFERENCES books(`id`),
                             FOREIGN KEY (`user_id`) REFERENCES users(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insert initial roles
INSERT INTO `roles`(id, name) VALUES
                                  (1, 'ROLE_ADMIN'),
                                  (2, 'ROLE_AUTHOR'),
                                  (3, 'ROLE_READER');

-- Insert initial genres
INSERT INTO `genres`(name) VALUES
                               ('Fantasy'),
                               ('Science Fiction'),
                               ('Romance'),
                               ('Biography'),
                               ('Classics'),
                               ('Comic Book'),
                               ('Science');

-- Insert initial users
INSERT INTO `users`(email, password, first_name, last_name, role_id, is_blocked, avatar) VALUES
                                                                                             ('reader@mail.com', 'hashPassword3', 'Barbara', 'Oakley', 2, FALSE, NULL),
                                                                                             ('reader2@mail.com', 'hashPassword3', 'Aldous', 'Huxley', 2, FALSE, NULL),
                                                                                             ('reader3@mail.com', 'hashPassword3', 'Jules', 'Verne', 2, FALSE, NULL),
                                                                                             ('reader4@mail.com', 'hashPassword3', 'Jamie', 'Oliver', 2, FALSE, NULL),
                                                                                             ('reader5@mail.com', 'hashPassword3', 'George', 'Orwell', 2, FALSE, NULL),
                                                                                             ('reader6@mail.com', 'hashPassword3', 'Ioan', 'Slavici', 2, FALSE, NULL);


-- Insert initial books
INSERT INTO `books`(title, description, genre_id, ISBN, user_id, published_date, url_link, is_approved, page_cover) VALUES
                                                                                                                        ('A Mind for Numbers', 'In A Mind for Numbers, Dr. Oakley lets us in on the secrets to effectively learning math and science—secrets that even dedicated and successful students wish they’d known earlier.', 7, '0593419057', 1, '2014-07-31 00:00:00', 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf', TRUE, 'https://m.media-amazon.com/images/I/81xk6jafTJL._SY466_.jpg'),
                                                                                                                        ('Island', 'In Island, his last novel, Huxley transports us to a Pacific island where, for 120 years, an ideal society has flourished.', 5, '0061561797', 2, '1962-01-01 00:00:00', 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf', TRUE, 'https://m.media-amazon.com/images/I/71zv1NFS4qL._SY466_.jpg'),
                                                                                                                        ('20,000 Thousand Leagues Under the Sea', 'Jules Verne was remarkably successful in foretelling the wonders science held for the future. This, his most famous novel, earned him the title of "Father of Science Fiction."', 2, '9781433213526', 3, '1869-01-01 00:00:00', 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf', TRUE, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1348014023i/3185559.jpg'),
                                                                                                                        ('Everyday Super Food', 'No matter how busy you are, you’ll find that healthy eating the Jamie way is both straightforward and achievable, making it super easy to choose exactly the kind of meals that suit you.', 7, '9780718181239', 4, '2015-08-27 00:00:00', 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf', TRUE, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1443019551i/25648087.jpg'),
                                                                                                                        ('Animal Farm', 'Today it is devastatingly clear that wherever and whenever freedom is attacked, under whatever banner, the cutting clarity and savage comedy of George Orwell’s masterpiece have a meaning and message still ferociously fresh.', 1, '9780718181238', 5, '1945-08-17 00:00:00', 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf', TRUE, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1740048031i/170448.jpg'),
                                                                                                                        ('Moara cu Noroc', 'Alcătuită din 17 capitole, nuvela are un subiect concentrat, cu deschideri bogate. Nuvela debutează cu un precept moral izvorât din înţelepciunea bătrânească rostit de mama-soacră, ce are în nuvelă rolul corului din tragedia antică greacă: „- Omul să fie mulţumit cu sărăcia sa, căci, dacă-i vorba, nu bogăţia, ci liniştea colibei tale te face fericit".', 5, '9780718181237', 6, '1881-01-01 00:00:00', 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf', TRUE, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1390497510i/1711424.jpg');

-- Insert initial user_books relationships
INSERT INTO `user_books`(user_id, book_id, is_read, is_wishlist, read_date) VALUES
                                                                                (3, 2, TRUE, FALSE, '2024-1-19'),
                                                                                (3, 1, FALSE, TRUE, NULL),
                                                                                (3, 3, FALSE, TRUE, NULL);

-- Insert initial reviews
INSERT INTO `reviews`(user_id, book_id, rating, comment) VALUES
                                                             (3, 2, 5, 'Excellent!'),
                                                             (3, 2, 1, 'Not great, not terrible.'),
                                                             (3, 2, 4, 'Very good.'),
                                                             (3, 2, 3, 'Decent');