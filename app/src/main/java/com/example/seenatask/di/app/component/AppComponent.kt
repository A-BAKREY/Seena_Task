package com.example.task.di.app.component

import android.app.Application
import com.example.task.di.app.module.AppModule
import com.example.task.di.app.module.RxModule
import com.example.task.di.app.scope.AppScope
import com.example.task.di.presentation.subcomponent.ActivitySubComponent
import com.example.task.di.presentation.subcomponent.FragmentSubComponent
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, RxModule::class])
interface AppComponent {

  fun getActivitySubComponent(): ActivitySubComponent.Builder

  fun getFragmentSubComponent(): FragmentSubComponent.Builder

  @Component.Builder
  interface Builder {

    @BindsInstance fun bindApplication(application: Application): Builder

    fun build(): AppComponent
  }
}