package com.mashumaro.weattherheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class TodayForecastAdapter extends RecyclerView.Adapter<TodayForecastAdapter.TFViewHolder> {

    private Context context;
    private ArrayList<HourlyWeather> weatherArrayList;

    public TodayForecastAdapter(Context context, ArrayList<HourlyWeather> weatherArrayList) {
        this.context = context;
        this.weatherArrayList = weatherArrayList;
    }

    @NonNull
    @Override
    public TodayForecastAdapter.TFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todayforecast_rv_item, parent, false);
        return new TFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayForecastAdapter.TFViewHolder holder, int position) {

        HourlyWeather weather = weatherArrayList.get(position);
        String windDir = weather.getWindDirection();

        holder.temperatureTV.setText(weather.getTemperature().concat("°C"));
        holder.iconIV.setImageResource(context.getResources().getIdentifier("drawable/i" + weather.getIcon(), null, context.getPackageName()));
        holder.windSpeedTV.setText(weather.getWindSpeed().concat("km/h"));
        holder.windDirectionIV.setRotation(Float.parseFloat(windDir));

        Date hour = new Date(Long.parseLong(weather.getTime()) * 1000L);
        DateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
        String formattedHour = format.format(hour);
        holder.timeTV.setText(formattedHour);

    }

    @Override
    public int getItemCount() {
        return weatherArrayList.size();
    }

    public class TFViewHolder extends RecyclerView.ViewHolder{

        private TextView timeTV, temperatureTV, windSpeedTV;
        private ImageView iconIV, windDirectionIV;

        public TFViewHolder(@NonNull View itemView) {
            super(itemView);

            timeTV = itemView.findViewById(R.id.time);
            temperatureTV = itemView.findViewById(R.id.temperature);
            windSpeedTV = itemView.findViewById(R.id.windSpeed);
            iconIV = itemView.findViewById(R.id.icon);
            windDirectionIV = itemView.findViewById(R.id.windDirection);
        }
    }
}
