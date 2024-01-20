package com.bonge.traveltest.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.bonge.traveltest.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) {
    private val loadingDialog =
        Dialog(context)
    private val binding: DialogLoadingBinding by lazy {
        val layoutInflater = LayoutInflater.from(context)
        DialogLoadingBinding.inflate(layoutInflater)
    }

    init {
        loadingDialog.setContentView(binding.root)
        loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadingDialog.setCancelable(false)
    }

    fun show() {
        loadingDialog.show()
    }

    fun dismiss() {
        loadingDialog.dismiss()
    }
}
