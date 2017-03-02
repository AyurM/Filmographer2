package ru.ayurmar.filmographer.model;

import android.content.Context;
import android.util.Log;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ru.ayurmar.filmographer.utils.FormatUtils;

public class Parameters {
    public static final String RELEASE_DATE_GTE = "primary_release_date.gte";
    public static final String RELEASE_DATE_LTE = "primary_release_date.lte";
    public static final String WITH_GENRES = "with_genres";
    public static final String VOTE_AVERAGE_GTE = "vote_average.gte";
    public static final String SORT_BY = "sort_by";
    private static final String VOTE_COUNT_GTE = "vote_count.gte";
    //настройки по умолчанию
    public static final int DEFAULT_MIN_RELEASE_DATE = 1970;
    public static final int DEFAULT_MAX_RELEASE_DATE = Calendar.getInstance().get(Calendar.YEAR);
    public static final String DEFAULT_MMDD = "-01-01";
    public static final String CURRENT_MMDD = setCurrentMMDD();
    public static final String MAX_MMDD = "-12-31";
    private static final String sDefaultVoteAverage = "5.0";
    private static final String sDefaultVoteCountGte = "200";


    private static final String sSaveFilename = "FilterParameters.json";
    private Map<String, String> mParameters;

    public Parameters(){
        mParameters = new HashMap<>();
        setParameter(VOTE_COUNT_GTE, sDefaultVoteCountGte);
        setParameter(VOTE_AVERAGE_GTE, sDefaultVoteAverage);
        setParameter(RELEASE_DATE_GTE, DEFAULT_MIN_RELEASE_DATE + DEFAULT_MMDD);
        setParameter(RELEASE_DATE_LTE, DEFAULT_MAX_RELEASE_DATE + CURRENT_MMDD);
    }

    public Map<String, String> getParameters() {
        return mParameters;
    }

    public Set<String> getFilledParameters(){
        Set<String> result = mParameters.keySet();
        for(String key : result){
            if(!hasParameter(key)){
                result.remove(key);
            }
        }
        return result;
    }

    public void setParameters(Map<String, String> parameters) {
        mParameters = parameters;
    }

    public void setParameter(String key, String value){
        mParameters.put(key, value);
    }

    public String getParameter(String key){
        String result = mParameters.get(key);
        if(result == null || result.isEmpty()){
            return null;
        }
        return result;
    }

    public boolean hasParameter(String key){
        return mParameters.containsKey(key) && getParameter(key) != null;
    }

    public void saveParameters(Context context){
        FileOutputStream outputStream;
        ObjectMapper mapper = new ObjectMapper();
        try{
            outputStream = context.openFileOutput(sSaveFilename, Context.MODE_PRIVATE);
            mapper.writeValue(outputStream, mParameters);
            outputStream.close();
        } catch (IOException e){
            Log.e(FormatUtils.TAG, "Error while saving filter parameters!");
            e.printStackTrace();
        }
    }

    public void loadParameters(Context context){
        InputStream inputStream;
        ObjectMapper mapper = new ObjectMapper();
        try{
            inputStream = context.openFileInput(sSaveFilename);
            mParameters = mapper.readValue(inputStream,
                    new TypeReference<Map<String, String>>(){});
            inputStream.close();
        } catch (IOException e){
            Log.e(FormatUtils.TAG, "Error while loading filter parameters!");
            e.printStackTrace();
        }
    }

    private static String setCurrentMMDD(){
        String currMMDD = "-";
        String currMM = Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1);
        currMM = currMM.length() == 1 ? "0" + currMM : currMM;
        String currDD = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        currMMDD += currMM + "-" +currDD;
        return currMMDD;
    }
}
