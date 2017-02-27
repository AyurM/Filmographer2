package ru.ayurmar.filmographer.discover;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ayurmar.filmographer.R;
import ru.ayurmar.filmographer.model.Movie;
import ru.ayurmar.filmographer.utils.ParseUtils;

public class DiscoverFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<Movie> mMovieList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new LoadMoviesTask().execute(getActivity());
//        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_discover_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    private void setupAdapter(List<Movie> movieList) {
        mRecyclerView.setAdapter(new DiscoverAdapter(movieList, getActivity()));
    }

    private class LoadMoviesTask extends AsyncTask<Context, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(Context... params) {
            List<Movie> result;
            String url = ParseUtils.createUrl();
            try{
                result = ParseUtils.parseTmdbJson(ParseUtils.getJson(url), params[0]);
            } catch (IOException e){
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            mMovieList.addAll(result);
            setupAdapter(mMovieList);
        }
    }
}
