package com.xmrion.tnm.aitao.activity.login;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xmrion.tnm.aitao.activity.forget.ForgetActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.register.RegisterActivity_Xmarion;
import com.xmrion.tnm.aitao.model.LoginController_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.utils.EditTextUtil_Xmarion;
import com.xmrion.tnm.aitao.utils.ToastUtils_Xmarion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郑艳达 on 2017/7/7.
 * LoginFragment
 */

public class LoginFragment_Xmarion extends Fragment {

    private LoginController_Xmarion loginController;

    private List<EditText> editTexts = new ArrayList<>();

    @BindView(R.id.id_et_login_pn)
    public EditText phoneNumEt;
    @BindView(R.id.id_et_login_pw)
    public EditText passwordEt;

    @BindView(R.id.id_btn_login_login)
    public TextView logintBtn;

    @OnClick(R.id.id_btn_login_login)
    public void onClickLoginBtn() {
        //// TODO: 2017/7/7 添加登陆接口
        if (!EditTextUtil_Xmarion.checkInput(editTexts)) {
            ToastUtils_Xmarion.showToast("请确认所有信息都已经填好！", getActivity());
            return;
        }
        loginController.setPassword(passwordEt.getText().toString());
        loginController.setPhoneNum(phoneNumEt.getText().toString());
        loginController.login(logintBtn);
    }


    @OnClick(R.id.id_btn_login_forget)
    public void onClickForgetBtn() {
        startActivity(new Intent(getActivity(), ForgetActivity_Xmarion.class));
    }


    @OnClick(R.id.id_btn_login_register)
    public void onClickRegisterBtn() {
        startActivity(new Intent(getActivity(), RegisterActivity_Xmarion.class));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, v);
        initialData();
        return v;
    }

    private void initialData() {
        loginController = new LoginController_Xmarion(getActivity());

        editTexts.add(phoneNumEt);
        editTexts.add(passwordEt);
    }
}
