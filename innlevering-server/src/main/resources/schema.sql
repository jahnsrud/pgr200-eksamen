CREATE TABLE IF NOT EXISTS Talks (
    id SERIAL PRIMARY KEY,
    TITLE TEXT NOT NULL,
    DESCRIPTION TEXT NOT NULL,
    TOPIC TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Conferences (
    id SERIAL PRIMARY KEY,
    TITLE TEXT NOT NULL,
    DESCRIPTION TEXT NOT NULL,
    ORGANIZED_BY TEXT NOT NULL
);