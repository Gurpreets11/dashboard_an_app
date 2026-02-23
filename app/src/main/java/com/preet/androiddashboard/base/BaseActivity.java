package com.preet.androiddashboard.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.preet.androiddashboard.R;
import com.preet.androiddashboard.core.LoadingDialog;


import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLoading() {
        loadingDialog.show();
    }

    public void hideLoading() {
        loadingDialog.dismiss();
    }
}
