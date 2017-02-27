package ru.ayurmar.filmographer;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ayurmar.filmographer.model.Movie;
import ru.ayurmar.filmographer.utils.ParseUtils;

public class FilmographerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Movie> mMovieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmographer);

        mRecyclerView = (RecyclerView) findViewById(R.id.fragment_movie_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new LoadMoviesTask().execute(this);
    }

    private void setupAdapter(List<Movie> movieList) {
        mRecyclerView.setAdapter(new MovieAdapter(movieList, this));
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
        List<Movie> mAdapterList;
        Context mContext;

        public MovieAdapter(List<Movie> movieList, Context context){
            mAdapterList = movieList;
            mContext = context;
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater
                    .inflate(R.layout.cardview_movie_info, parent, false);
            return new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieHolder movieHolder, int position) {
            Movie movieInfo = mAdapterList.get(position);
            movieHolder.bindMovie(movieInfo);
        }

        @Override
        public int getItemCount() {
            return mAdapterList.size();
        }
    }

    class MovieHolder extends RecyclerView.ViewHolder{
        ImageView mMoviePoster;
        TextView mMovieTitle;
        TextView mMovieInfo;
        TextView mMovieOverview;
        TextView mMovieCast;
        Button mButtonImdb;
        Button mButtonWantToWatch;
        RelativeLayout mMainInfoLayout;
        RelativeLayout mDetailInfoLayout;
        Movie mMovie;

        public MovieHolder(View itemView){
            super(itemView);
            mMovieTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            mMovieOverview = (TextView) itemView.findViewById(R.id.text_view_overview);
            mMovieInfo = (TextView) itemView.findViewById(R.id.text_view_info);
            mMovieCast = (TextView) itemView.findViewById(R.id.text_view_cast);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.image_view_backdrop);
            mButtonImdb = (Button) itemView.findViewById(R.id.button_imdb);
            mButtonWantToWatch = (Button) itemView.findViewById(R.id.button_want_to_watch);
            mMainInfoLayout = (RelativeLayout) itemView.findViewById(R.id.layout_main_info);
            mDetailInfoLayout = (RelativeLayout) itemView.findViewById(R.id.layout_detail_info);

        }

        public void bindMovie(Movie movie){
            mMovie = movie;
            if(mMovie != null){
                mMovieTitle.setText(mMovie.getTitle());    //вывести название
                //вывести жанр
                String genres = mMovie.getGenres();
                //вывести инфо о фильме
                String imdbRating = mMovie.getImdbRating();
                String year = mMovie.getReleaseDate();
                String overview = mMovie.getOverview();
                mMovieInfo.setVisibility(View.VISIBLE);
                mMovieInfo.setText(year);
                //вывести описание
                mMovieOverview.setText(overview);
            }
        }
    }

    private class LoadMoviesTask extends AsyncTask<Context, Void, List<Movie>>{
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
