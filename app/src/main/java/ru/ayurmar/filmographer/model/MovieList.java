package ru.ayurmar.filmographer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayur on 16.02.2017.
 */

public class MovieList {

    private List<Movie> mMovieList;

    public MovieList(){
        this.mMovieList = new ArrayList<>();
    }

    public List<Movie> getMovies(){
        return mMovieList;
    }

    public void add(Movie movie){
        mMovieList.add(movie);
    }

    public void addAll(List<Movie> movieList){
        mMovieList.addAll(movieList);
    }

    public void delete(Movie movie){
        mMovieList.remove(movie);
    }

    public void clear(){
        mMovieList.clear();
    }
}
