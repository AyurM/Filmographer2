package ru.ayurmar.filmographer.model;

import android.content.Context;
import android.util.Log;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ru.ayurmar.filmographer.discover.DiscoverFragment;

public class Parameters {
    public static final String RELEASE_DATE_GTE = "primary_release_date.gte";
    public static final String RELEASE_DATE_LTE = "primary_release_date.lte";
    public static final String WITH_GENRES = "with_genres";
    public static final String VOTE_AVERAGE_GTE = "vote_average.gte";
    public static final String SORT_BY = "sort_by";
    private static final String VOTE_COUNT_GTE = "vote_count.gte";
    
    private static final String sSaveFilename = "FilterParameters.json";
    private static final String sVoteCountGte = "200";

    private Map<String, String> mParameters;

    public Parameters(){
        mParameters = new HashMap<>();
        setParameter(VOTE_COUNT_GTE, sVoteCountGte);
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
            Log.e(DiscoverFragment.TAG, "Error while saving filter parameters!");
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
            Log.e(DiscoverFragment.TAG, "Error while loading filter parameters!");
            e.printStackTrace();
        }
    }
}
