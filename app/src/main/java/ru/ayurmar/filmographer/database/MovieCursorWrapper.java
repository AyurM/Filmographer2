package ru.ayurmar.filmographer.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import ru.ayurmar.filmographer.model.Movie;
import ru.ayurmar.filmographer.database.MovieDbSchema.MovieTable;

public class MovieCursorWrapper extends CursorWrapper {

    public MovieCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Movie getMovie() {
        String tmdbid = getString(getColumnIndex(MovieTable.Cols.TMDBID));
        String title = getString(getColumnIndex(MovieTable.Cols.TITLE));
        String imdb_link = getString(getColumnIndex(MovieTable.Cols.IMDB_ID));
        String backdrop_path = getString(getColumnIndex(MovieTable.Cols.BACKDROP_PATH));
        String release_date = getString(getColumnIndex(MovieTable.Cols.RELEASE_DATE));
        String overview = getString(getColumnIndex(MovieTable.Cols.OVERVIEW));
        String imdb_rating = getString(getColumnIndex(MovieTable.Cols.IMDB_RATING));
        String actors = getString(getColumnIndex(MovieTable.Cols.ACTORS));
        String genres = getString(getColumnIndex(MovieTable.Cols.GENRES));
        String info_loaded = getString(getColumnIndex(MovieTable.Cols.INFO_LOADED));
        String status = getString(getColumnIndex(MovieTable.Cols.STATUS));

        Movie movie = new Movie();
        movie.setId(tmdbid);
        movie.setTitle(title);
        movie.setImdbId(imdb_link);
        movie.setBackdropPath(backdrop_path);
        movie.setReleaseDate(release_date);
        movie.setOverview(overview);
        movie.setImdbRating(imdb_rating);
        movie.setActors(actors);
        movie.setGenres(genres);
        movie.setImdbInfoLoaded(info_loaded.equals("true"));
        movie.setStatus(status);

        return movie;
    }
}
