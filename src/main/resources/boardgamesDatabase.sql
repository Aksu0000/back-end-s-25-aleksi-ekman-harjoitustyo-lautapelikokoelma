-- Poistetaan vanhat taulut varmuuden vuoksi
DROP TABLE IF EXISTS application_user CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS game CASCADE;

-- 1. Käyttäjät
CREATE TABLE application_user (
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    application_password VARCHAR(255) NOT NULL,
    application_role VARCHAR(50) NOT NULL
);

-- 2. Genre (Category)
CREATE TABLE genre (
    id BIGSERIAL PRIMARY KEY,
    genre_name VARCHAR(100) NOT NULL UNIQUE
);

-- 3. Game (Boardgame)
CREATE TABLE game (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publication_year INT NOT NULL,
    min_players INT NOT NULL,
    max_players INT NOT NULL,
    image_url VARCHAR(1000),
    game_description TEXT,
    source VARCHAR(50),
    added_by VARCHAR(50),
    genre_id BIGINT NOT NULL,
    CONSTRAINT fk_application_user FOREIGN KEY (added_by) REFERENCES application_user(username) ON DELETE SET NULL,
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);

-- Esimerkkidata

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
INSERT INTO game (title, publication_year, min_players, max_players, image_url, game_description, source, added_by, genre_id) VALUES
('Catan', 1995, 3, 4, 'https://cf.geekdo-images.com/0XODRpReiZBFUffEcqT5-Q__imagepage/img/enC7UTvCAnb6j1Uazvh0OBQjvxw=/fit-in/900x600/filters:no_upscale():strip_icc()/pic9156909.png', 'Kuuluisa kauppapeli.', 'Local', 'admin', 1),
('Carcassonne', 2000, 2, 5, 'https://cf.geekdo-images.com/peUgu3A20LRmAXAMyDQfpQ__imagepage/img/Ywa8dN-gGRhgo_uVSR_mMGoRQMM=/fit-in/900x600/filters:no_upscale():strip_icc()/pic8621446.jpg', 'Laattojen asettelu ja kaupungin rakentaminen.', 'Local', 'admin', 2);
('Terraforming Mars', 2016, 1, 5, 'https://cf.geekdo-images.com/wg9oOLcsKvDesSUdZQ4rxw__imagepage/img/FS1RE8Ue6nk1pNbPI3l-OSapQGc=/fit-in/900x600/filters:no_upscale():strip_icc()/pic3536616.jpg', 'Kilpailu Marsin muuttamisesta elinkelpoiseksi planeetaksi.', 'Local', 'admin', 1),
('Gloomhaven', 2017, 1, 4, 'https://cf.geekdo-images.com/sZYp_3BTDGjh2unaZfZmuA__imagepage@2x/img/adAzZs3Diya9XQpAvJ49kHD2YWs=/fit-in/1800x1200/filters:strip_icc()/pic2437871.jpg', 'Seikkailupeli, jossa taistellaan hirviöitä vastaan ja kehitetään hahmoa.', 'Local', 'admin', 3),
('Azul', 2017, 2, 4, 'https://cf.geekdo-images.com/aPSHJO0d0XOpQR5X-wJonw__imagepage/img/q4uWd2nXGeEkKDR8Cc3NhXG9PEU=/fit-in/900x600/filters:no_upscale():strip_icc()/pic6973671.png', 'Laattojen asettelu kauniilla kuvioilla. Helppo oppia, vaikea hallita.', 'Local', 'admin', 2),
('Pandemic', 2008, 2, 4, 'https://cf.geekdo-images.com/S3ybV1LAp-8SnHIXLLjVqA__imagepage/img/kIBu-2Ljb_ml5n-S8uIbE6ehGFc=/fit-in/900x600/filters:no_upscale():strip_icc()/pic1534148.jpg', 'Yhteistyöpeli maailmanlaajuisen epidemian pysäyttämiseksi.', 'Local', 'admin', 3),
('Ticket to Ride', 2004, 2, 5, 'https://cf.geekdo-images.com/kdWYkW-7AqG63HhqPL6ekA__imagepage/img/AWsdGNNSuI78BaCPAVQpjrUneKY=/fit-in/900x600/filters:no_upscale():strip_icc()/pic8937637.jpg', 'Junareittien rakentaminen ja kaupunkien yhdistäminen.', 'Local', 'admin', 2),
('7 Wonders', 2010, 2, 7, 'https://cf.geekdo-images.com/35h9Za_JvMMMtx_92kT0Jg__imagepage/img/WKlTys0Dc3F6x9r05Fwyvs82tz4=/fit-in/900x600/filters:no_upscale():strip_icc()/pic7149798.jpg', 'Sivilisaatioiden rakentamista korttipelimekaniikalla.', 'Local', 'admin', 1),
('Splendor', 2014, 2, 4, 'https://cf.geekdo-images.com/vNFe4JkhKAERzi4T0Ntwpw__imagepage@2x/img/o5_euaQYSaPxlu9L4i11AwVU4aQ=/fit-in/1800x1200/filters:strip_icc()/pic8234167.png', 'Jalokivien keräilyä ja kauppiaiden kilpailua korttien avulla.', 'Local', 'admin', 2),
('Dominion', 2008, 2, 4, 'https://cf.geekdo-images.com/j6iQpZ4XkemZP07HNCODBA__imagepage@2x/img/8nN7tVoS7YQbIWIIm6uCIGEuKgI=/fit-in/1800x1200/filters:strip_icc()/pic394356.jpg', 'Korttipeli oman pakan rakentamisesta ja strategisista valinnoista.', 'Local', 'admin', 1);
