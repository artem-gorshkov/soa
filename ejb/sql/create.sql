CREATE TABLE COORDINATES
(
    ID SERIAL PRIMARY KEY,
    X  FLOAT NOT NULL CHECK (X > -331),
    Y  FLOAT NOT NULL CHECK (Y > -320)
);

CREATE TABLE LABEL
(
    NAME VARCHAR(256) PRIMARY KEY
);

CREATE TABLE MUSIC_BAND
(
    ID                     SERIAL PRIMARY KEY CHECK (ID > 0),
    NAME                   VARCHAR(256)                                                            NOT NULL CHECK (CHAR_LENGTH(NAME) > 0),
    X  FLOAT NOT NULL CHECK (X > -331),
    Y  FLOAT NOT NULL CHECK (Y > -320),
    CREATION_DATE          DATE                                                                    NOT NULL,
    NUMBER_OF_PARTICIPANTS INTEGER CHECK (NUMBER_OF_PARTICIPANTS > 0)                              NOT NULL,
    ALBUMS_COUNT           BIGINT CHECK (ALBUMS_COUNT > 0),
    GENRE                  VARCHAR(256),
    LABEL_NAME VARCHAR(256)
);
