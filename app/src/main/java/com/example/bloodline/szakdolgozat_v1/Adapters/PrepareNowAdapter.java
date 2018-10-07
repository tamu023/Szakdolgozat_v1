package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.Firebase;

import java.util.List;

public class PrepareNowAdapter extends ArrayAdapter<FinishedFood> {
    private Context context;
    private int resource;
    private List<AddProducts> rawFoodList;
    private List<FinishedFood> prepareNowList;
    private List<AddProducts> storageList;
    private List<AddProducts> alterStorageList;
    private Firebase ref;
    private List<Double> maxPortionList;

    public PrepareNowAdapter(@NonNull Context context, int resource, List<FinishedFood> prepareNowList, List<AddProducts> rawFoodList, List<Double> maxPortionList) {
        super(context, resource, prepareNowList);
        this.context = context;
        this.resource = resource;
        this.prepareNowList = prepareNowList;
        this.rawFoodList = rawFoodList;
        this.maxPortionList = maxPortionList;
    }

    @NonNull
    @Override
    //TODO befejezni
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final FinishedFood prepareNowItem = prepareNowList.get(position);
        final double maxPortion = maxPortionList.get(position);

        LinearLayout linButton = view.findViewById(R.id.itmPrepNowLin);
        TextView txtName = view.findViewById(R.id.itmPrepNowTxtName);
        TextView txtKcal = view.findViewById(R.id.itmPrepNowTxtKcal);
        TextView txtTime = view.findViewById(R.id.itmPrepNowTxtTime);
        TextView txtMilk = view.findViewById(R.id.itmPrepNowTxtMilk);
        TextView txtMeat = view.findViewById(R.id.itmPrepNowTxtMeat);
        TextView txtFlour = view.findViewById(R.id.itmPrepNowTxtFlour);
        TextView txtPortion = view.findViewById(R.id.itmPrepNowTxtPortion);

        txtName.setText(prepareNowItem.getFoodname());
        txtKcal.setText(prepareNowItem.getCarb() + " Kcal");
        txtTime.setText((int) Math.floor(prepareNowItem.getPreptime()) + " Min");
        txtPortion.setText((int) Math.floor(maxPortion) + " Portion");

        if (prepareNowItem.getFlour()) {
            txtFlour.setBackgroundColor(0xFF00FF00);
        }
        if (prepareNowItem.getMilk()) {
            txtMilk.setBackgroundColor(0xFF00FF00);
        }
        if (prepareNowItem.getMeat()) {
            txtMeat.setBackgroundColor(0xFF00FF00);
        }

        linButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Set Portion: Min 1, Max  " + (int) Math.floor(maxPortion));
                final EditText input = new EditText(parent.getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int portion = Integer.parseInt(input.getText().toString());
                        if (portion > 0 && portion <= (int) Math.floor(maxPortion)) {
                            //receptfragmensre ugrás
                            dialog.cancel();
                        } else {
                            input.setText(null);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        return view;
    }
}
