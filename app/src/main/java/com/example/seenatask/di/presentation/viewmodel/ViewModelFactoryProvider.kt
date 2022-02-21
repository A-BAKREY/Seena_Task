package com.example.task.di.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactoryProvider @Inject constructor(
  private val viewModelMap: Map<Class<out ViewModel>,
      @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    val creator = viewModelMap[modelClass] ?: viewModelMap.asIterable().firstOrNull { mapEntry ->
      modelClass.isAssignableFrom(mapEntry.key)
    }?.value ?: throw IllegalArgumentException()
    return try {
      @Suppress("UNCHECKED_CAST")
      creator.get() as T
    } catch (e: Exception) {
      Log.d("bindException", e.toString())
      throw IllegalArgumentException()
    }
  }
}