package ru.ayurmar.filmographer.model;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MovieCollectionTest {

//    private MovieCollection mMovieCollection;
//    private Context mContext;
//
//    @Before
//    public void setUp(){
//        mContext = mock(Context.class);
//        mMovieCollection = MovieCollection.get(mContext);
//    }
//
//    @After
//    public void tearDown(){
//        mMovieCollection.clear();
//    }
//
//    @Test
//    public void whenInstantiatedThenListIsEmpty(){
//        assertTrue(mMovieCollection.getMovies().isEmpty());
//    }
//
//    @Test
//    public void whenMoviesAreAddedThenAddToList(){
//        Movie test_movie = new Movie();
//        Movie another_test_movie = new Movie();
//        mMovieCollection.add(test_movie);
//        mMovieCollection.add(another_test_movie);
//        assertEquals(mMovieCollection.getMovies().size(), 2);
//    }
//
//    @Test
//    public void whenMovieIsRemovedThenDeleteFromList(){
//        Movie test_movie = new Movie();
//        test_movie.setId("test_id");
//        test_movie.setTitle("test_title");
//        mMovieCollection.add(test_movie);
//        assertEquals(mMovieCollection.getMovies().size(), 1);
//        mMovieCollection.delete(test_movie);
//        assertTrue(mMovieCollection.getMovies().isEmpty());
//    }
//
//    @Test
//    public void whenListIsClearedThenListIsEmpty(){
//        Movie test_movie = new Movie();
//        Movie another_test_movie = new Movie();
//        mMovieCollection.add(test_movie);
//        mMovieCollection.add(another_test_movie);
//        mMovieCollection.clear();
//        assertTrue(mMovieCollection.getMovies().isEmpty());
//    }
//
//    @Test
//    public void whenListOfMoviesIsAddedThenAddAll(){
//        List<Movie> testList = new ArrayList<>();
//        Movie test_movie = new Movie();
//        Movie another_test_movie = new Movie();
//        testList.add(test_movie);
//        testList.add(another_test_movie);
//        mMovieCollection.addAll(testList);
//        assertEquals(mMovieCollection.getMovies().size(), 2);
//    }
}
