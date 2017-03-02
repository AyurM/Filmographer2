package ru.ayurmar.filmographer.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import ru.ayurmar.filmographer.R;
import ru.ayurmar.filmographer.model.Movie;

public class FormatUtils {
    public static final String TAG = "Filmographer";
    private static final int sMaxOverviewLength = 220;
    private static final double sImdbRatingThreshold = 7.0;

    private static String editReleaseYear(String year, Context context){
        if(year != null && year.length() > 4){
            year = year.substring(0, 4);
        } else {
            year = context.getString(R.string.message_unknown_text);
        }
        return year;
    }

    private static String editImdbRating(String imdbRating, Context context){
        if(imdbRating == null || imdbRating.equals("")){
            imdbRating = context.getString(R.string.message_unknown_text);
        }
        return imdbRating;
    }

    /**
     * Сокращает слишком длинное описание фильма или информирует об его отсутствии
     * @param overview исходное описание
     * @return скорректированное описание
     */
    public static String editOverview(String overview, Context context){
        if(overview.length() > sMaxOverviewLength){
            overview = overview.substring(0, sMaxOverviewLength) + "… " +
                    "<b>" + context.getString(R.string.movie_overview_show_more_text) + "</b>";    //слишком длинное описание
        } else if(overview.equals("null") || overview.length() == 0){
            overview = context.getString(R.string.movie_no_overview_text);    //нет описания
        }
        return overview;
    }

    public static String buildStringForHtmlInfo(Movie movieInfo, String year, String genres,
                                                String rating, Context context){
        String editedYear = editReleaseYear(year, context);
        String editedRating = editImdbRating(rating, context);
        String result = "<b>" + context.getString(R.string.movie_year_text) + "</b>" + " " + editedYear + "<br>" +
                "<b>" + context.getString(R.string.movie_genre_text) + "</b>" + " " + genres + "<br>" +
                "<b>" + context.getString(R.string.movie_rating_text) + "</b>"+ " ";
        //хорошая оценка IMDb выводится большим жирным шрифтом
        try{
            if(movieInfo.isImdbInfoLoaded()
                    && Double.parseDouble(editedRating) >= sImdbRatingThreshold){
                result += "<big> <b>" + editedRating + "</b> </big>";
            } else {
                result += editedRating;
            }
        } catch (NumberFormatException exc){
            result += editedRating;
        }
        return result;
    }

    public static String buildStringForHtmlOverview(String overview, Context context){
        String editedOverview = editOverview(overview, context);
        return "<b>" + "\t" + context.getString(R.string.movie_overview_text) +
                "</b>" + " " + editedOverview;
    }

    public static void showConnectionErrorMessage(FragmentActivity activity){
        Toast.makeText(activity, R.string.message_no_connection_text, Toast.LENGTH_SHORT).show();
    }
}
