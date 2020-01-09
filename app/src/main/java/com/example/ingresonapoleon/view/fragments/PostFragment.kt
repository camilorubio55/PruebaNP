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
import com.example.ingresonapoleon.model.data.PostBind
import com.example.ingresonapoleon.view.adapters.ListPostAdapter
import com.example.ingresonapoleon.viewmodel.UIState
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment : Fragment() {

    // Adapters
    private lateinit var listPostAdapter: ListPostAdapter

    // Inject
    private val postViewModel = App.injectPostViewModel()

    private fun setupHandlers() {
        postViewModel.getPostInfoLiveData().observe(this, Observer { status ->
            //hideLoading()
            when (status) {
                is UIState.Loading -> {
                    //showLoading(getString(R.string.title_loading_dialog), this.supportFragmentManager)
                }
                is UIState.Success -> {
                    val data = status.data as MutableList<PostBind>
                    listPostAdapter.clearData()
                    listPostAdapter.setData(data)
                }
                is UIState.Error -> {
                    listPostAdapter.clearData()
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
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListPostsAdapter()
        setupHandlers()
    }

    override fun onStart() {
        super.onStart()
        getPost()
    }

    private fun setupListPostsAdapter() {
        listPostAdapter = ListPostAdapter()
        recyclerViewPosts.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = listPostAdapter
        }
    }

    private fun getPost() {
        postViewModel.getPosts()
    }

    companion object{
        const val TAG = "PostFragment"
    }
}
