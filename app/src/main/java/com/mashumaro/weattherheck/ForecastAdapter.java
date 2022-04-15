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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.FViewHolder> {

    private Context context;
    private ArrayList<DailyWeather> weatherArrayList;

    public ForecastAdapter(Context context, ArrayList<DailyWeather> weatherArrayList) {
        this.context = context;
        this.weatherArrayList = weatherArrayList;
    }

    @NonNull
    @Override
    public ForecastAdapter.FViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_rv_item, parent, false);
        return new FViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.FViewHolder holder, int position) {
        DailyWeather weather = weatherArrayList.get(position);
        holder.minMaxTempTV.setText(weather.getMaxTemp().concat("°").concat(" / ").concat(weather.getMinTemp()).concat("°"));
        holder.iconIV.setImageResource(context.getResources().getIdentifier("drawable/i" + weather.getConditionIcon(), null, context.getPackageName()));

        Date date = new Date(Long.parseLong(weather.getDate()) * 1000L);
        DateFormat format = new SimpleDateFormat("dd/MM");
        format.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
        String formattedHour = format.format(date);
        holder.dateAndConditionTV.setText(formattedHour.concat("    ").concat(weather.getCondition()));
    }

    @Override
    public int getItemCount() {
        return weatherArrayList.size();
    }

    public class FViewHolder extends RecyclerView.ViewHolder{

        private TextView dateAndConditionTV, minMaxTempTV;
        private ImageView iconIV;

        public FViewHolder(@NonNull View itemView) {
            super(itemView);

            dateAndConditionTV = itemView.findViewById(R.id.dateAndCondition);
            minMaxTempTV = itemView.findViewById(R.id.minMaxTemp);
            iconIV = itemView.findViewById(R.id.icon);
        }
    }
}
