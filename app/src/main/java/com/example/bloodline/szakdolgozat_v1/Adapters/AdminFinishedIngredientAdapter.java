package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.Firebase;


import java.util.List;

public class AdminFinishedIngredientAdapter extends ArrayAdapter<FinishedFood> {
    private Context context;
    private int resource;
    private List<FinishedFood> finishedFoodList;

    public AdminFinishedIngredientAdapter(Context context, int resource, List<FinishedFood> finishedFoodList) {
        super(context, resource, finishedFoodList);
        this.context = context;
        this.resource = resource;
        this.finishedFoodList = finishedFoodList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final FinishedFood finishedFood = finishedFoodList.get(position);

        TextView txtName = view.findViewById(R.id.itmAdmFinTxtName);
        TextView txtKcal = view.findViewById(R.id.itmAdmFinTxtKcal);
        TextView txtFlour = view.findViewById(R.id.itmAdmFinTxtFlour);
        TextView txtMilk = view.findViewById(R.id.itmAdmFinTxtMilk);
        TextView txtMeat = view.findViewById(R.id.itmAdmFinTxtMeat);
        Button btnDecline = view.findViewById(R.id.itmAdmFinBtnDecline);

        txtName.setText(finishedFood.getFoodname());
        txtKcal.setText(finishedFood.getCarb() + "Kcal");

        setColors(finishedFood.getFlour(), txtFlour);
        setColors(finishedFood.getMeat(), txtMeat);
        setColors(finishedFood.getMilk(), txtMilk);

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(parent.getContext());
                a_builder.setMessage("Do you want to remove this Food?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Firebase ref = new Firebase(Global_Vars.finProdRef);
                                ref.child(finishedFood.getFoodname()).removeValue();
                                finishedFoodList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), "Deleted from Database.", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Remove Food");
                alert.show();

            }
        });
        return view;
    }

    private void setColors(boolean bl, TextView txt) {
        if (bl) {
            txt.setBackgroundColor(0xFF5EFF66);
        }
    }
}
