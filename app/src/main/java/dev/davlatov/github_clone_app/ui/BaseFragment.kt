package dev.davlatov.github_clone_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.databinding.CustomDialogLoadingBinding
import dev.davlatov.github_clone_app.models.ForYouAndTrendingModel

// this fragment is a base fragment for all fragments
open class BaseFragment : Fragment() {
    private lateinit var dialogBinding: CustomDialogLoadingBinding
    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }
    protected open fun prepareModelsList(): ArrayList<ForYouAndTrendingModel> {
        val list = ArrayList<ForYouAndTrendingModel>()
        for (i in 1..30) {
            list.add(ForYouAndTrendingModel(null, null))
        }
        return list
    }

    private fun initViews() {
        dialogBinding = CustomDialogLoadingBinding.inflate(layoutInflater)
        progressDialog = AlertDialog.Builder(requireContext()).create()
        progressDialog.setCancelable(false)
        progressDialog.setView(dialogBinding.root)
    }

    fun showDialog() {
        if (!progressDialog.isShowing) progressDialog.show()
    }

    fun dismissDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}