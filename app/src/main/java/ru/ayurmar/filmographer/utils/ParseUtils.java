package ru.ayurmar.filmographer.utils;

import android.content.Context;
import android.net.Uri;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ru.ayurmar.filmographer.R;
import ru.ayurmar.filmographer.model.Movie;

/**
 * Created by Ayur on 16.02.2017.
 */

public class ParseUtils {
    public static final String TMDB_MOVIE_BASE = "http://api.themoviedb.org/3/movie/";
    public static final String TMDB_DISCOVER_BASE = "http://api.themoviedb.org/3/discover/movie/";
    public static final String API_KEY = "4cdbd4367d3bbac1a675ab6e9416c1e6";

    public static JsonNode getJson(String url){
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(new URL(url));
        } catch (Exception e){
            return null;
        }
        return rootNode;
    }

    public static List<Movie> parseTmdbJson(String jsonString, Context context)
        throws IOException{
        List<Movie> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
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

    public static String getGenreNameById(String id, List<String> ids, List<String> names){
        if(ids.contains(id)){
            return names.get(ids.indexOf(id));
        } else {
            return "";
        }
    }

    public static String createUrl(){
        Uri.Builder builder = Uri.parse(TMDB_DISCOVER_BASE).buildUpon()
               .appendQueryParameter("api_key", API_KEY);
        return builder.build().toString();
    }
}
