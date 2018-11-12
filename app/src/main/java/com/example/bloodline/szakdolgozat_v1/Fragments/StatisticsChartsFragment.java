package com.example.bloodline.szakdolgozat_v1.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class StatisticsChartsFragment extends Fragment {

    private ArrayList<String> nevek;
    private ArrayList<Double> mennyiseg;
    private ArrayList<BarEntry> entries;
    private ArrayList<String> labels;
    private BarChart barChart;
    private boolean unit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics__charts, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nevek = new ArrayList<>();
        mennyiseg = new ArrayList<>();
        entries = new ArrayList<>();
        labels = new ArrayList<>();
        barChart = view.findViewById(R.id.barchart);

        //olvasás SharedPreferences-ből
        SharedPreferences prefs = getContext().getSharedPreferences("unit", Context.MODE_PRIVATE);
        unit = prefs.getBoolean("unit", true);
        Firebase ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("statistics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    if ((boolean) elsoszint.child("unit").getValue() == unit) {
                        nevek.add(elsoszint.getKey());
                        mennyiseg.add((double) elsoszint.child("quantity").getValue());
                    }
                }
                int count;
                if (mennyiseg.size() != 0) {
                    if (mennyiseg.size() < 5) {
                        count = mennyiseg.size();
                    } else {
                        count = 5;
                    }
                    for (int i = 0; i < count; i++) {
                        double max = mennyiseg.get(0);
                        for (int j = 0; j < mennyiseg.size(); j++) {
                            if (max < mennyiseg.get(j)) {
                                max = mennyiseg.get(j);
                            }
                            if (j == mennyiseg.size() - 1) {
                                max = Math.round(max);
                                float f = (float) max;
                                entries.add(new BarEntry(i, f));
                                labels.add(nevek.get(j));
                                mennyiseg.remove(j);
                                nevek.remove(j);
                            }
                        }
                    }
                    BarDataSet dataset = new BarDataSet(entries, "Cells");
                    BarData data = new BarData(dataset);
                    XAxis xAxis = barChart.getXAxis();
                    dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    barChart.setData(data); // set the data and list of labels into chart
                } else {
                    //nem készít chartot mert nincs statisztikában semmi
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*entries.add(new BarEntry(1, 20));
        entries.add(new BarEntry(2, 12));
        entries.add(new BarEntry(3, 4));
        entries.add(new BarEntry(4, 5));
        entries.add(new BarEntry(5, 11));
        entries.add(new BarEntry(6, 9));

        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");
        BarDataSet dataset = new BarDataSet(entries, "Cells");
        BarData data = new BarData( dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data); // set the data and list of labels into chart*/
    }
}
