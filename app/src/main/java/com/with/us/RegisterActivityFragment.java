package com.with.us;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegisterActivityFragment extends Fragment {

    Spinner region_spinner,interest_spinner;
    ArrayAdapter region_adapter,interest_adapter;

    public RegisterActivityFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View register_view = inflater.inflate(R.layout.activity_register_fragment, container, false);

        //지역스피너 Adapt
        region_spinner = (Spinner) register_view.findViewById(R.id.activity_register_spn_region);
        region_adapter = ArrayAdapter.createFromResource(getContext(), R.array.region, android.R.layout.simple_spinner_dropdown_item);
        region_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region_spinner.setAdapter(region_adapter);

        //관심사스피너 Adapt
        interest_spinner= (Spinner) register_view.findViewById(R.id.activity_register_spn_interest);
        interest_adapter = ArrayAdapter.createFromResource(getContext(), R.array.interest, android.R.layout.simple_spinner_dropdown_item);
        interest_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interest_spinner.setAdapter(interest_adapter);

        return register_view;
    }




}
