package com.example.task.presentation.file

import com.example.task.utility.extension.DownloadStatus

data class FileViewState(
    val tile: String? = null,
    val published_by: String? = null,
    val url: String? = null,
    var rate: String? = null,
    var published_date: String? = null
    )