package com.example.ingresonapoleon.core

import androidx.fragment.app.FragmentManager
import com.example.ingresonapoleon.view.dialogs.LoadingDialog

interface Loading {

    // Variable
    var loadingDialog: LoadingDialog?

    fun showLoading (message: String, fragment: FragmentManager) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.newInstance(message)
            loadingDialog!!.show(fragment, LoadingDialog.TAG)
        }
    }

    fun hideLoading() {
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
            loadingDialog = null
        }
    }
}