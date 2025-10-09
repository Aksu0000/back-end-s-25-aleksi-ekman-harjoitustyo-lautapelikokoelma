-- Varmuuden vuoksi poistetaan vanhat taulut, jos ne on jo olemassa
DROP TABLE IF EXISTS application_user CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS game CASCADE;
DROP TABLE IF EXISTS game_genre CASCADE;

-- 1. application_user
CREATE TABLE application_user (
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    application_password VARCHAR(255) NOT NULL,
    application_role VARCHAR(50) NOT NULL
);

-- 2. Genre
CREATE TABLE genre (
    id BIGSERIAL PRIMARY KEY,
    genre_name VARCHAR(100) NOT NULL UNIQUE
);

-- 3. Game

CREATE TABLE game (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publication_year INT NOT NULL,
    min_players INT,
    max_players INT,
    image_url VARCHAR(1000),
    game_description TEXT,
    source VARCHAR(50),
    added_by VARCHAR(50),
    CONSTRAINT fk_application_user FOREIGN KEY (added_by) REFERENCES application_user(username) ON DELETE SET NULL
);

-- 4. Game_Genre (ManyToMany)
CREATE TABLE game_genre (
    game_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY(game_id, genre_id),
    CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE,
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);

-- Lisätään esimerkkidataa

-- Käyttäjät
INSERT INTO application_user (firstname, lastname, username, application_password, application_role) VALUES
('Admin', 'User', 'admin', '$2a$10$FWjcgEeZdWUGgjyJZmxQNurBxhED3WfIdem7MPqxnvbxEJibSYFdu', 'ADMIN'),
('User', 'User', 'user',  '$2a$10$G5mujdmwWh.jil9X.sqV3uBGJJ9Lz/vn7Emyv16KnpykYBjASGzFu', 'USER'),
('Aksu', 'Ekman', 'aksu', '$2a$10$tJa62m.vEuDntkh10I50le3FPogit6Xx7CAqT6oh062K2LU3IwZja', 'ADMIN');

-- Genre
INSERT INTO genre (genre_name) VALUES
('Strategia'), 
('Perhepeli'),
('Seikkailu');

-- Pelit
INSERT INTO game (title, publication_year, min_players, max_players, image_url, game_description, source, added_by) VALUES
('Catan', 1995, 3, 4, 'https://example.com/catan.jpg', 'Kuuluisa kauppapeli.', 'Local', 'admin'),
('Carcassonne', 2000, 2, 5, 'https://example.com/carcassonne.jpg', 'Laattojen asettelu ja kaupungin rakentaminen.', 'Local', 'admin');

-- Game_Genre yhteys
INSERT INTO game_genre (game_id, genre_id) VALUES
(1, 1), -- Catan - Strategia
(2, 2); -- Carcassonne - Perhepeli