package me.gustavozapata.spotter.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.gustavozapata.spotter.R;
import me.gustavozapata.spotter.model.SpotCheck;

public class GridAdapter extends BaseAdapter {
    List<SpotCheck> list;
    Context c;

    public GridAdapter(Context context, List<SpotCheck> lista) {
        c = context;
        list = lista;
    }

    public void setSpotChecks(List<SpotCheck> spots){
        this.list = spots;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View row = layoutInflater.inflate(R.layout.spot_check_card, null);

        TextView date = row.findViewById(R.id.textViewSpotCheckDate);
        TextView location = row.findViewById(R.id.textViewSpotCheckLocation);
        TextView plateNumber = row.findViewById(R.id.textViewCarPlate);
        TextView carMake = row.findViewById(R.id.textViewCar);
        TextView result = row.findViewById(R.id.textViewResult);

        TextView elID = row.findViewById(R.id.elID);

        SpotCheck temp = list.get(i);
        date.setText(temp.getDate());
        location.setText(temp.getLocation());
        plateNumber.setText(temp.getNumberPlate());
        String car = temp.getCarMake() + " - " + temp.getCarModel();
        carMake.setText(car);
        result.setText(temp.getResult());

        elID.setText(temp.getId());

        return row;
    }
}