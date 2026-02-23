package com.preet.androiddashboard.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.preet.androiddashboard.ui.home.MainActivity;
import com.preet.androiddashboard.MainApplication;
import com.preet.androiddashboard.R;
import com.preet.androiddashboard.base.BaseActivity;
import com.preet.androiddashboard.core.model.LoginData;
import com.preet.androiddashboard.core.storage.PrefManager;
import com.preet.androiddashboard.utils.AppToast;
import com.preet.androiddashboard.utils.ValidationUtils;
import com.preet.androiddashboard.widgets.SwipeButtonView;
import com.preet.androiddashboard.factory.AppViewModelFactory;
import com.preet.androiddashboard.viewmodel.AuthViewModel;

import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;


public class LoginActivity extends BaseActivity {
    private EditText etEmail, etPassword;
    private AuthViewModel viewModel;
    private ProgressBar progressBar;
    private SwipeButtonView swipeLogin;


    @Override
    protected boolean showDrawer() {
        return false;
    }

    @Override
    protected boolean showBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        loadContentLayout(R.layout.activity_login);

        setTitle("Login");

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        etEmail.setText("gurpreet33@gmail.com");
        etPassword.setText("123456");

        findViewById(R.id.tvSignup).setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));

        progressBar = findViewById(R.id.progressBar);

        AppViewModelFactory factory = ((MainApplication) getApplication()).getViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        //findViewById(R.id.btnLogin).setOnClickListener(v -> onClickedLogin());
        swipeLogin = findViewById(R.id.swipeLogin);
        swipeLogin.setSwipeButtonLabel("Swipe to Login");
        swipeLogin.setOnSwipeCompleteListener(this::onClickedLogin);
    }

    private void onClickedLogin() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (!ValidationUtils.isEmailValid(email) ||
                !ValidationUtils.isPasswordValid(password)) {
            AppToast.getInstance().show(this, "Invalid credentials");
//            AppAlertDialog.showMessage(this, "Error", "Invalid credentials");
            resetSwipe();
            return;
        }

        viewModel.login(email, password).observe(this, result -> {
            if (result == null) {
                resetSwipe();
                return;
            }
            switch (result.status) {
                case LOADING:
                    // If using swipe loader animation
                    swipeLogin.showSuccess();
                    break;

                case SUCCESS:
                    List<LoginData> users = result.data.getData();
                        swipeLogin.showSuccess();
                        AppToast.getInstance().show(this, "Login Success");
                        String token = users.get(0).getToken();
                        PrefManager.getInstance(this).saveToken(token);
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    break;
                case ERROR:
                    resetSwipe();
                    AppToast.getInstance().show(this, result.message);
                    break;
            }
        });
    }

    public void resetSwipe() {
        swipeLogin.resetSwipe();
        swipeLogin.setSwipeButtonLabel("Swipe to Login");
    }
}