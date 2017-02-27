package ru.ayurmar.filmographer.net;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Ayur on 27.02.2017.
 */

public class MovieInfoLoaderTest {

    @Test
    public void whenInstantiatedThenLocaleIsSet(){
        MovieInfoLoader loader = new MovieInfoLoader();
        String expectedLocale = "ru";
        assertEquals(expectedLocale, loader.getLocaleLanguage());
    }
}
