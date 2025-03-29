package entity;

public class Director {
    private Integer director_movie_id;
    private String movie_id;
    private String name;

    public Director() {

    }

    public Director(Integer director_movie_id, String movie_id, String name) {
        this.director_movie_id = director_movie_id;
        this.movie_id = movie_id;
        this.name = name;
    }

    public Director(String movie_id, String name) {
        this.movie_id = movie_id;
        this.name = name;
    }


    public Integer getDirector_movie_id() { return director_movie_id; }

    public void setDirector_movie_id(Integer set_id) {
        this.director_movie_id = set_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String set_name) {
        this.name = set_name;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String set_id) {
        this.movie_id = set_id;
    }
}