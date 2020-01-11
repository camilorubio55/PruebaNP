package com.example.ingresonapoleon.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.ingresonapoleon.App
import com.example.ingresonapoleon.R
import com.example.ingresonapoleon.core.Loading
import com.example.ingresonapoleon.model.data.UserBind
import com.example.ingresonapoleon.view.dialogs.LoadingDialog
import com.example.ingresonapoleon.viewmodel.UIState
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity(), Loading {

    // Inject
    private val userViewModel = App.injectUserViewModel()

    // Adapters
    override var loadingDialog: LoadingDialog? = null

    @Suppress("UNCHECKED_CAST")
    private fun setupHandlers() {
        userViewModel.getUserInfoLiveData().observe(this, Observer { status ->
            hideLoading()
            when (status) {
                is UIState.Loading -> {
                    showLoading(getString(R.string.title_loading_dialog), this.supportFragmentManager)
                }
                is UIState.Success -> {
                    val data: UserBind = status.data as UserBind
                    bindTextViews(data)
                }
                is UIState.Error -> {
                    Toast.makeText(this, status.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setupHandlers()
    }

    override fun onStart() {
        super.onStart()
        getUser()
    }

    private fun bindTextViews(user: UserBind) {
        userName.text = user.name
        userPhone.text = user.phone
        userEmail.text = user.email
    }

    private fun getUser() {
        userViewModel.getUsers(intent.extras.let{
            it!!.getInt("idUser")
        })
    }
}
