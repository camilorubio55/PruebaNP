package com.example.ingresonapoleon.view.dialogs


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.example.ingresonapoleon.R
import kotlinx.android.synthetic.main.loading_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class LoadingDialog : DialogFragment() {

    // Message to show
    internal var message = String()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.loading_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDialog()
    }

    private fun setupDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        messageLoading.text = message
    }

    companion object {
        const val TAG = "LoadingDialog"
        fun newInstance(message: String): LoadingDialog {
            val dialog = LoadingDialog()
            dialog.message = message
            return dialog
        }
    }
}