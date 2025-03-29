package entity;

public class Genre {
    private Integer genre_id;
    private String movie_id;
    private String genre;

    public Genre() {};

    public Genre(Integer genre_id, String movie_id, String genre) {
        this.genre_id = genre_id;
        this.movie_id = movie_id;
        this.genre = genre;
    }

    public Genre(String movie_id, String genre) {
        this.movie_id = movie_id;
        this.genre = genre;
    }

    public Integer getGenre_id() { return genre_id; }
    public void setGenre_id(Integer genre_id) { this.genre_id = genre_id; }
    public String getMovie_id() { return movie_id; }
    public void setMovie_id(String movie_id) { this.movie_id = movie_id; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}
