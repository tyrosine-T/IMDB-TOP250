package entity;

public class Actor {
    private Integer actor_id;
    private String movie_id;
    private String name;

    public Actor() {

    }

    public Actor(Integer actor_id, String movie_id, String name) {
        this.actor_id = actor_id;
        this.movie_id = movie_id;
        this.name = name;
    }

    public Actor(String movie_id, String name) {
        this.movie_id = movie_id;
        this.name = name;
    }


    public Integer getActor_id() {
        return actor_id;
    }

    public void setActor_id(Integer set_id) {
        this.actor_id = set_id;
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