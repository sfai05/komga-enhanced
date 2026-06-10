CREATE TABLE IF NOT EXISTS FOLLOW (
    ID              varchar       NOT NULL PRIMARY KEY,
    LIBRARY_ID      varchar       NOT NULL,
    URL             varchar(2048) NOT NULL,
    TITLE           varchar(512),
    ENABLED         boolean       NOT NULL DEFAULT 1,
    CHAPTER_FROM    real,
    CHAPTER_TO      real,
    ADDED_AT        datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LAST_CHECKED_AT datetime,
    UNIQUE (LIBRARY_ID, URL)
);
CREATE INDEX IF NOT EXISTS idx_follow_library ON FOLLOW (LIBRARY_ID);
