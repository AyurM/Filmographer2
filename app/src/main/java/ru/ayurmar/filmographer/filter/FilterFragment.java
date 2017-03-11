package ru.ayurmar.filmographer.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import ru.ayurmar.filmographer.R;
import ru.ayurmar.filmographer.model.Parameters;

/**
 * Экран настроек фильтра фильмов, Аюр М., 10.08.2016.
 */

public class FilterFragment extends Fragment {

    private Parameters mParameters;
    private int mSelectedGenre, mSelectedSortOrder;
    private NumberPicker mDateGtePicker, mDateLtePicker;
    private boolean mParametersChanged;

    @Override
    public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setRetainInstance(true);
         mParameters = new Parameters();
         mParameters.loadParameters(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter_settings, container, false);

        mDateGtePicker = (NumberPicker) v.findViewById(R.id.release_date_gte_picker);
        mDateLtePicker = (NumberPicker) v.findViewById(R.id.release_date_lte_picker);
        //выставить диапазон дат, исходя из параметров
        mDateGtePicker.setMinValue(Parameters.DEFAULT_MIN_RELEASE_DATE);
        mDateGtePicker.setMaxValue(Parameters.DEFAULT_MAX_RELEASE_DATE);
        mDateGtePicker.setValue(Integer
                .parseInt(mParameters.getParameter(Parameters.RELEASE_DATE_GTE)
                        .substring(0, 4)));
        mDateLtePicker.setMinValue(mDateGtePicker.getValue());
        mDateLtePicker.setMaxValue(Parameters.DEFAULT_MAX_RELEASE_DATE);
        mDateLtePicker.setValue(Integer
                .parseInt(mParameters.getParameter(Parameters.RELEASE_DATE_LTE)
                        .substring(0, 4)));
        //слушатели изменений дат
        mDateGtePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                mParameters.setParameter(Parameters.RELEASE_DATE_GTE,
                        yearToDateGTE(newVal));    //обновить значение даты "не ранее"
                mParametersChanged = true;
                mDateLtePicker.setMinValue(newVal);    //обновить диапазон второго пикера
            }
        });
        mDateLtePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                mParameters.setParameter(Parameters.RELEASE_DATE_LTE,
                        yearToDateLTE(newVal));    //обновить значение даты "не позднее"
                mParametersChanged = true;
                mDateGtePicker.setMaxValue(newVal);    //обновить диапазон второго пикера
            }
        });

        Spinner genreSpinner = (Spinner) v.findViewById(R.id.filter_spinner_genre);
        Spinner sortOrderSpinner = (Spinner) v.findViewById(R.id.filter_spinner_sort_order);
        //выставить выбранный ранее жанр и порядок сортировки
        mSelectedGenre = findSelectedGenrePosition(mParameters);
        genreSpinner.setSelection(mSelectedGenre);
//        mSelectedSortOrder = mParameters.getSortOrder();
        sortOrderSpinner.setSelection(mSelectedSortOrder);

        //слушатель изменений жанра
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != mSelectedGenre){
                    String[] genreIds = getResources().getStringArray(R.array.filter_genres_ids);
                    mParameters.setParameter(Parameters.WITH_GENRES, i == 0 ? "" : genreIds[i]);
                    mParametersChanged = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //слушатель изменений порядка сортировки результатов
        sortOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i != mSelectedSortOrder){
//                    mParameters.setSortOrder(i);
//                    mParametersChanged = true;
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //сменить заголовок
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(getResources().getString(R.string.menu_filter_parameters_text));
        }
        return v;
    }

    @Override
    public void onPause(){
        super.onPause();
        if(mParametersChanged){
            mParameters.saveParameters(getActivity());
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mParameters.loadParameters(getActivity());
        mParametersChanged = false;
    }

    private String yearToDateGTE(int year){
        return year + Parameters.DEFAULT_MMDD;
    }

    private String yearToDateLTE(int year){
        String result = Integer.toString(year);
        if(year == Parameters.DEFAULT_MAX_RELEASE_DATE){
            result += Parameters.CURRENT_MMDD;
        } else {
            result += Parameters.MAX_MMDD;
        }
        return result;
    }
	
    private int findSelectedGenrePosition(Parameters parameters){
        String selectedGenre = parameters.getParameter(Parameters.WITH_GENRES);
        if(selectedGenre == null){
            return 0;
        } else {
            String[] genreIds = getResources().getStringArray(R.array.filter_genres_ids);
            for(int i = 1; i < genreIds.length; i++){
                if(genreIds[i].equals(selectedGenre)){
                    return i;
                }
            }
        }
        return 0;
    }
}