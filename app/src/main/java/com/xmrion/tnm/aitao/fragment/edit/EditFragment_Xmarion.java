package com.xmrion.tnm.aitao.fragment.edit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xmrion.tnm.aitao.R;

/**
 * Created by 郑艳达 on 2017/7/9.
 * EditFragment
 */

public class EditFragment_Xmarion extends Fragment{

    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_edit, container, false);
        return v;
    }
    public View getV() {
        return v;
    }
}
