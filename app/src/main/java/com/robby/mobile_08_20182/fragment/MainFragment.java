package com.robby.mobile_08_20182.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.robby.mobile_08_20182.BuildConfig;
import com.robby.mobile_08_20182.R;
import com.robby.mobile_08_20182.adapter.CityAdapter;
import com.robby.mobile_08_20182.entity.City;
import com.robby.mobile_08_20182.entity.WeatherResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Robby
 */
public class MainFragment extends Fragment implements CityAdapter.DataClickListener, CityAdapter.MenuClickListener {

    private static final String FRAG_ROT = "frag_rotate";

    @BindView(R.id.rv_data)
    RecyclerView rvData;
    private CityAdapter cityAdapter;
    private DetailFragment detailFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(FRAG_ROT)) {
            getCityAdapter().setCities(savedInstanceState.<City>getParcelableArrayList(FRAG_ROT));
        } else {
            populateCityData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, rootView);
        rvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvData.setAdapter(getCityAdapter());
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(FRAG_ROT, getCityAdapter().getCities());
    }

    @Override
    public void onCityClickedListener(City city) {
        collectWeatherData(city);
    }

    @Override
    public void onMenuClickListener(final City city, View view) {
        PopupMenu popup = new PopupMenu(Objects.requireNonNull(getContext()), view);
        popup.inflate(R.menu.op_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mn_loc:
                        showCityLocation(city);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private CityAdapter getCityAdapter() {
        if (cityAdapter == null) {
            cityAdapter = new CityAdapter();
            cityAdapter.setListener(this);
            cityAdapter.setMenuClickListener(this);
        }
        return cityAdapter;
    }

    private DetailFragment getDetailFragment() {
        if (detailFragment == null) {
            detailFragment = new DetailFragment();
        }
        return detailFragment;
    }

    private void populateCityData() {
        try {
            InputStream inputStream = Objects.requireNonNull(getActivity()).getAssets().open("city.list.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            Gson gson = new Gson();
            City[] cityArr = gson.fromJson(reader, City[].class);
            ArrayList<City> cities = new ArrayList<>(Arrays.asList(cityArr));
            Collections.sort(cities, new Comparator<City>() {
                @Override
                public int compare(City city, City t1) {
                    return city.getName().compareTo(t1.getName());
                }
            });
            getCityAdapter().setCities(cities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void collectWeatherData(final City city) {
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        Uri uri = Uri.parse("https://api.openweathermap.org/data/2.5/weather").buildUpon()
                .appendQueryParameter("id", String.valueOf(city.getId()))
                .appendQueryParameter("appid", BuildConfig.ApiKey)
                .build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Gson gson = new Gson();
                    WeatherResponse weatherResponse = gson.fromJson(jsonObject.toString(), WeatherResponse.class);
                    showWeatherDetails(weatherResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), R.string.response_error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void showWeatherDetails(WeatherResponse weather) {
        if (getActivity().findViewById(R.id.fl_detail) == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(WeatherResponse.WEATHER_DET, weather);
            getDetailFragment().setArguments(bundle);
            FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_container, getDetailFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(WeatherResponse.WEATHER_DET, weather);
            getDetailFragment().setArguments(bundle);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_detail, fragment);
            transaction.commit();
        }
    }

    private void showCityLocation(City city) {
        if (city != null) {
            Uri uri = Uri.parse("geo:" + city.getCoordinate().getLatitude() + "," + city.getCoordinate().getLongitude() + "?q=" + city.getCoordinate().getLatitude() + "," + city.getCoordinate().getLongitude());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
