package com.example.msi.onthidaihoc.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msi.onthidaihoc.R;

/**
 * Created by DK on 11/1/2017.
 */

public class FragmentSupport extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentsupport,container,false);
        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }
}

