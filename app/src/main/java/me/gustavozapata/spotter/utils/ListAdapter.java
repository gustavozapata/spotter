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

import static me.gustavozapata.spotter.utils.SpotCheckUtils.convertDateToString;

public class ListAdapter extends BaseAdapter {
    List<SpotCheck> list;
    Context c;

    public ListAdapter(Context context, List<SpotCheck> lista) {
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
        View row = layoutInflater.inflate(R.layout.spot_check_row, null);

        SpotCheck temp = list.get(i);
        TextView rowPlate = row.findViewById(R.id.rowPlate);
        TextView rowResult = row.findViewById(R.id.rowResult);
        TextView rowDate = row.findViewById(R.id.rowDate);
        rowPlate.setText(temp.getNumberPlate());
        rowResult.setText(temp.getResult());
        rowDate.setText(convertDateToString(temp.getDate()));

        return row;
    }
}