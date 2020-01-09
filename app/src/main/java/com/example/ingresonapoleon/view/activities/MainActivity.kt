package com.example.ingresonapoleon.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ingresonapoleon.R
import com.example.ingresonapoleon.view.fragments.PostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchFragment()
    }

    private fun launchFragment() {
        supportFragmentManager.beginTransaction().run {
            add(R.id.containerPost, PostFragment(), PostFragment.TAG)
            addToBackStack(PostFragment.TAG)
            commit()
        }
    }
}

