package com.example.task.di.presentation.module

import com.example.task.di.presentation.viewmodel.ViewModelProviderModule
import dagger.Module

@Module(includes = [ViewModelProviderModule::class])
abstract class ActivityViewModelModule