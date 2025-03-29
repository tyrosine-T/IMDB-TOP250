
CREATE TABLE Movies (
    movie_id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    image_url VARCHAR(255),
    content_rating VARCHAR(10),
    duration VARCHAR(50),
    release_year INT
);

CREATE TABLE Ratings (
    rating_id INT NOT NULL,
    movie_id VARCHAR(255),
    rating FLOAT,
    rating_count INT,
    PRIMARY KEY (rating_id),
    FOREIGN KEY (movie_id) REFERENCES Movies(movie_id)
);

CREATE TABLE Actors (
    actor_id INT NOT NULL,
    movie_id VARCHAR(255),
    actor VARCHAR(255),
    PRIMARY KEY (actor_id),
    FOREIGN KEY (movie_id) REFERENCES Movies(movie_id)
);

CREATE TABLE ProductionStatus (
    status_id INT NOT NULL,
    movie_id VARCHAR(255),
    status VARCHAR(255),
    PRIMARY KEY (status_id),
    FOREIGN KEY (movie_id) REFERENCES Movies(movie_id)
);

CREATE TABLE Genres (
    genre_id INT NOT NULL,
    movie_id VARCHAR(255),
    genre VARCHAR(255),
    PRIMARY KEY (genre_id),
    FOREIGN KEY (movie_id) REFERENCES Movies(movie_id)
);

create table directors (
    director_movie_id integer not null,
    movie_id varchar(255) references MOVIES(movie_id),
    director varchar(255)
)