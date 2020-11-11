package me.gustavozapata.spotter.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.gustavozapata.spotter.R;
import me.gustavozapata.spotter.SpotCheckRow;

public class ListAdapter extends BaseAdapter {
    ArrayList<SpotCheckRow> list;
    Context c;

    public ListAdapter(Context context) {
        c = context;
        list = new ArrayList<>();
        list.add(new SpotCheckRow("18 October 2020", "TW12 2XR", "UK PL8TE", "Mazda", "3 2008", "No action required"));
        list.add(new SpotCheckRow("11 Octover 2020", "KT3 7AS", "UK AER33", "BMW", "XLL", "Produced documents"));
        list.add(new SpotCheckRow("24 September 2020", "SW12 4DG", "RU 34RTY", "Audi", "3000", "No action required"));
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
        View row = layoutInflater.inflate(R.layout.spot_check_row, null);

        SpotCheckRow temp = list.get(i);
        TextView rowPlate = row.findViewById(R.id.rowPlate);
        TextView rowResult = row.findViewById(R.id.rowResult);
        TextView rowDate = row.findViewById(R.id.rowDate);
        rowPlate.setText(temp.plateNumber);
        rowResult.setText(temp.result);
        rowDate.setText(temp.date);

        return row;
    }
}