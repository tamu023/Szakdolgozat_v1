package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.os.Bundle;
import android.app.Fragment;
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
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


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
                    if (check_Parameters()) {
                        //elfogadott adatok beírása az osztályba
                        Functions.setName(edtName.getText().toString());
                        Functions.setHeight(Long.parseLong(edtHeight.getText().toString()));
                        Functions.setWeight(Long.parseLong(edtWeight.getText().toString()));
                        Functions.setBmiindex(Functions.calcBMI(Functions.getWeight(), Functions.getHeight()));
                        //adatok beírása adatbázisba
                        Functions.UpdateUserinfo(false);
                        btnCancel.callOnClick();
                        Toast.makeText(getActivity().getApplicationContext(), "Updating User Informations are completed.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                    }
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

        //adatok lekérése adatbázisból
        Firebase ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot masodikszint : dataSnapshot.getChildren()) {
                    switch (masodikszint.getKey()) {
                        case "name":
                            Functions.setName(masodikszint.getValue().toString());
                            break;
                        case "email":
                            Functions.setEmail(masodikszint.getValue().toString());
                            break;
                        case "cukorbetegseg":
                            Functions.setCukorbetegseg((boolean) masodikszint.getValue());
                            break;
                        case "liszterzekenyeg":
                            Functions.setLiszterzekenyseg((boolean) masodikszint.getValue());
                            break;
                        case "weight":
                            Functions.setWeight((long) masodikszint.getValue());
                            break;
                        case "height":
                            Functions.setHeight((long) masodikszint.getValue());
                            break;
                        case "gender":
                            Functions.setGender((boolean) masodikszint.getValue());
                            break;
                        case "bmiindex":
                            Functions.setBmiindex((double) masodikszint.getValue());
                            break;
                        case "laktozerzekenyseg":
                            Functions.setLaktozerzekenyseg((boolean) masodikszint.getValue());
                            break;
                        case "acctype":
                            Functions.setAcctype((boolean) masodikszint.getValue());
                            break;
                        default:
                    }
                }
                set_Variables();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void set_Variables() {
        edtName.setText(Functions.getName());
        edtHeight.setText(Functions.getHeight() + "");
        edtWeight.setText(Functions.getWeight() + "");
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

    private boolean check_Parameters() {
        boolean ok = true;
        if (edtName.getText().toString().isEmpty() || edtHeight.getText().toString().isEmpty() || edtWeight.getText().toString().isEmpty()) {
            ok = false;
        }
        return ok;
    }
}
