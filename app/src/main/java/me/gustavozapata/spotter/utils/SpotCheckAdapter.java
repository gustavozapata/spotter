package me.gustavozapata.spotter.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.gustavozapata.spotter.R;
import me.gustavozapata.spotter.model.SpotCheck;

//TODO: RecyclerView in case it's needed
public class SpotCheckAdapter extends RecyclerView.Adapter<SpotCheckAdapter.SpotCheckViewHolder> {

    private List<SpotCheck> spotChecks = new ArrayList<>();

    @NonNull
    @Override
    public SpotCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spot_check_card, parent, false);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spot_check_row, parent, false);
        return new SpotCheckViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpotCheckViewHolder holder, int position) {
        SpotCheck currentSpot = spotChecks.get(position);
//        holder.date.setText(currentSpot.getDate());
        holder.location.setText(currentSpot.getLocation());
        holder.numberPlate.setText(currentSpot.getNumberPlate());
        String car = currentSpot.getCarMake() + " - " + currentSpot.getCarModel();
        holder.carMake.setText(car);
        holder.result.setText(currentSpot.getResult());
    }

    @Override
    public int getItemCount() {
        return spotChecks.size();
    }

    public void setSpotChecks(List<SpotCheck> spots){
        this.spotChecks = spots;
        notifyDataSetChanged();
    }

    class SpotCheckViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView location;
        private TextView numberPlate;
        private TextView carMake;
        private TextView result;

        public SpotCheckViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textViewSpotCheckDate);
            location = itemView.findViewById(R.id.textViewSpotCheckLocation);
            numberPlate = itemView.findViewById(R.id.textViewCarPlate);
            carMake = itemView.findViewById(R.id.textViewCar);
            result = itemView.findViewById(R.id.textViewResult);
        }
    }
}
