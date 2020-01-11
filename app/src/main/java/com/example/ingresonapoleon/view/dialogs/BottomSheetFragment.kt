package com.example.ingresonapoleon.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ingresonapoleon.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_delete_post.*

class BottomSheetFragment: BottomSheetDialogFragment() {

    internal var listener: (Int) -> Unit = {}
    private val idPost = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_delete_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        buttonCancel.setOnClickListener {
            dismiss()
        }
        buttonYes.setOnClickListener {
            listener(arguments!!.getInt("idPost"))
            dismiss()
        }
    }

    companion object {
        const val TAG = "BottomSheetFragment"
    }
}