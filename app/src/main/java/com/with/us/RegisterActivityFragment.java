package com.with.us;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.with.us.models.UserInfo;
import com.with.us.services.auxiliary.RequestHelper;


public class RegisterActivityFragment extends Fragment {

    private static final String TAG = "RegisterActivityFragment";

    RequestHelper helper;
    Spinner region_spinner, interest_spinner;
    String gender;
    EditText name;
    EditText birthDate;
    RadioGroup activity_register_rg;
    Button activity_register_fragment_btn;

    public RegisterActivityFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View register_view = inflater.inflate(R.layout.activity_register_fragment, container, false);

        region_spinner = (Spinner) register_view.findViewById(R.id.activity_register_spn_region);
        interest_spinner= (Spinner) register_view.findViewById(R.id.activity_register_spn_interest);
        name = register_view.findViewById(R.id.activity_register_et_name);
        birthDate = register_view.findViewById(R.id.activity_register_et_birth);
        activity_register_rg = register_view.findViewById(R.id.activity_register_rg);
        activity_register_fragment_btn = register_view.findViewById(R.id.activity_register_fragment_btn);

        makeSpinner(region_spinner, R.array.region);
        makeSpinner(interest_spinner, R.array.interest);

        helper = new RequestHelper(getActivity(), TAG);

        activity_register_fragment_btn.setOnClickListener(new View.OnClickListener() {
            String region = String.valueOf(region_spinner.getSelectedItem());
            String interest = String.valueOf(interest_spinner.getSelectedItem());

            @Override
            public void onClick(View v) {
                RadioButton gender = (RadioButton) register_view.findViewById(activity_register_rg.getCheckedRadioButtonId());
                UserInfo userInfo = new UserInfo(name.getText().toString(), Integer.parseInt(birthDate.getText().toString()), gender.getText().toString(), region, interest);
                createUser(userInfo);
            }
        });

        return register_view;
    }

    private void makeSpinner(Spinner spinner, int array) {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void createUser(UserInfo userInfo) {
        helper.postRequest(userInfo);
        Toast.makeText(getContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setClass(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
