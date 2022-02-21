package com.example.task.di.presentation.module

import android.content.Context
import android.view.LayoutInflater
import com.example.task.data.repository.FilesRepoImpl
import com.example.task.databinding.FragmentFilesListBinding
import com.example.task.di.presentation.scopes.FragmentScope
import com.example.task.domain.repository.FilesRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class FragmentModule {

  @Binds
  @FragmentScope
  abstract fun bindsFilesRepositoryImpl(filesRepoImpl: FilesRepoImpl): FilesRepo

}