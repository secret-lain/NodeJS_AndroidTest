package com.socketclient.activities.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socketclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.View{
    @BindView(R.id.title_edittext)
    TextView titleEditText;
    @BindView(R.id.result_text_view)
    TextView mResultTextView;
    @BindView(R.id.id_edittext)
    EditText mIdEditText;
    @BindView(R.id.pw_edittext)
    EditText mPwEditText;
    @BindView(R.id.confirm_pw_edittext)
    EditText mConfirmPwEditText;
    @BindView(R.id.email_edittext)
    EditText mEmailEditText;
    @BindView(R.id.login_button_container)
    LinearLayout mLoginButtonContainer;
    @BindView(R.id.regist_button_container)
    LinearLayout mRegistButtonContainer;

    boolean isLoginMode;
    LoginContract.Presenter mPresenter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
        mPresenter = new LoginPresenter();
        mPresenter.setView(this);
        isLoginMode = true;
        titleEditText.setText(getString(R.string.app_name) + "\n로그인");
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }

    @OnClick({R.id.submit_button, R.id.regist_toggle_button, R.id.cancel_button, R.id.regist_submit_button})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.submit_button:
                mPresenter.login(mIdEditText.getText().toString(), mPwEditText.getText().toString());
                break;
            case R.id.regist_toggle_button:
            case R.id.cancel_button:
                toggleMode();
                break;
            case R.id.regist_submit_button:
                mPresenter.registration(mIdEditText.getText().toString(), mPwEditText.getText().toString(),
                        mConfirmPwEditText.getText().toString(), mEmailEditText.getText().toString());
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void toggleMode() {
        clearText();
        // 로그인일때 : 회원가입일때
        if(isLoginMode){
            //회원가입모드로 변경
            mConfirmPwEditText.setVisibility((View.VISIBLE));
            mEmailEditText.setVisibility(View.VISIBLE);

            mLoginButtonContainer.setVisibility(View.GONE);
            mRegistButtonContainer.setVisibility(View.VISIBLE);
            titleEditText.setText(getString(R.string.app_name) + "\n회원가입");
        } else{
            //로그인모드로 변경
            mConfirmPwEditText.setVisibility((View.GONE));
            mEmailEditText.setVisibility(View.GONE);

            mLoginButtonContainer.setVisibility(View.VISIBLE);
            mRegistButtonContainer.setVisibility(View.GONE);
            titleEditText.setText(getString(R.string.app_name) + "\n로그인");
        }
        isLoginMode = !isLoginMode;
    }

    @Override
    public void showLoading() {
        setResult("처리중...");
    }

    @Override
    public void hideLoading() {
        //setResult("");
    }

    @Override
    public void setResult(String message) {
        if(message.equals("")){
            mResultTextView.setVisibility(View.GONE);
        } else{
            mResultTextView.setVisibility(View.VISIBLE);
            mResultTextView.setText(message);
        }
    }

    /**
     * 모든 텍스트를 초기화한다.
     */
    private void clearText(){
        mIdEditText.setText("");
        mPwEditText.setText("");
        mConfirmPwEditText.setText("");
        mEmailEditText.setText("");
        setResult("");
    }
}
