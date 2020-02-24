package com.xmrion.tnm.aitao.activity.editname;

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
import com.xmrion.tnm.aitao.activity.storeinfor.StoreInformationFragment_Xmaion;
import com.xmrion.tnm.aitao.utils.ToastUtils_Xmarion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郑艳达 on 2017/7/11.
 * 新建店铺名称
 */

public class EditNameFragment_Xmarion extends Fragment {

    @BindView(R.id.id_tv_edit_title)
    public TextView titleText;

    @BindView(R.id.id_et_edit)
    public EditText et;

    @OnClick(R.id.id_btn_edit_back)
    public void back() {
        getActivity().finish();
    }

    @BindView(R.id.id_btn_edit_confirm)
    public TextView confirmBtn;

    @OnClick(R.id.id_btn_edit_confirm)
    public void confirm() {
        String content = et.getText().toString();
        if (content.equals("")) {
            ToastUtils_Xmarion.showToast("店铺名不能为空！", getActivity());
            return;
        }
        StoreInformationFragment_Xmaion.storeName = content;
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
        titleText.setText("编辑店铺名");
        et.setHint(new SpannableString(getString(R.string.input_store_name)));
        InputFilter[] filters = {new InputFilter.LengthFilter(15)};
        et.setFilters(filters);
        confirmBtn.setText(getString(R.string.confirm));
    }
}
