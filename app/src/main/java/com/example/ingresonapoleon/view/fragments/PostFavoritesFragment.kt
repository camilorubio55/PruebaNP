package com.example.ingresonapoleon.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ingresonapoleon.App
import com.example.ingresonapoleon.R
import com.example.ingresonapoleon.core.Loading
import com.example.ingresonapoleon.model.data.PostBind
import com.example.ingresonapoleon.view.adapters.ListFavoritePostsAdapter
import com.example.ingresonapoleon.view.dialogs.LoadingDialog
import com.example.ingresonapoleon.viewmodel.UIState
import kotlinx.android.synthetic.main.fragment_post_favorites.*

class PostFavoritesFragment : Fragment(), Loading {

    // Adapters
    private lateinit var listFavoritePostAdapter: ListFavoritePostsAdapter

    // Dialogs
    override var loadingDialog: LoadingDialog? = null

    // Inject
    private val favoritePostsViewModel = App.injectFavoritePostsViewModel()

    private fun setupHandlers() {
        favoritePostsViewModel.getFavoritePostsInfoLiveData().observe(this, Observer {status ->
            hideLoading()
            when (status) {
                is UIState.Loading -> {
                    showLoading(getString(R.string.title_loading_dialog), fragmentManager!!)
                }
                is UIState.Success -> {
                    val data = status.data as MutableList<PostBind>
                    listFavoritePostAdapter.clearData()
                    listFavoritePostAdapter.setData(data)
                }
                is UIState.Error -> {
                    listFavoritePostAdapter.clearData()
                    Toast.makeText(context, status.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListPostsAdapter()
        setupHandlers()
    }

    override fun onStart() {
        super.onStart()
        getFavoritePosts()
    }

    private fun setupListPostsAdapter() {
        listFavoritePostAdapter = ListFavoritePostsAdapter()
        recyclerViewPostsFavs.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = listFavoritePostAdapter
        }
    }

    private fun getFavoritePosts() {
        favoritePostsViewModel.getFavoritePosts()
    }

    companion object{
        const val TAG = "PostFavoritesFragment"
    }
}
