package ru.ayurmar.filmographer.discover;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.ayurmar.filmographer.R;
import ru.ayurmar.filmographer.model.Movie;
import ru.ayurmar.filmographer.utils.FormatUtils;

public class DiscoverHolder extends RecyclerView.ViewHolder {
    ImageView mMoviePoster;
    TextView mMovieTitle;
    TextView mMovieInfo;
    FloatingActionButton mButtonWantToWatch;
    RelativeLayout mMainInfoLayout;
    Movie mMovie;

    public DiscoverHolder(View itemView){
        super(itemView);
        mMovieTitle = (TextView) itemView.findViewById(R.id.text_view_title);
        mMovieInfo = (TextView) itemView.findViewById(R.id.text_view_info);
        mMoviePoster = (ImageView) itemView.findViewById(R.id.image_view_backdrop);
        mButtonWantToWatch = (FloatingActionButton) itemView.findViewById(R.id.fab);
        mMainInfoLayout = (RelativeLayout) itemView.findViewById(R.id.layout_main_info);
    }

    public void bindMovie(Movie movie, Context context){
        mMovie = movie;
        mMovieTitle.setText(mMovie.getTitle());    //вывести название
        //вывести жанр
        String genres = mMovie.getGenres();
        //вывести инфо о фильме
        String imdbRating = mMovie.getImdbRating();
        String year = mMovie.getReleaseDate();
        Spanned spannedInfo;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spannedInfo = Html.fromHtml(FormatUtils
                    .buildStringForHtmlInfo(mMovie, year, genres, imdbRating, context),
                    Html.FROM_HTML_MODE_LEGACY);
        } else {
            spannedInfo = Html.fromHtml(FormatUtils
                    .buildStringForHtmlInfo(mMovie, year, genres, imdbRating, context));
        }
        mMovieInfo.setVisibility(View.VISIBLE);
        mMovieInfo.setText(spannedInfo);
    }

    public ImageView getMoviePoster(){
        return mMoviePoster;
    }
}
