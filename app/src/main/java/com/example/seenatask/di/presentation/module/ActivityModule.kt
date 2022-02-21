package com.example.task.di.presentation.module

import android.content.Context
import android.view.LayoutInflater
import com.example.task.databinding.ActivityMainBinding
import com.example.task.di.presentation.scopes.ActivityScope
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule {

  companion object {
    @Provides
    @ActivityScope
    fun provideLayoutInflater(context: Context): LayoutInflater {
      return LayoutInflater.from(context)
    }
    @Provides
    @ActivityScope
    fun provideMainBinding(inflater: LayoutInflater): ActivityMainBinding {
      return ActivityMainBinding.inflate(inflater)
    }
  }
}