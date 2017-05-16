package com.socketclient.activities.first;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.socketclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity implements FirstContract.View, View.OnClickListener{
    @BindView(R.id.nickname_edittext)
    EditText nicknameEdittext;

    FirstContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(FirstActivity.this);
        presenter = new FirstPresenter();
        presenter.setView(this);
    }

    @Override
    public Context getContext() {
        return FirstActivity.this;
    }


    @OnClick({R.id.submit_button})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.submit_button:
                presenter.onSubmitNickname(nicknameEdittext.getText().toString());
                break;
        }
    }
}
