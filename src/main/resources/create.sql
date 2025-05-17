DROP TABLE IF EXISTS music_bands CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TYPE  IF EXISTS music_genre;
DROP SEQUENCE IF EXISTS users_id_seq;
DROP SEQUENCE IF EXISTS musicband_id_seq;

CREATE TYPE music_genre AS ENUM (
    'ROCK',
    'JAZZ',
    'PUNK_ROCK'
);

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE users (
                       id             BIGINT PRIMARY KEY
                                        DEFAULT nextval('users_id_seq')
                                        CHECK (id > 0),
                       login          TEXT      UNIQUE NOT NULL,
                       password_hash  TEXT      NOT NULL,
                       created_at     TIMESTAMP NOT NULL DEFAULT now()
);

CREATE SEQUENCE musicband_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE music_bands (
        id                       INTEGER NOT NULL PRIMARY KEY
                                    DEFAULT nextval('musicband_id_seq')
                                    CHECK (id > 0),
        name                     TEXT NOT NULL,
        coord_x                  INTEGER NOT NULL,
        coord_y                  BIGINT NOT NULL,
        creation_date            TIMESTAMP NOT NULL DEFAULT now(),
        number_of_participants   BIGINT NOT NULL
                                    CHECK (number_of_participants > 0),
        genre                    music_genre,
        studio_name              TEXT,
        created_by               TEXT NOT NULL
                                    REFERENCES users(login)
);

CREATE INDEX idx_music_bands_created_by ON music_bands(created_by);
