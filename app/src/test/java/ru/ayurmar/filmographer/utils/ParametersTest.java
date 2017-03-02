package ru.ayurmar.filmographer.utils;

import org.junit.Before;
import org.junit.Test;

import ru.ayurmar.filmographer.model.Parameters;

import static org.junit.Assert.*;


public class ParametersTest {
    private Parameters mParameters;

    @Before
    public void setUp(){
        mParameters = new Parameters();
    }

    @Test
    public void whenInstantiatedThenParametersAreEmpty(){
        assertTrue(mParameters.getParameters().isEmpty());
    }

    @Test
    public void whenParameterIsSetThenItIsInMap(){
        mParameters.setParameter(Parameters.RELEASE_DATE_GTE, "2010-01-01");
        mParameters.setParameter(Parameters.RELEASE_DATE_LTE, "2016-01-01");
        assertEquals(2, mParameters.getParameters().size());
    }

    @Test
    public void whenCorrectParameterIsRequestedThenItsReturned(){
        mParameters.setParameter(Parameters.VOTE_AVERAGE_GTE, "100");
        String expected = "100";
        assertEquals(expected, mParameters.getParameter(Parameters.VOTE_AVERAGE_GTE));
    }

    @Test
    public void whenEmptyParameterIsRequestedThenNull(){
        mParameters.setParameter(Parameters.RELEASE_DATE_LTE, "");
        assertNull(mParameters.getParameter(Parameters.RELEASE_DATE_LTE));
    }

    @Test
    public void whenWrongParameterIsRequestedThenNull(){
        assertNull(mParameters.getParameter(Parameters.RELEASE_DATE_LTE));
    }

    @Test
    public void whenCorrectParameterIsCheckedThenTrue(){
        mParameters.setParameter(Parameters.VOTE_AVERAGE_GTE, "250");
        assertTrue(mParameters.hasParameter(Parameters.VOTE_AVERAGE_GTE));
    }

    @Test
    public void whenEmptyParameterIsCheckedThenFalse(){
        mParameters.setParameter(Parameters.VOTE_AVERAGE_GTE, "");
        assertFalse(mParameters.hasParameter(Parameters.VOTE_AVERAGE_GTE));
    }
}
