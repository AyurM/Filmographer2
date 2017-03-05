package ru.ayurmar.filmographer.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class MovieTest {

    private Movie mMovie;

    @Before
    public void setUp(){
        mMovie = new Movie();
    }

    @Test
    public void whenInstantiatedThenInfoIsEmpty(){
        assertEquals("", mMovie.getTitle());
        assertEquals("", mMovie.getId());
        assertEquals("", mMovie.getImdbId());
        assertEquals("", mMovie.getImdbRating());
        assertEquals("", mMovie.getReleaseDate());
        assertEquals("", mMovie.getOverview());
        assertEquals("", mMovie.getActors());
        assertEquals("", mMovie.getGenres());
        assertEquals("", mMovie.getStatus());
        assertEquals("", mMovie.getBackdropPath());
        assertFalse(mMovie.isImdbInfoLoaded());
    }

    @Test
    public void whenToStringThenDescriptionIsPrinted(){
        String id = "test_id_12345";
        String title = "test_title";
        String overview = "test_overview_bla_bla_bla";
        String release_date = "test_date";
        String imdb_link = "test_link";
        String backdrop_path = "test_path";
        mMovie.setId(id);
        mMovie.setTitle(title);
        mMovie.setOverview(overview);
        mMovie.setReleaseDate(release_date);
        mMovie.setImdbId(imdb_link);
        mMovie.setBackdropPath(backdrop_path);
        String expectedDescription = Movie.KEY_ID + ": " + id + "; " +
                Movie.KEY_TITLE + ": " + title + "; " +
                Movie.KEY_OVERVIEW + ": " + overview + "; " +
                Movie.KEY_RELEASE_DATE + ": " + release_date + "; " +
                Movie.KEY_IMDB_ID + ": " + imdb_link + "; " +
                Movie.KEY_BACKDROP_PATH + ": " + backdrop_path + ";";
        assertEquals(expectedDescription, mMovie.toString());
    }

    @Test
    public void whenMoviesWithSameIdAreComparedThenTrue(){
        mMovie.setId("test_id_56789");
        Movie testMovie = new Movie();
        testMovie.setId("test_id_56789");
        assertTrue(mMovie.equals(testMovie));
    }

    @Test
    public void whenMoviesWithDifferentIdAreComparedThenFalse(){
        mMovie.setId("test_id_56789");
        Movie testMovie = new Movie();
        testMovie.setId("test_id_12345");
        assertFalse(mMovie.equals(testMovie));
    }
}