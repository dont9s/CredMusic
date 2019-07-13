package com.gojek.weather.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gojek.weather.R;
import com.gojek.weather.helper.DateUtil;
import com.gojek.weather.service.model.Forecastday;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {


    private List<Forecastday> forecastdays;

    public ForecastAdapter(List<Forecastday> forecastdays) {
        this.forecastdays = forecastdays;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);


        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {

        Forecastday day = forecastdays.get(position);

        if (holder != null) {
            holder.tvForecastTemp.setText(holder.itemView.getContext().getString(R.string.temp_celcius,
                    String.valueOf(day.getDay().getAvgtempC())));

            holder.tvDay.setText(DateUtil.getDayOfWeek(day.getDate()));
        }
    }

    @Override
    public int getItemCount() {
        return forecastdays.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDay, tvForecastTemp;


        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvForecastTemp = itemView.findViewById(R.id.tv_forecast_temp);

        }
    }
}
