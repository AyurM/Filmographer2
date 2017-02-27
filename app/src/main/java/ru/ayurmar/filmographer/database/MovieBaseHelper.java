package ru.ayurmar.filmographer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.ayurmar.filmographer.database.MovieDbSchema.MovieTable;

/**
 * Создание SQLite-БД, Аюр М., 27.02.2017.
 */

public class MovieBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "movieBase.db";

    public MovieBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MovieTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                MovieTable.Cols.TMDBID + ", " +
                MovieTable.Cols.TITLE + ", " +
                MovieTable.Cols.IMDB_LINK + ", " +
                MovieTable.Cols.BACKDROP_PATH + ", " +
                MovieTable.Cols.RELEASE_DATE + ", " +
                MovieTable.Cols.OVERVIEW + ", " +
                MovieTable.Cols.IMDB_RATING + ", " +
                MovieTable.Cols.ACTORS + ", " +
                MovieTable.Cols.GENRES + ", " +
                MovieTable.Cols.STATUS + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
