DROP TABLE IF EXISTS track_genres, tracks, albums, artists, genres;

create table artists (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

create TABLE albums (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    year int NOT NULL,
    artist_id INT NOT NULL REFERENCES artists(id) ON DELETE CASCADE 
    -- CASCADE ведь при удалении артиста нам нужно удалить всю инфу о нем каскадно
);

CREATE TABLE tracks (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    duration_seconds int,
    album_id INT NOT NULL REFERENCES albums(id) ON DELETE CASCADE
);

create table genres (
    id SERIAL primary key,
    name text not null unique
);

create table track_genres (
    track_id int not null REFERENCES tracks(id) ON DELETE CASCADE,
    genre_id int not null REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (track_id, genre_id)
);



-- индексы могут быть нужны когда мы будем выполнять фильтрацию по особо частым колонкам по типу album_id, artist_id
-- у трека могут быть несколько жанров, поэтому отдельная таблица