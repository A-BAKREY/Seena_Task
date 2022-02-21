package com.example.seenatask.data

import android.content.Context
import com.example.seenatask.R
import com.example.seenatask.domain.entity.RecyclerEntity
import com.example.seenatask.domain.repository.RepositoryInterface
import com.google.gson.Gson
import io.reactivex.Single
import javax.inject.Inject

class ModelRepository @Inject constructor(
    private val context: Context,
    private val gson: Gson
) : RepositoryInterface {

    override fun getFilesList(): Single<List<RecyclerEntity>> {
        val filesJson = context
            .resources
            .openRawResource(R.raw.get_list_response)
            .streamToString()
        val filesList = gson.fromJson(filesJson, Array<RecyclerModel>::class.java).toList()

        return Single.just(filesList.map {
            it.mapToEntity()
        })
    }

}