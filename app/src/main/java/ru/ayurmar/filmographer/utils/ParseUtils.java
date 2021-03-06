package ru.ayurmar.filmographer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import ru.ayurmar.filmographer.R;
import ru.ayurmar.filmographer.model.Movie;
import ru.ayurmar.filmographer.model.Parameters;

/**
 * Содержит методы для загрузки сведений о фильмах, Аюр М., 16.02.2017.
 */

public class ParseUtils {
    private static final String sOmdbBase = "http://www.omdbapi.com/";
    private static final String sTmdbMovieBase = "http://api.themoviedb.org/3/movie/";
    private static final String sTmdbDiscoverBase = "http://api.themoviedb.org/3/discover/movie/";
    private static final String sApiKey = "4cdbd4367d3bbac1a675ab6e9416c1e6";

    public static JsonNode getJson(String url)
        throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        rootNode = objectMapper.readTree(new URL(url));
        return rootNode;
    }

    public static List<Movie> parseTmdbJson(JsonNode rootNode, Context context)
        throws IOException{
        List<Movie> result = new ArrayList<>();
        //парсинг результатов
        JsonNode moviesNode = rootNode.path(Movie.KEY_RESULTS);
        if(moviesNode.isMissingNode()){
            return null;
        }
        List<String> ids = Arrays.asList(context
                .getResources().getStringArray(R.array.filter_genres_ids));
        List<String> names = Arrays.asList(context
                .getResources().getStringArray(R.array.filter_genres_names));
        Iterator<JsonNode> moviesElements = moviesNode.elements();
        while(moviesElements.hasNext()){
            Movie movie = new Movie();

            JsonNode movieNode = moviesElements.next();
            //заполнение сведений о фильме
            movie.setTitle(movieNode.path(Movie.KEY_TITLE).asText());
            movie.setId(movieNode.path(Movie.KEY_ID).asText());
            movie.setBackdropPath(movieNode.path(Movie.KEY_BACKDROP_PATH).asText());
            movie.setReleaseDate(movieNode.path(Movie.KEY_RELEASE_DATE).asText());
            movie.setOverview(movieNode.path(Movie.KEY_OVERVIEW).asText());
            movie.setStatus(Movie.STATUS_DISCOVERED);

            //заполнение жанров
            String genres = "";
            JsonNode genresNode = movieNode.path(Movie.KEY_GENRES_IDS);
            Iterator<JsonNode> genreIds = genresNode.elements();
            while(genreIds.hasNext()){
                JsonNode genreId = genreIds.next();
                genres += getGenreNameById(genreId.asText(), ids, names) + ", ";
            }
            if(!genres.equals("")){
                genres = genres.substring(0, genres.length() - 2);
            }
            movie.setGenres(genres);
            result.add(movie);
        }
        return result;
    }

    public static void getImdbID(Movie movie)
        throws IOException{
        String url = Uri.parse(sTmdbMovieBase + movie.getId()).buildUpon()
                .appendQueryParameter("api_key", sApiKey)
                .appendQueryParameter("language", Locale.getDefault().getLanguage())
                .build().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new URL(url));
        movie.setImdbId(rootNode.path(Movie.KEY_IMDB_ID).asText());
    }

    public static void getImdbInfo(Movie movie)
            throws IOException{
        String url = Uri.parse(sOmdbBase).buildUpon()
                .appendQueryParameter("i", movie.getImdbId())
                .build().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new URL(url));
        String rating = rootNode.path(Movie.KEY_IMDB_RATING).asText();
        String actors = rootNode.path(Movie.KEY_ACTORS).asText();
        movie.setImdbRating(rating);
        movie.setActors(actors);
        movie.setImdbInfoLoaded(true);
    }

    private static String getGenreNameById(String id, List<String> ids, List<String> names){
        if(ids.contains(id)){
            return names.get(ids.indexOf(id));
        } else {
            return "";
        }
    }

    public static String createUrl(Parameters parameters){
        Uri.Builder builder = Uri.parse(sTmdbDiscoverBase).buildUpon()
                .appendQueryParameter("api_key", sApiKey)
                .appendQueryParameter("language", Locale.getDefault().getLanguage());
        Set<String> keys = parameters.getFilledParameters();
        for(String key : keys){
            builder.appendQueryParameter(key, parameters.getParameter(key));
        }
        return builder.build().toString();
    }

    /**
     * Проверяет наличие интернет-соединения
     * @return true, если имеется доступное подключение
     */
    public static boolean isOnline(FragmentActivity activity){
        ConnectivityManager connManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}