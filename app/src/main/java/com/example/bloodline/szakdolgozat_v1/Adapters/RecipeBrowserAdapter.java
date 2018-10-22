package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.Fragments.RecipeFragment;
import com.example.bloodline.szakdolgozat_v1.R;

import java.util.List;

public class RecipeBrowserAdapter extends ArrayAdapter<FinishedFood> {

    private Context context;
    private int resource;
    private List<FinishedFood> recipeList;

    public RecipeBrowserAdapter(Context context, int resource, List<FinishedFood> recipeList) {
        super(context, resource, recipeList);
        this.context = context;
        this.resource = resource;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final FinishedFood recipeItem = recipeList.get(position);

        LinearLayout lin = view.findViewById(R.id.recipeBrowserlin);
        TextView txtName = view.findViewById(R.id.itmRawTxtName);
        TextView txtFlour = view.findViewById(R.id.itmRawTxtFluor);
        TextView txtMilk = view.findViewById(R.id.itmRawTxtMilk);
        TextView txtMeat = view.findViewById(R.id.itmRawTxtMeat);

        txtName.setText(recipeItem.getFoodname());
        if (recipeItem.getFlour()) {
            txtFlour.setBackgroundColor(0xFF5EFF66);
        }
        if (recipeItem.getMilk()) {
            txtMilk.setBackgroundColor(0xFF5EFF66);
        }
        if (recipeItem.getMeat()) {
            txtMeat.setBackgroundColor(0xFF5EFF66);
        }

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recept Shared Preferences
                SharedPreferences prefs = parent.getContext().getSharedPreferences("recept", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("recept", "");
                editor.putString("recept", recipeItem.getRecipe());
                editor.apply();
                //food name SharedPreference
                SharedPreferences foodpref = parent.getContext().getSharedPreferences("foodname", Context.MODE_PRIVATE);
                SharedPreferences.Editor foodeditor = foodpref.edit();
                foodeditor.putString("foodname", recipeItem.getFoodname());
                foodeditor.apply();

                Fragment startfragment = new RecipeFragment();
                Context context = parent.getContext();
                FragmentManager fm = ((Activity) context).getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.mainframeplace, startfragment);
                ft.commit();

            }
        });

        return view;
    }
}
