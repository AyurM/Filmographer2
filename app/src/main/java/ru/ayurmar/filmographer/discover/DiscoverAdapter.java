package ru.ayurmar.filmographer.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;
import ru.ayurmar.filmographer.R;
import ru.ayurmar.filmographer.model.Movie;
import ru.ayurmar.filmographer.utils.ParseUtils;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverHolder> {
    List<Movie> mAdapterList;
    Context mContext;
    ImdbInfoLoader<DiscoverHolder> mInfoLoader;

    public DiscoverAdapter(List<Movie> movieList, Context context,
                           ImdbInfoLoader<DiscoverHolder> infoLoader){
        mAdapterList = movieList;
        mContext = context;
        mInfoLoader = infoLoader;
    }

    @Override
    public DiscoverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater
                .inflate(R.layout.cardview_movie_info, parent, false);
        return new DiscoverHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoverHolder movieHolder, int position) {
        Movie movie = mAdapterList.get(position);
        movieHolder.bindMovie(movie, mContext);
        //загрузка картинки к фильму
        Picasso.with(mContext).load(movie.getBackdropPath())
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(movieHolder.getMoviePoster());
        if(!movie.isImdbInfoLoaded()){
            mInfoLoader.queueLoad(movieHolder, movie);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapterList.size();
    }
}
