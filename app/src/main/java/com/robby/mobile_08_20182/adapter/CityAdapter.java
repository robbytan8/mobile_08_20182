package com.robby.mobile_08_20182.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robby.mobile_08_20182.R;
import com.robby.mobile_08_20182.entity.City;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Robby
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private ArrayList<City> cities;
    private DataClickListener listener;
    private MenuClickListener menuClickListener;

    public ArrayList<City> getCities() {
        if (cities == null) {
            cities = new ArrayList<>();
        }
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.getCities().clear();
        this.getCities().addAll(cities);
        notifyDataSetChanged();
    }

    public void setListener(DataClickListener listener) {
        this.listener = listener;
    }

    public void setMenuClickListener(MenuClickListener menuClickListener) {
        this.menuClickListener = menuClickListener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CityViewHolder cityViewHolder, int i) {
        final City city = getCities().get(i);
        cityViewHolder.txtId.setText(String.valueOf(city.getId()));
        cityViewHolder.txtName.setText(city.getName());
        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCityClickedListener(city);
            }
        });
        cityViewHolder.txtOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuClickListener.onMenuClickListener(city, cityViewHolder.txtOptions);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getCities().size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_id)
        TextView txtId;
        @BindView(R.id.tv_name)
        TextView txtName;
        @BindView(R.id.tv_options)
        TextView txtOptions;

        CityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface DataClickListener {

        void onCityClickedListener(City city);
    }

    public interface MenuClickListener {

        void onMenuClickListener(City city, View view);
    }
}
