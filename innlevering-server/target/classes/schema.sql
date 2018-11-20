CREATE TABLE IF NOT EXISTS Talks (
    talkid SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    title TEXT NOT NULL,
    topic TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Timeslots (
    timeslotid SERIAL PRIMARY KEY,
    talkid INT,
    FOREIGN KEY talkid REFERENCES Talks(talkid),
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS Days (
    dayid SERIAL PRIMARY KEY,
    timeslotid INT,
    FOREIGN KEY timeslotid REFERENCES Timeslots(timeslotid),
    date_of_conference DATE NOT NULL,
    id SERIAL PRIMARY KEY
    
);

CREATE TABLE IF NOT EXISTS Tracks (
    trackid SERIAL PRIMARY KEY,
    timeslotid INT,
    FOREIGN KEY timeslotid REFERENCES Timeslots(timeslotid),
    room TEXT NOT NULL,
    
);

CREATE TABLE IF NOT EXISTS Conference (
    dayid INT,
    trackid INT,
    title TEXT NOT NULL,
    FOREIGN KEY dayid REFERENCES Days(dayid),
    FOREIGN KEY trackid REFERENCES Tracks(trackid),
    PRIMARY KEY (dayid, trackid)
);