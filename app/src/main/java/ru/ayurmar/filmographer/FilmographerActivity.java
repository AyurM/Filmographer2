package ru.ayurmar.filmographer;

import android.support.v4.app.Fragment;

import ru.ayurmar.filmographer.discover.DiscoverFragment;

public class FilmographerActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new DiscoverFragment();
    }
}
