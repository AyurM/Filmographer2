package ru.ayurmar.filmographer.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Ayur on 16.02.2017.
 */

public class MovieInfoLoader {
    public static final String TAG = "MovieInfoLoader";
    public static final String OMDB_BASE = "http://www.omdbapi.com/";
    private static final String API_KEY = "4cdbd4367d3bbac1a675ab6e9416c1e6";
    private static final String TMDB_MOVIE_BASE = "http://api.themoviedb.org/3/movie/";
    private static final String TMDB_DISCOVER_BASE = "http://api.themoviedb.org/3/discover/movie/";
    private final String mLocaleLanguage;

    public MovieInfoLoader(){
        mLocaleLanguage = Locale.getDefault().getLanguage();
    }

    public String getLocaleLanguage() {
        return mLocaleLanguage;
    }
}
