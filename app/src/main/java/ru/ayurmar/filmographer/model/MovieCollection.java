package ru.ayurmar.filmographer.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.ayurmar.filmographer.database.MovieBaseHelper;
import ru.ayurmar.filmographer.database.MovieCursorWrapper;
import ru.ayurmar.filmographer.database.MovieDbSchema.MovieTable;

public class MovieCollection {
    private static MovieCollection sMovieCollection;
    private SQLiteDatabase mDatabase;

    public static MovieCollection get(Context context){
        if(sMovieCollection == null){
            sMovieCollection = new MovieCollection(context);
        }
        return sMovieCollection;
    }

    private MovieCollection(Context context){
        mDatabase = new MovieBaseHelper(context)
                .getWritableDatabase();
    }

    private static ContentValues getValues(Movie movie){
        ContentValues values = new ContentValues();
        values.put(MovieTable.Cols.TMDBID, movie.getId());
        values.put(MovieTable.Cols.TITLE, movie.getTitle());
        values.put(MovieTable.Cols.IMDB_ID, movie.getImdbId());
        values.put(MovieTable.Cols.BACKDROP_PATH, movie.getBackdropPath());
        values.put(MovieTable.Cols.RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieTable.Cols.OVERVIEW, movie.getOverview());
        values.put(MovieTable.Cols.IMDB_RATING, movie.getImdbRating());
        values.put(MovieTable.Cols.ACTORS, movie.getActors());
        values.put(MovieTable.Cols.GENRES, movie.getGenres());
        values.put(MovieTable.Cols.INFO_LOADED, String.valueOf(movie.isImdbInfoLoaded()));
        values.put(MovieTable.Cols.STATUS, movie.getStatus());
        return values;
    }

    private MovieCursorWrapper queryMovies(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MovieTable.NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new MovieCursorWrapper(cursor);
    }

    public List<Movie> getMovies(String status){
        List<Movie> movies = new ArrayList<>();
        MovieCursorWrapper cursorWrapper = queryMovies(MovieTable.Cols.STATUS + " = ?",
                new String[] {status});

        cursorWrapper.moveToFirst();
        while (!cursorWrapper.isAfterLast()) {
            movies.add(cursorWrapper.getMovie());
            cursorWrapper.moveToNext();
        }
        cursorWrapper.close();
        return movies;
    }

    public void add(Movie movie){
        ContentValues values = getValues(movie);
        mDatabase.insert(MovieTable.NAME, null, values);
    }

    public void addAll(List<Movie> movieList){
        for(Movie movie : movieList){
            add(movie);
        }
    }

    public void delete(Movie movie){
        mDatabase.delete(MovieTable.NAME,
                MovieTable.Cols.TMDBID + " = ?",
                new String[] { movie.getId() });
    }

    public void update(Movie movie){
        ContentValues values = getValues(movie);
        mDatabase.update(MovieTable.NAME, values,
                MovieTable.Cols.TMDBID + " = ?",
                new String[] { movie.getId() });
    }

    public void clear(String status){
        mDatabase.delete(MovieTable.NAME,
                MovieTable.Cols.STATUS + " = ?",
                new String[] { status });
    }
}
