package com.example.bloodline.szakdolgozat_v1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private EditText edtEmail;
    private EditText edtPassw;
    private EditText edtConfpassw;
    private EditText edtName;
    private EditText edtHeight;
    private EditText edtWeight;
    private TextView txtUser;
    private TextView txtAdmin;
    private RadioButton rdbMale;
    private RadioButton rdbFemale;
    private Switch swCukor;
    private Switch swLaktoz;
    private Switch swLiszt;

    private boolean type; //true = admin selected, false = user selected
    private boolean gender; //true = male, false = female
    private boolean checked;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Functions.setmAuth(FirebaseAuth.getInstance());
        Button btnReg = view.findViewById(R.id.regButton);
        TextView txtBack = view.findViewById(R.id.regBack);
        edtEmail = view.findViewById(R.id.regEmail);
        edtPassw = view.findViewById(R.id.regPass);
        edtConfpassw = view.findViewById(R.id.regConfpass);
        edtName = view.findViewById(R.id.regName);
        edtHeight = view.findViewById(R.id.regHeight);
        edtWeight = view.findViewById(R.id.regWeight);
        txtUser = view.findViewById(R.id.regUser);
        txtAdmin = view.findViewById(R.id.regAdmin);
        RadioGroup rbgGender = view.findViewById(R.id.regGrpGender);
        rdbMale = view.findViewById(R.id.regMale);
        rdbFemale = view.findViewById(R.id.regFemale);
        swCukor = view.findViewById(R.id.regCukor);
        swLaktoz = view.findViewById(R.id.regLaktoz);
        swLiszt = view.findViewById(R.id.regLiszt);

        type = false;
        checked = false;
        txtUser.setBackgroundColor(Color.parseColor("#278737"));
        txtAdmin.setBackgroundColor(Color.TRANSPARENT);

        //Reklám
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Back gomb
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.loginfragmentplace, fragment);
                ft.commit();

            }
        });

        //Bejelentkezési gomb
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.setEmail(edtEmail.getText().toString());
                String passw = edtPassw.getText().toString();
                String confpassw = edtConfpassw.getText().toString();

                if (CheckInputs(Functions.getEmail(), passw, confpassw, edtName.getText().toString(), edtHeight.getText().toString(), edtWeight.getText().toString(), checked)) {
                    Register(Functions.getEmail(), passw);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Missing or Invalid parameters.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Felhasználó típus kijelölése
        txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type) {
                    type = false;
                    txtUser.setBackgroundColor(Color.parseColor("#278737"));
                    txtAdmin.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        //Admin típus kijelölése
        txtAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!type) {
                    type = true;
                    txtAdmin.setBackgroundColor(Color.parseColor("#278737"));
                    txtUser.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        //Radiobutton group kezelése
        rbgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checked = true;
                if (rdbFemale.isChecked()) {
                    gender = false;
                } else if (rdbMale.isChecked()) {
                    gender = true;
                }
            }
        });
    }

    private boolean CheckInputs(String email, String password, String confpassword, String name, String height, String weight, boolean checked) {
        boolean ok = true;
        if (email.isEmpty() || password.isEmpty() || confpassword.isEmpty() || !password.equals(confpassword) || name.isEmpty() || height.isEmpty() || weight.isEmpty() || !checked) {
            ok = false;
        }
        //adatok mentése Functions-ba ha megfelelőek az ellenörzés után
        if (ok) {
            setVariables();
        }
        return ok;
    }

    private void Register(String email, String password) {
        Functions.getmAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Functions.setUser(Functions.getmAuth().getCurrentUser());
                            Functions.setUID(Functions.getUser().getUid());
                            //adatbázisba beírás
                            Firebase.setAndroidContext(getActivity().getApplicationContext());
                            RegLog reg = new RegLog(Functions.getEmail(), Functions.getName(), Functions.getCukorbetegseg(), Functions.getLiszterzekenyseg(), Functions.getLaktozerzekenyseg(), Functions.getWeight(), Functions.getHeight(), Functions.getGender(), Functions.getBmiindex(), Functions.getAcctype());
                            reg.write_database();
                            //------------------
                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setVariables() {
        Functions.setName(edtName.getText().toString());
        Functions.setEmail(edtEmail.getText().toString());
        Functions.setCukorbetegseg(swCukor.isChecked());
        Functions.setLiszterzekenyseg(swLiszt.isChecked());
        Functions.setLaktozerzekenyseg(swLaktoz.isChecked());
        Functions.setWeight(Long.parseLong(edtWeight.getText().toString()));
        Functions.setHeight(Long.parseLong(edtHeight.getText().toString()));
        Functions.setGender(gender);
        Functions.setBmiindex(calcBMI(Functions.getHeight(), Functions.getWeight()));
        Functions.setAcctype(type);
    }

    private double calcBMI(long height, long weight) {
        return  (double)weight / Math.pow((double)height, 2);
    }
}
