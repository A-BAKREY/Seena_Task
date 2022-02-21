package com.example.task.di.app.module

import android.app.Application
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.WorkManager
import com.example.task.di.app.scope.AppScope
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @Provides
  @AppScope
  fun provideGson() = GsonBuilder().setLenient().create()

  @Provides
  @AppScope fun providesDataBuilder() = Data.Builder()

  @Provides
  @AppScope fun provideWorkManagerInstance(context: Application) =
    WorkManager.getInstance(context)

  @Provides
  @AppScope fun providesConstraintToWorkManager(): Constraints {
    return Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
  }
}