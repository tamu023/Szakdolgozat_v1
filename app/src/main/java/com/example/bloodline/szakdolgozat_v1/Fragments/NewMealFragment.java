package com.example.bloodline.szakdolgozat_v1.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.R;


public class NewMealFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_meal, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView txtFlour = view.findViewById(R.id.newmealTxtFlour);
        final TextView txtMilk = view.findViewById(R.id.newmealTxtMilk);
        final TextView txtMeat = view.findViewById(R.id.newmealTxtMeat);
        final TextView txtPortion = view.findViewById(R.id.newmealTxtPortion);
        Button btnSub = view.findViewById(R.id.newmealBtnSub);
        Button btnAdd = view.findViewById(R.id.newmealBtnAdd);
        Button btnSearch = view.findViewById(R.id.newmealBtnSearch);
        SeekBar sbFlour = view.findViewById(R.id.newmealSbFlour);
        SeekBar sbMilk = view.findViewById(R.id.newmealSbMilk);
        SeekBar sbMeat = view.findViewById(R.id.newmealSbMeat);

        //preset seekbars
        if (Functions.getLaktozerzekenyseg()) {
            sbMilk.setProgress(0);
            ChangeFilter(txtMilk, 0);
        }
        if (Functions.getLiszterzekenyseg()) {
            sbFlour.setProgress(0);
            ChangeFilter(txtFlour, 0);
        }

        //seekbar mozgatására változik a textview színe
        sbFlour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ChangeFilter(txtFlour, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbMilk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ChangeFilter(txtMilk, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbMeat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ChangeFilter(txtMeat, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(txtPortion.getText().toString()) == 1) {
                    txtPortion.setText("10");
                } else {
                    txtPortion.setText(Integer.toString(Integer.parseInt(txtPortion.getText().toString()) - 1));
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(txtPortion.getText().toString()) == 10) {
                    txtPortion.setText("1");
                } else {
                    txtPortion.setText(Integer.toString(Integer.parseInt(txtPortion.getText().toString()) + 1));
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void ChangeFilter(TextView txt, int progress) {
        switch (progress) {
            case 0:
                txt.setBackgroundColor(0xffff0000);
                break;
            case 1:
                txt.setBackgroundColor(0xff8f8f8f);
                break;
            case 2:
                txt.setBackgroundColor(0xff00ff00);
                break;
        }
    }

}
