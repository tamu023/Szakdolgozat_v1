package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.Classes.AdminUser;
import com.example.bloodline.szakdolgozat_v1.R;

import java.util.List;


public class AdminListAdapter extends ArrayAdapter<AdminUser> {
    private Context context;
    private int resource;
    private List<AdminUser> adminUserList;

    public AdminListAdapter(Context context, int resource, List<AdminUser> adminUserList) {
        super(context, resource, adminUserList);
        this.context = context;
        this.resource = resource;
        this.adminUserList = adminUserList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        TextView edtName = view.findViewById(R.id.admtxtName);
        AdminUser adminUser = adminUserList.get(position);
        edtName.setText(adminUser.getName());

        //Elfogadó és elutasító gombok
        Button btnYes = view.findViewById(R.id.admbtnYes);
        Button btnNo = view.findViewById(R.id.admbtnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminUserList.remove(position);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO megváltoztatni az adott USER acctypját TRUE ra és törölni a listából
                adminUserList.remove(position);
            }
        });
        return view;
    }
}
