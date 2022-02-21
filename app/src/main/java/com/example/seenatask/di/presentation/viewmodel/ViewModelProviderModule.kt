package com.example.task.di.presentation.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelProviderModule {
  @Binds
  abstract fun bindsViewModelFactory(viewModelFactoryProvider: ViewModelFactoryProvider): ViewModelProvider.Factory
}