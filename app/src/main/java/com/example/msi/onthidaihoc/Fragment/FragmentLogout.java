package com.example.msi.onthidaihoc.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msi.onthidaihoc.CustomDialog.LogoutDialog;
import com.example.msi.onthidaihoc.R;


public class FragmentLogout extends Fragment {
//    private FirebaseAuth mAuth;
    private LogoutDialog logoutDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentlogout,container,false);
        openLogoutDialog();
        return view;
    }
    private void openLogoutDialog() {
        logoutDialog = new LogoutDialog(getActivity());
        logoutDialog.show();
    }
}

