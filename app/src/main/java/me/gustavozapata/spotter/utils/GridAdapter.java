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

import static me.gustavozapata.spotter.utils.SpotCheckUtils.colourResult;
import static me.gustavozapata.spotter.utils.SpotCheckUtils.convertDateToString;

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

    public SpotCheck getSpotAt(int position){
        return list.get(position);
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

        SpotCheck temp = list.get(i);

        TextView date = row.findViewById(R.id.textViewSpotCheckDate);
        TextView location = row.findViewById(R.id.textViewSpotCheckLocation);
        TextView plateNumber = row.findViewById(R.id.textViewCarPlate);
        TextView carMake = row.findViewById(R.id.textViewCar);
        TextView result = row.findViewById(R.id.textViewResult);
        colourResult(temp.getResult(), result);

        date.setText(convertDateToString(temp.getDate()));
        location.setText(temp.getLocation());
        plateNumber.setText(temp.getNumberPlate());
        String car = temp.getCarMake() + " - " + temp.getCarModel();
        carMake.setText(car);
        result.setText(temp.getResult());

        return row;
    }
}