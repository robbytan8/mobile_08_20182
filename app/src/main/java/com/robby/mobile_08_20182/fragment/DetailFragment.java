package com.robby.mobile_08_20182.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robby.mobile_08_20182.R;
import com.robby.mobile_08_20182.entity.WeatherResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Robby
 */
public class DetailFragment extends Fragment {

    @BindView(R.id.tv_city_name)
    TextView txtCityName;
    @BindView(R.id.tv_latitude)
    TextView txtLatitude;
    @BindView(R.id.tv_longitude)
    TextView txtLongitude;
    @BindView(R.id.tv_temperature)
    TextView txtTemperature;
    @BindView(R.id.tv_humidity)
    TextView txtHumidity;
    @BindView(R.id.tv_pressure)
    TextView txtPressure;
    @BindView(R.id.tv_min_temp)
    TextView txtMinTemp;
    @BindView(R.id.tv_max_temp)
    TextView txtMaxTemp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null && getArguments().containsKey(WeatherResponse.WEATHER_DET)) {
            WeatherResponse weather = getArguments().getParcelable(WeatherResponse.WEATHER_DET);
            if (weather != null) {
                txtCityName.setText(weather.getCityName());
                txtLatitude.setText(String.valueOf(weather.getCoordinate().getLatitude()));
                txtLongitude.setText(String.valueOf(weather.getCoordinate().getLongitude()));
                txtTemperature.setText(String.valueOf(weather.getMain().getTemperature()));
                txtHumidity.setText(String.valueOf(weather.getMain().getHumidity()));
                txtPressure.setText(String.valueOf(weather.getMain().getPressure()));
                txtMinTemp.setText(String.valueOf(weather.getMain().getMinTemp()));
                txtMaxTemp.setText(String.valueOf(weather.getMain().getMaxTemp()));
            }
        }
    }

}
