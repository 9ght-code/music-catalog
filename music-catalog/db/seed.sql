-- Наполнение каталога тестовыми данными.
-- Рассчитан на схему с колонкой albums.artist_id (не artists_id!).
-- Явные id безопасны, потому что schema.sql каждый раз пересоздаёт таблицы
-- и последовательности начинаются с 1.

BEGIN;

-- Артисты
INSERT INTO artists (id, name) VALUES
    (1, 'Queen'),
    (2, 'Metallica'),
    (3, 'Daft Punk'),
    (4, 'Radiohead'),
    (5, 'ABBA');

-- Альбомы
INSERT INTO albums (id, title, year, artist_id) VALUES
    (1, 'A Night at the Opera', 1975, 1),
    (2, 'The Game',            1980, 1),
    (3, 'Master of Puppets',   1986, 2),
    (4, 'Discovery',           2001, 3),
    (5, 'Random Access Memories', 2013, 3),
    (6, 'OK Computer',         1997, 4),
    (7, 'Arrival',             1976, 5);

-- Треки
INSERT INTO tracks (id, title, duration_seconds, album_id) VALUES
    (1,  'Bohemian Rhapsody',        354, 1),
    (2,  'You''re My Best Friend',   172, 1),
    (3,  'Love of My Life',          219, 1),
    (4,  'Another One Bites the Dust', 215, 2),
    (5,  'Crazy Little Thing Called Love', 163, 2),
    (6,  'Battery',                  312, 3),
    (7,  'Master of Puppets',        516, 3),
    (8,  'Welcome Home (Sanitarium)', 387, 3),
    (9,  'One More Time',            320, 4),
    (10, 'Digital Love',             300, 4),
    (11, 'Harder, Better, Faster, Stronger', 225, 4),
    (12, 'Get Lucky',                369, 5),
    (13, 'Giorgio by Moroder',       548, 5),
    (14, 'Paranoid Android',         383, 6),
    (15, 'Karma Police',             263, 6),
    (16, 'No Surprises',             229, 6),
    (17, 'Dancing Queen',            234, 7),
    (18, 'Mamma Mia',                212, 7),
    (19, 'Take a Chance on Me',      243, 7);

-- Жанры
INSERT INTO genres (id, name) VALUES
    (1, 'Rock'),
    (2, 'Metal'),
    (3, 'Electronic'),
    (4, 'Pop'),
    (5, 'Alternative'),
    (6, 'Disco');

-- Связи трек-жанр (у многих треков по два жанра — зачем, ты уже знаешь)
INSERT INTO track_genres (track_id, genre_id) VALUES
    (1,  1), (1,  5),   -- Bohemian Rhapsody: Rock + Alternative
    (2,  1), (3,  1),
    (4,  1), (5,  1),
    (6,  2), (7,  2), (8,  2),
    (9,  3), (10, 3), (11, 3),
    (12, 3), (12, 6), (12, 4),  -- Get Lucky: Electronic + Disco + Pop
    (13, 3),
    (14, 5), (14, 1),  -- Paranoid Android: Alternative + Rock
    (15, 5), (16, 5),
    (17, 4), (17, 6),  -- Dancing Queen: Pop + Disco
    (18, 4), (19, 4);

COMMIT;
