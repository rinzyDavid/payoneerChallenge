package com.payoneerchallange.di;

import com.payoneerchallange.repository.GatewayService;
import com.payoneerchallange.repository.GatewayServiceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

/**
 * Hilt Module for injecting the GatewayService into ViewModel.
 * This is scoped to viewModel object which allows for resource
 * clean up when the viewModel is destroyed
 */

@Module
@InstallIn(ViewModelComponent.class)
public abstract class AppModule {

    @Binds
    public abstract GatewayService bindGatewayService(GatewayServiceImpl gatewayService);

}
