package com.example.task.di.presentation.module

import androidx.lifecycle.ViewModel
import com.example.task.di.presentation.scopes.ViewModelKey
import com.example.task.di.presentation.viewmodel.ViewModelProviderModule
import com.example.task.presentation.file.FilesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelProviderModule::class])
abstract class FragmentViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(FilesListViewModel::class)
  abstract fun bindsMainActivityViewModel(filesViewModel: FilesListViewModel): ViewModel

}