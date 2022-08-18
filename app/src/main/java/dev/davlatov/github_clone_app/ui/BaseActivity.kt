package dev.davlatov.github_clone_app.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.databinding.CustomDialogLoadingBinding


// this activity is a base activity for all activities
@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    private lateinit var dialogBinding: CustomDialogLoadingBinding
    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        dialogBinding = CustomDialogLoadingBinding.inflate(layoutInflater)
        progressDialog = AlertDialog.Builder(this).create()
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
