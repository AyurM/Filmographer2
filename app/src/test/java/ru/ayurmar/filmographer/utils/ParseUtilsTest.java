package ru.ayurmar.filmographer.utils;

import android.content.Context;
import android.content.res.Resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

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
    public void whenCorrectJsonThenItsParsed()
        throws IOException{
        String jsonString = "{\n" +
                "  \"page\": 1,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"poster_path\": \"/bbxtz5V0vvnTDA2qWbiiRC77Ok9.jpg\",\n" +
                "      \"adult\": false,\n" +
                "      \"overview\": \"Julia becomes worried about her boyfriend, Holt when he explores the dark urban legend of a mysterious videotape said to kill the watcher seven days after viewing. She sacrifices herself to save her boyfriend and in doing so makes a horrifying discovery: there is a \\\"movie within the movie\\\" that no one has ever seen before.\",\n" +
                "      \"release_date\": \"2017-02-01\",\n" +
                "      \"genre_ids\": [\n" +
                "        27\n" +
                "      ],\n" +
                "      \"id\": 14564,\n" +
                "      \"original_title\": \"Rings\",\n" +
                "      \"original_language\": \"en\",\n" +
                "      \"title\": \"Rings\",\n" +
                "      \"backdrop_path\": \"/biN2sqExViEh8IYSJrXlNKjpjxx.jpg\",\n" +
                "      \"popularity\": 159.533213,\n" +
                "      \"vote_count\": 209,\n" +
                "      \"video\": false,\n" +
                "      \"vote_average\": 5.2\n" +
                "    },\n" +
                "    {\n" +
                "      \"poster_path\": \"/WLQN5aiQG8wc9SeKwixW7pAR8K.jpg\",\n" +
                "      \"adult\": false,\n" +
                "      \"overview\": \"The quiet life of a terrier named Max is upended when his owner takes in Duke, a stray whom Max instantly dislikes.\",\n" +
                "      \"release_date\": \"2016-06-18\",\n" +
                "      \"mGenreIds\": [\n" +
                "        12,\n" +
                "        16,\n" +
                "        35,\n" +
                "        10751\n" +
                "      ],\n" +
                "      \"id\": 328111,\n" +
                "      \"original_title\": \"The Secret Life of Pets\",\n" +
                "      \"original_language\": \"en\",\n" +
                "      \"title\": \"The Secret Life of Pets\",\n" +
                "      \"backdrop_path\": \"/lubzBMQLLmG88CLQ4F3TxZr2Q7N.jpg\",\n" +
                "      \"popularity\": 109.400417,\n" +
                "      \"vote_count\": 2202,\n" +
                "      \"video\": false,\n" +
                "      \"vote_average\": 5.8\n" +
                "    }],\"total_results\": 299244,\n" +
                "  \"total_pages\": 14963\n" +
                "}";
        Movie firstMovie = new Movie();
        Movie secondMovie = new Movie();
        firstMovie.setId("14564");
        firstMovie.setTitle("Rings");
        secondMovie.setId("328111");
        secondMovie.setTitle("The Secret Life of Pets");
        List<Movie> testList = ParseUtils.parseTmdbJson(jsonString, mContext);
        assertNotNull(testList);
        assertEquals(2, testList.size());
        assertEquals(firstMovie, testList.get(0));
        assertEquals(secondMovie, testList.get(1));
    }

    @Test
    public void whenWrongJsonThenResultIsNull()
            throws IOException{
        String jsonString = "{\"status_message\":\"Invalid API key: " +
                "You must be granted a valid key.\",\"success\":false,\"status_code\":7}";
        List<Movie> testList = ParseUtils.parseTmdbJson(jsonString, mContext);
        assertNull(testList);
    }

    @Test
    public void whenGenreIdPassedThenGenreNameReturned(){
        assertEquals("Crime", ParseUtils.getGenreNameById("80", mGenreIds, mGenreNames));
    }

    @Test
    public void whenWrongGenreIdPassedThenEmptyString(){
        assertEquals("", ParseUtils.getGenreNameById("1686", mGenreIds, mGenreNames));
    }

    @Test
    public void whenCorrectJsonThenGenresAreParsed()
            throws IOException{
        String jsonString = "{\n" +
                "  \"page\": 1,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"poster_path\": \"/WLQN5aiQG8wc9SeKwixW7pAR8K.jpg\",\n" +
                "      \"adult\": false,\n" +
                "      \"overview\": \"The quiet life of a terrier named Max is upended when his owner takes in Duke, a stray whom Max instantly dislikes.\",\n" +
                "      \"release_date\": \"2016-06-18\",\n" +
                "      \"genre_ids\": [\n" +
                "        12,\n" +
                "        16,\n" +
                "        35,\n" +
                "        10751\n" +
                "      ],\n" +
                "      \"id\": 328111,\n" +
                "      \"original_title\": \"The Secret Life of Pets\",\n" +
                "      \"original_language\": \"en\",\n" +
                "      \"title\": \"The Secret Life of Pets\",\n" +
                "      \"backdrop_path\": \"/lubzBMQLLmG88CLQ4F3TxZr2Q7N.jpg\",\n" +
                "      \"popularity\": 109.400417,\n" +
                "      \"vote_count\": 2202,\n" +
                "      \"video\": false,\n" +
                "      \"vote_average\": 5.8\n" +
                "    }],\"total_results\": 299244,\n" +
                "  \"total_pages\": 14963\n" +
                "}";
        String expectedGenres = "Adventure, Animation, Comedy, Family";
        List<Movie> testList = ParseUtils.parseTmdbJson(jsonString, mContext);
        assertEquals(expectedGenres, testList.get(0).getGenres());
    }
}
