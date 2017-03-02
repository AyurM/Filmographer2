package ru.ayurmar.filmographer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import ru.ayurmar.filmographer.discover.DiscoverFragment;
import ru.ayurmar.filmographer.filter.FilterFragment;

public class FilmographerActivity extends SingleFragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBar;
    private DiscoverFragment mDiscoverFragment;
    private FilterFragment mFilterFragment;

    @Override
    protected Fragment createFragment() {
        mDiscoverFragment = new DiscoverFragment();
        return mDiscoverFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        NavigationView navDrawer = (NavigationView) findViewById(R.id.navigation_drawer);
        setupDrawerContent(navDrawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.addDrawerListener(mActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBar.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBar.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBar.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        switch(menuItem.getItemId()){
            case R.id.menu_drawer_discover:
                if(fragment.getClass() != DiscoverFragment.class){
                    if(mDiscoverFragment == null){
                        mDiscoverFragment = new DiscoverFragment();
                        fm.beginTransaction().replace(R.id.fragment_container, mDiscoverFragment)
                                .addToBackStack(DiscoverFragment.FRAGMENT_TAG).commit();
                    }
                    else{
                        fm.popBackStack(DiscoverFragment.FRAGMENT_TAG,
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
                break;
            case R.id.menu_drawer_watch_list:
                break;
            case R.id.menu_drawer_filter:
                if(fragment.getClass() != FilterFragment.class){
                    if(mFilterFragment == null){
                        mFilterFragment = new FilterFragment();
                        fm.beginTransaction().replace(R.id.fragment_container, mFilterFragment)
                                .addToBackStack(FilterFragment.FRAGMENT_TAG).commit();
                    }
                    else{
                        fm.popBackStack(FilterFragment.FRAGMENT_TAG,
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
                break;
            default:
        }
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
    }
}
