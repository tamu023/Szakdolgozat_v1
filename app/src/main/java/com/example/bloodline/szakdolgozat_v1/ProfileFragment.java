package com.example.bloodline.szakdolgozat_v1;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class ProfileFragment extends Fragment {

    private EditText edtName;
    private EditText edtHeight;
    private EditText edtWeight;
    private RadioGroup rbgGender;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private Switch swCukor;
    private Switch swLiszt;
    private Switch swLaktoz;
    private Button btnModify;
    private Button btnCancel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    //TODO folytatni a profil kinézetét
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtName = view.findViewById(R.id.prfName);
        edtHeight = view.findViewById(R.id.prfHeight);
        edtWeight = view.findViewById(R.id.prfWeight);
        rbgGender = view.findViewById(R.id.prfGender);
        rbMale = view.findViewById(R.id.prfMale);
        rbFemale = view.findViewById(R.id.prfFemale);
        swCukor = view.findViewById(R.id.prfCukor);
        swLiszt = view.findViewById(R.id.prfLiszt);
        swLaktoz = view.findViewById(R.id.prfLaktoz);
        btnModify = view.findViewById(R.id.prfModify);
        btnCancel = view.findViewById(R.id.prfCancel);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnModify.getText().equals("Modify")) {
                    edtName.setEnabled(true);
                    edtHeight.setEnabled(true);
                    edtWeight.setEnabled(true);
                    rbMale.setEnabled(true);
                    rbFemale.setEnabled(true);
                    swCukor.setEnabled(true);
                    swLiszt.setEnabled(true);
                    swLaktoz.setEnabled(true);
                    btnModify.setText("Confirm");
                    btnCancel.setVisibility(View.VISIBLE);
                } else if (btnModify.getText().equals("Confirm")) {
                    //TODO módosítások elmentése az adatbázisba
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setEnabled(false);
                edtHeight.setEnabled(false);
                edtWeight.setEnabled(false);
                rbMale.setEnabled(false);
                rbFemale.setEnabled(false);
                swCukor.setEnabled(false);
                swLiszt.setEnabled(false);
                swLaktoz.setEnabled(false);
                btnModify.setText("Modify");
                btnCancel.setVisibility(View.GONE);
            }
        });

        //Késleltetés mert a Firebase olvasás aszinkron ütemű és enélkül gyorsan lefut és üres mezőket tölt be
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                set_Variables();
            }
        }, 1000);  //setting 10 second delay : 1000 = 1 second


    }

    private void set_Variables() {
        edtName.setText(Functions.getName());
        edtHeight.setText(Functions.getHeight() + " cm");
        edtWeight.setText(Functions.getWeight() + " kg");
        if (Functions.getGender()) {
            rbgGender.check(R.id.prfMale);
        } else {
            rbgGender.check(R.id.prfFemale);
        }
        if (Functions.getCukorbetegseg()) {
            swCukor.setChecked(true);
        }
        if (Functions.getLiszterzekenyseg()) {
            swLiszt.setChecked(true);
        }
        if (Functions.getLaktozerzekenyseg()) {
            swLaktoz.setChecked(true);
        }
    }
}
