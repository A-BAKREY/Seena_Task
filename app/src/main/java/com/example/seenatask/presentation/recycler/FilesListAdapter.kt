package com.example.task.presentation.file

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.seenatask.data.model.RecyclerModel
import com.example.seenatask.databinding.ItemMainBinding
import com.example.task.utility.extension.*
import javax.inject.Inject

class FilesListAdapter @Inject constructor(

) : ListAdapter<FileViewState, FilesListAdapter.ViewHolder>(CountryListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            binding.title.text =
        }
    }


    class CountryListDiffCallback : DiffUtil.ItemCallback<FileViewState>() {

        override fun areItemsTheSame(
            oldProduct: FileViewState,
            newProduct: FileViewState
        ): Boolean {
            return oldProduct.tile == newProduct.tile
        }

        override fun areContentsTheSame(
            oldProduct: FileViewState,
            newProduct: FileViewState
        ): Boolean {
            return oldProduct == newProduct
        }

    }

}