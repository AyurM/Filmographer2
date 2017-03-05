
package ru.ayurmar.filmographer.discover;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.ayurmar.filmographer.model.Movie;
import ru.ayurmar.filmographer.utils.FormatUtils;
import ru.ayurmar.filmographer.utils.ParseUtils;

/**
 * Загрузчик IMDB-сведений к фильмам, Аюр М., 13.08.2016.
 */

class ImdbInfoLoader<T> extends HandlerThread {
    private static final String sTag = "ImdbInfoLoader";
    private static final int MESSAGE_DOWNLOAD = 0;
    private Handler mRequestHandler;
    private final ConcurrentMap<T, Movie> mRequestMap = new ConcurrentHashMap<>();
    private final Handler mResponseHandler;
    private ImdbInfoLoadListener<T> mImdbInfoLoadListener;

    public interface ImdbInfoLoadListener<T> {
        void onImdbInfoLoaded(T target);
    }

    public void setImdbInfoLoadListener
            (ImdbInfoLoadListener<T> listener) {
        mImdbInfoLoadListener = listener;
    }

    public ImdbInfoLoader(Handler responseHandler) {
        super(sTag);
        mResponseHandler = responseHandler;
    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    @SuppressWarnings("unchecked") T target = (T) msg.obj;
                    handleRequest(target);
                }
            }
        };
    }

    public void queueLoad(T target, Movie movie) {
        if (movie == null) {
            mRequestMap.remove(target);
        } else {
            mRequestMap.put(target, movie);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                    .sendToTarget();
        }
    }

    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }

    private void handleRequest(final T target) {
        final Movie movie = mRequestMap.get(target);
        if (movie == null) {
            return;
        }

        try{
            ParseUtils.getImdbID(movie);
            ParseUtils.getImdbInfo(movie);
        } catch (IOException e){
            Log.e(FormatUtils.TAG,
                    "Error while loading IMDB info for movie: " + movie.getTitle());
            e.printStackTrace();
        }


        mResponseHandler.post(new Runnable() {
            public void run() {
                if (!mRequestMap.get(target).equals(movie)) {
                    return;
                }
                mRequestMap.remove(target);
                mImdbInfoLoadListener.onImdbInfoLoaded(target);
            }
        });
    }
}
