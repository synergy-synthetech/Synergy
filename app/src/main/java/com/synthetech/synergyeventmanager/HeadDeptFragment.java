package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeadDeptFragment extends Fragment {


    public HeadDeptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View HeadDepartmentView = inflater.inflate(R.layout.fragment_head_dept, container, false);


        return HeadDepartmentView;
    }

}