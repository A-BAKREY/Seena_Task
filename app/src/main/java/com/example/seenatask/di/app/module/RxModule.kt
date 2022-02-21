package com.example.task.di.app.module

import com.example.presentation.base.AppSchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class RxModule {

    @Provides
    fun provideSchedulerProvider() = AppSchedulerProvider()

}