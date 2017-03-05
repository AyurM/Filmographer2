package ru.ayurmar.filmographer.database;

import ru.ayurmar.filmographer.model.Movie;

/**
 * Структура SQLite-БД, Аюр М., 27.02.2017.
 */

public class MovieDbSchema {
    public static final class MovieTable {
        public static final String NAME = "movies";

        public static final class Cols {
            public static final String TMDBID = "tmdbid";
            public static final String TITLE = Movie.KEY_TITLE;
            public static final String IMDB_ID = Movie.KEY_IMDB_ID;
            public static final String BACKDROP_PATH = Movie.KEY_BACKDROP_PATH;
            public static final String RELEASE_DATE = Movie.KEY_RELEASE_DATE;
            public static final String OVERVIEW = Movie.KEY_OVERVIEW;
            public static final String IMDB_RATING = Movie.KEY_IMDB_RATING;
            public static final String ACTORS = Movie.KEY_ACTORS;
            public static final String GENRES = Movie.KEY_GENRES;
            public static final String INFO_LOADED = Movie.KEY_IMDB_INFO_LOADED;
            public static final String STATUS = "status";
        }
    }
}
