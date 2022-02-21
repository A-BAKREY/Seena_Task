package com.example.task.di.presentation.subcomponent

import android.content.Context
import com.example.task.di.presentation.module.FragmentModule
import com.example.task.di.presentation.module.FragmentViewModelModule
import com.example.task.di.presentation.scopes.FragmentScope
import com.example.task.presentation.file.FilesListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentViewModelModule::class, FragmentModule::class])
interface FragmentSubComponent {

  fun inject(view: FilesListFragment)

  @Subcomponent.Builder
  interface Builder {

    @BindsInstance fun bindContext(application: Context): Builder

    fun build(): FragmentSubComponent
  }
}