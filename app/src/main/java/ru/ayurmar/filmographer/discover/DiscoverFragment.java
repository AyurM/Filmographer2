package ru.ayurmar.filmographer.discover;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ayurmar.filmographer.R;
import ru.ayurmar.filmographer.model.Movie;
import ru.ayurmar.filmographer.model.MovieCollection;
import ru.ayurmar.filmographer.model.Parameters;
import ru.ayurmar.filmographer.utils.FormatUtils;
import ru.ayurmar.filmographer.utils.ParseUtils;

public class DiscoverFragment extends Fragment {
    public static final String FRAGMENT_TAG = "discover_fragment";

    RecyclerView mRecyclerView;
    ProgressBar mProgressBar;
    Parameters mParameters = new Parameters();
    List<Movie> mMovieList = new ArrayList<>();
    boolean mDiscoverInProgress = false;
    private ImdbInfoLoader<DiscoverHolder> mInfoLoader;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mMovieList = MovieCollection.get(getActivity()).getMovies(Movie.STATUS_DISCOVERED);
        mParameters.loadParameters(getActivity());

        Handler responseHandler = new Handler();
        mInfoLoader = new ImdbInfoLoader<>(responseHandler);
        mInfoLoader.setImdbInfoLoadListener(
                new ImdbInfoLoader.ImdbInfoLoadListener<DiscoverHolder>() {
            @Override
            public void onImdbInfoLoaded(DiscoverHolder movieHolder) {
                movieHolder.rebindMovie(getActivity());    //передача загруженных IMDb-сведений о фильме
            }
        });
        mInfoLoader.start();
        mInfoLoader.getLooper();
        Log.i(FormatUtils.TAG, "Background thread started");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover, container, false);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_discover_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(getResources().getString(R.string.menu_discover_title));
        }
        if(mMovieList.isEmpty()){
            discoverMovies(true);
        } else {
            setupAdapter(mMovieList);
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_discover_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //обновить список discovered фильмов
            case R.id.menu_item_refresh_list:
                discoverMovies(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mParameters.loadParameters(getActivity());
    }

    @Override
    public void onPause(){
        super.onPause();
        mParameters.saveParameters(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInfoLoader.quit();
        Log.i(FormatUtils.TAG, "Background thread destroyed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mInfoLoader.clearQueue();
    }

    private void setupAdapter(List<Movie> movieList) {
        mRecyclerView.setAdapter(
                new DiscoverAdapter(movieList, getActivity(), mInfoLoader));
    }

    private void discoverMovies(boolean clearDb){
        if(ParseUtils.isOnline(getActivity())){
            if(!mDiscoverInProgress){
                mDiscoverInProgress = true;
                if(clearDb){
                    MovieCollection.get(getActivity()).clear(Movie.STATUS_DISCOVERED);
                    mMovieList.clear();
                }
                new LoadMoviesTask().execute(getActivity());
            }
        } else {
            FormatUtils.showConnectionErrorMessage(getActivity());
        }
    }

    private class LoadMoviesTask extends AsyncTask<Context, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            mRecyclerView.setAlpha(0.1f);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(Context... params) {
            Log.i(FormatUtils.TAG, "AsyncTask started...");
            List<Movie> result;
            String url = ParseUtils.createUrl(mParameters);
            Log.i(FormatUtils.TAG, "Discovering movies from: " + url);
            try{
                result = ParseUtils.parseTmdbJson(ParseUtils.getJson(url), params[0]);
            } catch (IOException e){
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            if(result == null){
                return;
            }
            MovieCollection.get(getActivity()).addAll(result);
            mMovieList = result;
            setupAdapter(mMovieList);
            mRecyclerView.setAlpha(1f);
            mProgressBar.setVisibility(View.GONE);
            mDiscoverInProgress = false;
            Log.i(FormatUtils.TAG, result.size() + " movies added to database.\nAsyncTask finished.");
        }
    }
}
