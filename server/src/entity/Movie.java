package entity;

public class Movie {
    private String movie_id;
    private String title;
    private String description;
    private String image_url;
    private String content_rating;
    private String duration;
    private String release_year;

    private String director_name;
    private String actor_name;
    private int rating;
    private String genre;

    public Movie() {}

    public Movie(String movie_id, String title, String description, String image_url,
                 String content_rating, String duration, String release_year,
                 String director_name, String actor_name, int rating, String genre) {
        this.movie_id = movie_id;
        this.title = title;
        this.description = description;
        this.image_url = image_url;

        this.content_rating = content_rating;
        this.duration = duration;
        this.release_year = release_year;
        this.director_name = director_name;
        this.actor_name = actor_name;

        this.rating = rating;
        this.genre = genre;
    }

    public String getMovie_id() {return movie_id;}
    public void setMovie_id(String movie_id) {this.movie_id = movie_id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getImage_url() {return image_url;}
    public void setImage_url(String image_url) {this.image_url = image_url;}
    public String getContent_rating() {return content_rating;}
    public void setContent_rating(String content_rating) {this.content_rating = content_rating;}
    public String getDuration() {return duration;}
    public void setDuration(String duration) {this.duration = duration;}
    public String getRelease_year() {return release_year;}
    public void setRelease_year(String release_year) {this.release_year = release_year;}
    public String getDirector_name() {return director_name;}
    public void setDirector_name(String director_name) {this.director_name = director_name;}
    public String getActor_name() {return actor_name;}
    public void setActor_name(String actor_name) {this.actor_name = actor_name;}
    public int getRating() {return rating;}
    public void setRating(int rating) {this.rating = rating;}
    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}

}
