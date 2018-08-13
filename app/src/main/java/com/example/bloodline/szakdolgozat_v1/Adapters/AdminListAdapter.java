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
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.Firebase;

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
        final AdminUser adminUser = adminUserList.get(position);
        edtName.setText(adminUser.getName());

        //Elfogadó és elutasító gombok
        Button btnYes = view.findViewById(R.id.admbtnYes);
        Button btnNo = view.findViewById(R.id.admbtnNo);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase(Global_Vars.pendingUserRef);
                ref.child(adminUser.getUID()).removeValue();
                ref = new Firebase(Global_Vars.usersRef);
                ref.child(adminUser.getUID()).child("acctype").setValue(true);
                adminUserList.remove(position);
                notifyDataSetChanged();

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase(Global_Vars.pendingUserRef);
                ref.child(adminUser.getUID()).removeValue();
                adminUserList.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
