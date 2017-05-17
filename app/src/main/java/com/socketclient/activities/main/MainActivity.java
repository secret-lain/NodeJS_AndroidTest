package com.socketclient.activities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.socketclient.R;
import com.socketclient.util.UserStatusContainer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_token_textview)
    TextView userTokenTextview;
    @BindView(R.id.user_id_textview)
    TextView userIdTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MainActivity");

        UserStatusContainer user = UserStatusContainer.getInstance();

        userIdTextView.setText("Hello " + user.getUserId());
        userTokenTextview.setText("Your Token is " + user.getToken());
    }
}
