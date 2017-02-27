package ru.ayurmar.filmographer.utils;

import android.content.Context;
import android.content.res.Resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ru.ayurmar.filmographer.R;
import ru.ayurmar.filmographer.model.Movie;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ParseUtilsTest {
    private Context mContext;
    private Resources mResources;
    private List<String> mGenreNames;
    private List<String> mGenreIds;

    @Rule
    public ExpectedException exception =
            ExpectedException.none();

    @Before
    public void setUp(){
        mContext = mock(Context.class);
        mResources = mock(Resources.class);
        String[] names_array = {"Any", "Action", "Adventure", "Animation", "Comedy",
                "Crime", "Family"};
        String[] ids_array = {"null", "28", "12", "16", "35", "80", "10751"};
        mGenreNames = Arrays.asList(names_array);
        mGenreIds = Arrays.asList(ids_array);
        doReturn(mResources).when(mContext).getResources();
        doReturn(names_array).when(mResources)
                .getStringArray(R.array.filter_genres_names);
        doReturn(ids_array).when(mResources)
                .getStringArray(R.array.filter_genres_ids);
    }

    @Test
    public void whenWrongUrlThenIOException()
        throws IOException{
        //строка с неправильным api_key
        String url = "http://api.themoviedb.org/3/discover/movie/" +
                "?api_key=4cdbd4asdaac1a675ab6e9416c1e6";
        exception.expect(IOException.class);
        ParseUtils.getJson(url);
    }

    @Test
    public void whenCorrectJsonThenItsParsed()
        throws IOException{
        String url = "http://api.themoviedb.org/3/discover/movie/" +
                "?api_key=4cdbd4367d3bbac1a675ab6e9416c1e6";
        assertNotNull(ParseUtils.parseTmdbJson(ParseUtils.getJson(url), mContext));
        assertEquals(20, ParseUtils.parseTmdbJson(ParseUtils.getJson(url), mContext).size());
    }

    @Test
    public void whenWrongJsonThenResultIsNull()
            throws IOException{
        //URL, по которому не содержатся сведения о фильме
        String url = "https://api.themoviedb.org/3/genre/movie/list" +
                "?api_key=4cdbd4367d3bbac1a675ab6e9416c1e6";
        assertNull(ParseUtils.parseTmdbJson(ParseUtils.getJson(url), mContext));
    }
}
