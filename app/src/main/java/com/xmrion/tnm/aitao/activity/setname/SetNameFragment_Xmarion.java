package com.xmrion.tnm.aitao.activity.setname;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.bmob.User_Xmarion;
import com.xmrion.tnm.aitao.utils.ToastUtils_Xmarion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 郑艳达 on 2017/7/8.
 * SetNameFragment_Xmarion
 */

public class SetNameFragment_Xmarion extends Fragment {

    @BindView(R.id.id_btn_edit_confirm)
    public TextView confirmText;

    @OnClick(R.id.id_btn_edit_back)
    public void back() {
        getActivity().finish();
    }

    @BindView(R.id.id_et_edit)
    public EditText et;


    @OnClick(R.id.id_btn_edit_confirm)
    public void confirm() {
        String name = et.getText().toString();
        if (name.equals("")) {
            ToastUtils_Xmarion.showToast("用户名不能为空!", getActivity());
            return;
        }
        User_Xmarion user = new User_Xmarion();
        user.setUsername(name);
        MyApplication_Xmarion app = (MyApplication_Xmarion) getActivity().getApplication();
        app.getUser().setUsername(name);
        user.update(app.getUser().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils_Xmarion.showToast("用户名更新成功", getActivity());
                        }
                    });
                }
            }
        });
        back();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        ButterKnife.bind(this, v);
        initialView();
        return v;
    }

    private void initialView() {
        et.setHint(new SpannableString(getString(R.string.input_new_name)));
        InputFilter[] filters = {new InputFilter.LengthFilter(15)};
        et.setFilters(filters);
        confirmText.setText(getString(R.string.confirm));
    }
}
