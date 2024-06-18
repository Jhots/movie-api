ALTER TABLE MOVIE
    ADD (
        MOVIE_SYNOPSIS VARCHAR2(300),
        MOVIE_CATEGORY VARCHAR2(25),
        MOVIE_YEAR NUMBER(4, 0),
        MOVIE_DIRECTOR VARCHAR2(50)
        );

COMMENT
    ON COLUMN MOVIE.MOVIE_SYNOPSIS IS '[NOT_SECURITY_APPLY] Field used to store the synopsis of the movie.';
COMMENT
    ON COLUMN MOVIE.MOVIE_CATEGORY IS '[NOT_SECURITY_APPLY] Field used to store the category of the movie.';
COMMENT
    ON COLUMN MOVIE.MOVIE_YEAR IS '[NOT_SECURITY_APPLY] Field used to store the release year of the movie.';
COMMENT
    ON COLUMN MOVIE.MOVIE_DIRECTOR IS '[NOT_SECURITY_APPLY] Field used to store the director of the movie.';
