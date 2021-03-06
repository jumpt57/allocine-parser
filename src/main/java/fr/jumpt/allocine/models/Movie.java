package fr.jumpt.allocine.models;

import org.mongodb.morphia.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author Julien
 */
@Entity(value = "movies", noClassnameStored = true)
@Indexes(
        @Index(value = "date", fields = {@Field("releaseDate"), @Field("reReleaseDate")})
)
public class Movie {

    @Id
    private Integer id;

    private String title;

    private Date releaseDate;

    private Date reReleaseDate;

    private String length;

    private List<String> directors;

    private List<String> actors;

    private List<String> genres;

    private List<String> nationalities;

    private String synopsis;

    private String ageLimit;

    private String poster;

    private String type;

    private Integer year;

    public Movie() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<String> nationalities) {
        this.nationalities = nationalities;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getReReleaseDate() {
        return reReleaseDate;
    }

    public void setReReleaseDate(Date reReleaseDate) {
        this.reReleaseDate = reReleaseDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return id + " " + title + " " + year;
    }

}
