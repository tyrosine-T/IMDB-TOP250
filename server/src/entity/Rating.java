package entity;

public class Rating {
    private Integer rating_id;
    private String movie_id;
    private int rating;
    private Integer rating_count;

    public Rating() {}

    public Rating(Integer rationg_id, String movie_id, int rating, Integer rating_count) {
        this.rating_id = rationg_id;
        this.movie_id = movie_id;
        this.rating = rating;
        this.rating_count = rating_count;
    }

    public Rating( String movie_id, int rating) {
        this.movie_id = movie_id;
        this.rating = rating;
    }

    public Integer getRating_id() {return rating_id;}
    public void setRating_id(Integer rating_id) {this.rating_id = rating_id;}
    public String getMovie_id() {return movie_id;}
    public void setMovie_id(String movie_id) {this.movie_id = movie_id;}
    public int getRating() {return rating;}
    public void setRating(int rating) {this.rating = rating;}
    public Integer getRating_count() {return rating_count;}
    public void setRating_count(Integer rating_count) {this.rating_count = rating_count;}

}

