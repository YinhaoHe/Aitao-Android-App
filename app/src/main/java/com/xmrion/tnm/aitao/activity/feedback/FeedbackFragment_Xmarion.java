package com.xmrion.tnm.aitao.activity.feedback;

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
import com.xmrion.tnm.aitao.utils.ToastUtils_Xmarion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郑艳达 on 2017/7/9.
 * 反馈Fragment
 */

public class FeedbackFragment_Xmarion extends Fragment {

    @BindView(R.id.id_tv_edit_title)
    public TextView titleText;

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
        if (!et.getText().toString().equals("")) {
            ToastUtils_Xmarion.showToast("请输入反馈内容！", getActivity());
            return;
        }
        //// TODO: 2017/7/9 添加反馈接口

        ToastUtils_Xmarion.showToast("反馈成功！", getActivity());
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
        et.setHint(new SpannableString(getString(R.string.input_feedback_content)));
        InputFilter[] filters = {new InputFilter.LengthFilter(150)};
        et.setFilters(filters);
        confirmText.setText(getString(R.string.confirm));
        titleText.setText(getString(R.string.feedback));
    }
}
