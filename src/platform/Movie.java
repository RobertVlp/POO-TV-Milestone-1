package platform;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class Movie {
    private String name;
    private Integer year;
    private Integer duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private Integer numLikes;
    private Double rating;
    private Integer numRatings;

    public Movie() {
        this.setNumLikes(0);
        this.setNumRatings(0);
        this.setRating(0D);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public Integer getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final Integer numLikes) {
        this.numLikes = numLikes;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Integer getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final Integer numRatings) {
        this.numRatings = numRatings;
    }
}
