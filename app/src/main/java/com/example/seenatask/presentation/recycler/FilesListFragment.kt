package com.example.task.presentation.file

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import com.example.task.app.App
import com.example.task.di.presentation.subcomponent.FragmentSubComponent
import com.example.task.di.presentation.viewmodel.ViewModelFactoryProvider
import com.example.task.utility.Constants.Data.UPDATE_RESULT_ITEM
import com.example.task.utility.Constants.FileType.PDF
import com.example.task.utility.Constants.FileType.VIDEO
import com.example.task.utility.extension.*
import com.google.flatbuffers.Constants
import javax.inject.Inject


class FilesListFragment : Fragment(R.layout.fragment_files_list) {

    @Inject
    lateinit var factoryProvider: ViewModelFactoryProvider

    private val filesListAdapter by lazy {
        FilesListAdapter { fileViewState ->
            onFileDownloadClickAction(fileViewState)
        }
    }

    private fun onFileDownloadClickAction(fileViewState: FileViewState) {

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            if (fileViewState.downloadedStatus == DownloadStatus.DOWNLOADED) {
                when (fileViewState.type) {
                    PDF -> requireContext().openPDFIntent(getLocalFileUri(fileViewState.localFileUri))
                    VIDEO -> requireContext().openVideoIntent(getLocalFileUri(fileViewState.localFileUri))
                }
            } else {
                fileViewState.let {
                    viewModel.downloadFile(file = it)
                    observeWorkManager()
                }
            }

        } else {
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(this, factoryProvider).get(FilesListViewModel::class.java)
    }

    private val fragmentSubComponent: FragmentSubComponent by lazy {
        ((requireActivity().applicationContext) as App).appComponent.getFragmentSubComponent()
            .bindContext(requireContext())
            .build()
    }


    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.containsValue(false))
                showSnackBar(getString(R.string.read_write_permissions))
        }

    private var _binding: FragmentFilesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentSubComponent.inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFilesListBinding.bind(view)

        setUpViews()
        viewModel.loadFilesList()
        setUpObservers()
    }

    private fun setUpViews() {
        binding.apply {
            filesRv.apply {
                adapter = filesListAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                itemAnimator = null
            }
        }
    }

    private fun setUpObservers() {
        observeLoadingFiles()

    }

    private fun observeLoadingFiles() {
        viewModel.loadingState.observe(viewLifecycleOwner, { status ->
            binding.progressCircular.visible()
        })

        viewModel.filesList.observe(viewLifecycleOwner, { filesList ->
            binding.progressCircular.gone()
            filesListAdapter.submitList(filesList)
        })

        viewModel.errorState.observe(viewLifecycleOwner, { message ->
            binding.progressCircular.gone()
            showSnackBar(message)
        })
    }

    private fun observeWorkManager() {
        viewModel.downloadFileState.let { workInfo ->
            workInfo?.observe(viewLifecycleOwner, { info ->
                val outputData =
                    info.outputData.getString(UPDATE_RESULT_ITEM)
                        ?: ""
                when {
                    info.state == WorkInfo.State.RUNNING -> {
                    }
                    info.state == WorkInfo.State.FAILED -> {
                        showSnackBar(getString(R.string.file_corrupted))
                        viewModel.updateFilesList(outputData, DownloadStatus.FAILED)
                    }
                    info.state.isFinished -> {
                        showSnackBar(getString(R.string.file_download_successful))
                        viewModel.updateFilesList(outputData, DownloadStatus.DOWNLOADED)
                    }
                }
            })
        }
    }

    private fun showSnackBar(message: String) {
        binding.root.snack(message) {}
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}