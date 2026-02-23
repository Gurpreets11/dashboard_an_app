package com.preet.androiddashboard;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import androidx.lifecycle.ViewModel;

import com.preet.androiddashboard.core.network.ApiClient;
import com.preet.androiddashboard.core.network.ApiService;
import com.preet.androiddashboard.utils.GlobalExceptionHandler;
import com.preet.androiddashboard.factory.AppViewModelFactory;
import com.preet.androiddashboard.repository.AuthRepository;
import com.preet.androiddashboard.viewmodel.AuthViewModel;

public class MainApplication extends Application {

    private AppViewModelFactory factory;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiService apiService = ApiClient.getApiService(this);

        AuthRepository authRepository = new AuthRepository(apiService);
        Map<Class<? extends ViewModel>, Supplier<? extends ViewModel>> creators = new HashMap<>();
        creators.put(AuthViewModel.class, () -> new AuthViewModel(authRepository));

//        creators.put(ProfileViewModel.class, () -> new ProfileViewModel(profileRepository));
//        creators.put(HomeViewModel.class, () -> new HomeViewModel(homeRepository));

        factory = new AppViewModelFactory(creators);

        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler(this));
    }

    public AppViewModelFactory getViewModelFactory() {
        return factory;
    }

}
