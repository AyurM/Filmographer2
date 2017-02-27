package ru.ayurmar.filmographer.model;

/**
 * Created by Ayur on 16.02.2017.
 */

public class Movie {
    //наименования ключей для JSON-формата
    public static final String KEY_MOVIES = "movies";
    public static final String KEY_RESULTS = "results";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ID = "id";
    public static final String KEY_IMDB_LINK = "imdb_link";
    public static final String KEY_BACKDROP_PATH = "backdrop_path";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_RELEASE_DATE = "release_date";
    public static final String KEY_IMDB_RATING = "imdbRating";
    public static final String KEY_ACTORS = "actors";
    public static final String KEY_IMDB_INFO_LOADED = "imdbInfoLoaded";
    public static final String KEY_GENRES = "genres";
    public static final String KEY_GENRES_IDS = "genre_ids";

    private String mId = "";
    private String mTitle = "";
    private String mImdbLink = "";
    private String mBackdropPath = "";
    private String mReleaseDate = "";
    private String mOverview = "";
    private String mImdbRating = "";
    private String mActors = "";
    private String mGenres = "";
    private String mStatus = "";
    private boolean mImdbInfoLoaded;

    public Movie(){
    }

    @Override
    public String toString(){
        return KEY_ID + ": " + getId() + "; " +
                KEY_TITLE + ": " + getTitle() + "; " +
                KEY_OVERVIEW + ": " + getOverview() + "; " +
                KEY_RELEASE_DATE + ": " + getReleaseDate() + "; " +
                KEY_IMDB_LINK + ": " + getImdbLink() + "; " +
                KEY_BACKDROP_PATH + ": " + getBackdropPath() + ";";
    }

    @Override
    public boolean equals(Object movieInfo){
        //сравнение фильмов по их id
        if((movieInfo == null) || (movieInfo.getClass() != this.getClass())) {
            return false;
        }
        return !this.mId.equals("") && this.mId.equals(((Movie) movieInfo).getId());
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImdbLink() {
        return mImdbLink;
    }

    public void setImdbLink(String imdbLink) {
        mImdbLink = imdbLink;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getImdbRating() {
        return mImdbRating;
    }

    public void setImdbRating(String imdbRating) {
        mImdbRating = imdbRating;
    }

    public String getActors() {
        return mActors;
    }

    public void setActors(String actors) {
        mActors = actors;
    }

    public String getGenres() {
        return mGenres;
    }

    public void setGenres(String genres) {
        mGenres = genres;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public boolean isImdbInfoLoaded() {
        return mImdbInfoLoaded;
    }

    public void setImdbInfoLoaded(boolean imdbInfoLoaded) {
        mImdbInfoLoaded = imdbInfoLoaded;
    }
}
