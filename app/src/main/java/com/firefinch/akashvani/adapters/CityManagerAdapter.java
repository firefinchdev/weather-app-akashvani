package com.firefinch.akashvani.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firefinch.akashvani.R;
import com.firefinch.akashvani.db.LocationDbHelper;
import com.firefinch.akashvani.db.tables.LocationTable;
import com.firefinch.akashvani.models.Location;

import java.util.ArrayList;

/**
 * Created by Nitin on 10/10/2017.
 */

public class CityManagerAdapter extends RecyclerView.Adapter<CityManagerAdapter.CityManagerViewHolder> {

    Context context;
    ArrayList<Location> locations = new ArrayList<>();
    SQLiteDatabase db;
    Activity activity;

    public CityManagerAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        db = new LocationDbHelper(context).getWritableDatabase();
        locations = LocationTable.getAllLocations(db);
    }

    public void update(){
        locations = LocationTable.getAllLocations(db);
        notifyDataSetChanged();
    }

    @Override
    public CityManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_city_manager,parent,false);
        return new CityManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CityManagerViewHolder holder, final int position) {
        holder.tvCityName.setText(locations.get(position).getName());
        holder.ivCityDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationTable.deleteLocation(db,locations.get(position).getId());
                update();
            }
        });
        holder.ivCityEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                View view = activity.getLayoutInflater().inflate(R.layout.layout_city_edit,null);
                mBuilder.setView(view);
                final AlertDialog alertDialog = mBuilder.create();
                final EditText etName = view.findViewById(R.id.etName);
                Button btnOK = view.findViewById(R.id.btnOK);
                etName.setText(locations.get(position).getName());
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Location location = locations.get(position);
                        location.setName(etName.getText().toString());
                        LocationTable.deleteLocation(db,locations.get(position).getId());
                        LocationTable.insertLocation(db,location);
                        update();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class CityManagerViewHolder extends RecyclerView.ViewHolder{
        TextView tvCityName;
        ImageView ivCityDelete;
        ImageView ivCityEdit;
        public CityManagerViewHolder(View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvCityName);
            ivCityDelete = itemView.findViewById(R.id.ivCityDelete);
            ivCityEdit = itemView.findViewById(R.id.ivCityEdit);
        }
    }
}
