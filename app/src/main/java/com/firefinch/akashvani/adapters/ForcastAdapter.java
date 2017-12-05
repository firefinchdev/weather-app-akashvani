package com.firefinch.akashvani.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firefinch.akashvani.R;
import com.firefinch.akashvani.models.Weather;
import com.firefinch.akashvani.utils.PrefManager;
import com.firefinch.akashvani.utils.Util;

import java.util.ArrayList;

/**
 * Created by Nitin on 10/5/2017.
 */

public class ForcastAdapter extends RecyclerView.Adapter<ForcastAdapter.viewHolder>{

    ArrayList<Weather> weather;
    Context context;
    PrefManager pm;
    boolean inFahrenheit;

    public void update(ArrayList<Weather> weather){
        this.weather = weather;
        notifyDataSetChanged();

    }
    public ForcastAdapter(Context context, ArrayList<Weather> weather) {
        this.context = context;
        this.weather = weather;
        pm = new PrefManager(context);
        inFahrenheit = pm.isTempFahrenheit();

    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_forecast,parent,false));
    }



    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.time.setText(Util.getTimeFromUNIX(weather.get(position).getTime(),2));
        holder.summary.setText(weather.get(position).getSummary());
        holder.icon.setImageResource(getImgId(weather.get(position).getIcon()));
        if(inFahrenheit){
            holder.temperature.setText(String.valueOf(Util.getFehreneitFromCelcius(weather.get(position).getTemperatureHigh()).intValue()));
        } else {
            holder.temperature.setText(String.valueOf(weather.get(position).getTemperatureHigh().intValue()));
        }
       // holder.temperature.setText(weather.get(position).getTemperatureHigh().intValue().toString());
    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView summary;
        ImageView icon;
        TextView temperature;
        public viewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            summary = itemView.findViewById(R.id.summary);
            icon = itemView.findViewById(R.id.icon);
            temperature = itemView.findViewById(R.id.temperature);
        }
    }

    public int getImgId(String iconName){
        int imgId = R.drawable.defaultday;
        switch (iconName){
            case "clear-day":
                imgId = R.drawable.clear_day;
                break;
            case "clear-night":
                imgId = R.drawable.clear_night;
                break;
            case "rain":
                imgId = R.drawable.rain;
                break;
            case "snow":
                imgId = R.drawable.snow;
                break;
            case "sleet":
                imgId = R.drawable.sleet;
                break;
            case "wind":
                imgId = R.drawable.wind;
                break;
            case "fog":
                imgId = R.drawable.fog;
                break;
            case "cloudy":
                imgId = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                imgId = R.drawable.party_cloudy_day;
                break;
            case "partly-cloudy-night":
                imgId = R.drawable.party_cloudy_night;
                break;
        }
        return  imgId;
    }

}
