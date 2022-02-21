package com.example.task.di.presentation.subcomponent

import android.content.Context
import com.example.task.presentation.MainActivity
import com.example.task.di.presentation.module.ActivityModule
import com.example.task.di.presentation.module.ActivityViewModelModule
import com.example.task.di.presentation.scopes.ActivityScope
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityViewModelModule::class, ActivityModule::class])
interface ActivitySubComponent {

  fun inject(fileActivity: MainActivity)

  @Subcomponent.Builder
  interface Builder {

    @BindsInstance fun bindContext(application: Context): Builder

    fun build(): ActivitySubComponent
  }
}