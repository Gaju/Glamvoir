package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.activity.AddStory;
import glamvoir.appzstack.glamvoir.model.net.response.CityResponse;

/**
 * Created by jai on 11/2/2015.
 */
public class CustomCitySpinnerAdapter extends ArrayAdapter<CityResponse.City> {

    private Context context1;
    private List<CityResponse.City> data;
    public Resources res;
    LayoutInflater inflater;

    public CustomCitySpinnerAdapter(Context context, List<CityResponse.City> objects) {
        super(context, R.layout.spinner_row, objects);

        this.context1 = context;
        this.data = objects;

        inflater = (LayoutInflater) context1
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.spinner_row, parent, false);

        TextView tvCategory = (TextView) row.findViewById(R.id.tvCategory);
  /*      for (int j = 0; j < list.size(); j++) {
           *//* Button btnTag = new Button(this);
            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnTag.setText(list.get(j).city_name);
            btnTag.setId(j + 1);
            row.addView(btnTag);*//*
            bt1.setText(data.get(j).city_name);
           ;


        }*/

        tvCategory.setText(data.get(position).city_name);

        return row;
    }
}
