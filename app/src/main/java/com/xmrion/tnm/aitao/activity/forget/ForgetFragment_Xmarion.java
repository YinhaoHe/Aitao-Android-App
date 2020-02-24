package com.xmrion.tnm.aitao.activity.forget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xmrion.tnm.aitao.model.LoginController_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.utils.EditTextUtil_Xmarion;
import com.xmrion.tnm.aitao.utils.MyCountDownTimer;
import com.xmrion.tnm.aitao.utils.ToastUtils_Xmarion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郑艳达 on 2017/7/7.
 * ForgetFragment
 */

public class ForgetFragment_Xmarion extends Fragment {

    private LoginController_Xmarion loginController;

    private List<EditText> editTexts = new ArrayList<>();

    @BindView(R.id.id_et_forget_cp)
    public EditText confirmPasswordEt;

    @BindView(R.id.id_et_forget_np)
    public EditText newPasswordEt;

    @BindView(R.id.id_et_forget_pn)
    public EditText phoneNumEt;

    @BindView(R.id.id_et_forget_vc)
    public EditText verCodeEt;

    @OnClick(R.id.id_btn_forget_confirm)
    public void onClickConfirmBtn() {
        //// TODO: 2017/7/7 添加重置密码接口
        if (!EditTextUtil_Xmarion.checkInput(editTexts)) {
            ToastUtils_Xmarion.showToast("请确认所有信息都已经填好！", getActivity());
            return;
        }
        String pw = newPasswordEt.getText().toString();
        String pw2 = confirmPasswordEt.getText().toString();
        if (!pw.equals(pw2)) {
            ToastUtils_Xmarion.showToast("两次输入的密码不一致!", getActivity());
            return;
        }

        loginController.setPassword(pw);
        loginController.setVerCode(verCodeEt.getText().toString());
        loginController.resetPassword();
        getActivity().finish();
    }

    @OnClick(R.id.id_btn_forget_back)
    public void backBtn() {
        this.getActivity().finish();
    }

    @BindView(R.id.id_btn_forget_send)
    public TextView sendText;

    @OnClick(R.id.id_btn_forget_send)
    public void onClickSendBtn() {
        // TODO: 2017/7/7 添加发送验证码接口
        String pn = phoneNumEt.getText().toString();
        if (pn.equals("")) {
            ToastUtils_Xmarion.showToast("请输入电话号!", getActivity());
            return;
        }
        sendText.setClickable(false);
        new MyCountDownTimer(60000, 1000, sendText).start();
        loginController.setPhoneNum(phoneNumEt.getText().toString());
        loginController.sendVerCode();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forget, container, false);


        ButterKnife.bind(this, v);
        initialData();
        return v;
    }

    private void initialData() {
        loginController = new LoginController_Xmarion(getActivity());
        editTexts.add(confirmPasswordEt);
        editTexts.add(newPasswordEt);
        editTexts.add(phoneNumEt);
        editTexts.add(verCodeEt);
    }
}
