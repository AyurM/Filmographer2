package ru.ayurmar.filmographer.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MovieListTest {

    private MovieList mMovieList;

    @Before
    public void setUp(){
        mMovieList = new MovieList();
    }

    @Test
    public void whenInstantiatedThenListIsEmpty(){
        assertTrue(mMovieList.getMovies().isEmpty());
    }

    @Test
    public void whenMoviesAreAddedThenAddToList(){
        Movie test_movie = new Movie();
        Movie another_test_movie = new Movie();
        mMovieList.add(test_movie);
        mMovieList.add(another_test_movie);
        assertEquals(mMovieList.getMovies().size(), 2);
    }

    @Test
    public void whenMovieIsRemovedThenDeleteFromList(){
        Movie test_movie = new Movie();
        test_movie.setId("test_id");
        test_movie.setTitle("test_title");
        mMovieList.add(test_movie);
        assertEquals(mMovieList.getMovies().size(), 1);
        mMovieList.delete(test_movie);
        assertTrue(mMovieList.getMovies().isEmpty());
    }

    @Test
    public void whenListIsClearedThenListIsEmpty(){
        Movie test_movie = new Movie();
        Movie another_test_movie = new Movie();
        mMovieList.add(test_movie);
        mMovieList.add(another_test_movie);
        mMovieList.clear();
        assertTrue(mMovieList.getMovies().isEmpty());
    }

    @Test
    public void whenListOfMoviesIsAddedThenAddAll(){
        List<Movie> testList = new ArrayList<>();
        Movie test_movie = new Movie();
        Movie another_test_movie = new Movie();
        testList.add(test_movie);
        testList.add(another_test_movie);
        mMovieList.addAll(testList);
        assertEquals(mMovieList.getMovies().size(), 2);
    }
}
