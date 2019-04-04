package com.example.assignment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MeasurementListItemAdapter extends ArrayAdapter<Measurement> {

    private final int resource;

    public MeasurementListItemAdapter(Context context, int resource, Measurement[] objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Measurement measurement = getItem(position);
        long date = measurement.getUtc();
        String user = measurement.getUserId();
        LinearLayout measurementView;
        if (convertView == null) {
            measurementView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, measurementView, true);
        } else {
            measurementView = (LinearLayout) convertView;
        }
        TextView dateView = measurementView.findViewById(R.id.measurementDate);
        TextView userView = measurementView.findViewById(R.id.measurementUser);
        DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        dateView.setText(formatter.format(calendar.getTime()));
        userView.setText("   "+ user);
        return measurementView;
    }
}
