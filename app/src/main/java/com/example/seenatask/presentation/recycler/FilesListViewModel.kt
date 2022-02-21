package com.example.task.presentation.file

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.example.task.domain.usecase.FilesUseCase
import com.example.task.presentation.base.BaseViewModel
import com.example.task.service.DownloadFileWorkManager
import com.example.task.utility.extension.DownloadStatus
import com.example.task.utility.extension.deserializeFromGson
import com.example.task.utility.extension.serializeToGson
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FilesListViewModel @Inject constructor(
    private val filesUseCase: FilesUseCase,
    private val workManager: WorkManager,
    private val gson: Gson,
    private val constraints: Constraints,
    private val dataBuilder: Data.Builder
) : BaseViewModel() {

    private val _filesList by lazy { MutableLiveData<List<FileViewState>>() }
    val filesList: LiveData<List<FileViewState>> get() = _filesList

    private val _loadingState by lazy { MutableLiveData<Boolean>() }
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState by lazy { MutableLiveData<String>() }
    val errorState: LiveData<String> get() = _errorState

    var downloadFileState: LiveData<WorkInfo>? = null


    fun loadFilesList() {
        _loadingState.value = true
        compositeDisposable.add(
            filesUseCase.getFiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _filesList.value = it.map { fileEntity ->
                        fileEntity.toFileViewState()
                    }
                }, {
                    _errorState.value = it.message
                    it.printStackTrace()
                })

        )
    }

    fun downloadFile(file: FileViewState) {
        if (file.localFileUri.isBlank()) {
            val oneTimeWorkerBuilder =
                OneTimeWorkRequest.Builder(DownloadFileWorkManager::class.java)
                    .setConstraints(constraints)
            dataBuilder.putString(
                Constants.Data.SEND_DOWNLOAD_ITEM_REQUEST,
                file.serializeToGson(Gson())
            )

            oneTimeWorkerBuilder.setInputData(dataBuilder.build())
            oneTimeWorkerBuilder.build()
            val workRequest = oneTimeWorkerBuilder.build()
            val id = workRequest.id
            workManager.enqueue(workRequest)
            downloadFileState = workManager.getWorkInfoByIdLiveData(id)

        }

    }

    fun updateFilesList(
        fileViewStateStr: String, downloadStatus: DownloadStatus
    ) {
        val file: FileViewState = gson.deserializeFromGson(fileViewStateStr)
        val items = filesList.value
        items?.let { uiModelList ->
            uiModelList.find { it.id == file.id }?.let {
                val newItem = it.copy(
                    id = file.id,
                    type = file.type,
                    url = file.url,
                    name = file.name,
                    downloadedStatus = downloadStatus,
                    localFileUri = file.localFileUri,
                )
                _filesList.value = uiModelList.map { uiItem ->
                    if (uiItem.id == file.id) {
                        newItem
                    } else
                        uiItem
                }
            }
        }
    }


}