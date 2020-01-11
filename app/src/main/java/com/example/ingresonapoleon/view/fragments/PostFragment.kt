package com.example.ingresonapoleon.view.fragments


import android.content.Intent
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
import com.example.ingresonapoleon.view.activities.UserActivity
import com.example.ingresonapoleon.view.adapters.ListPostAdapter
import com.example.ingresonapoleon.view.dialogs.BottomSheetFragment
import com.example.ingresonapoleon.view.dialogs.LoadingDialog
import com.example.ingresonapoleon.viewmodel.UIState
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment : Fragment(), Loading {

    // Adapters
    private lateinit var listPostAdapter: ListPostAdapter

    // Dialogs
    private val bottomSheetDelete: BottomSheetFragment = BottomSheetFragment()
    override var loadingDialog: LoadingDialog? = null

    // Inject
    private val postViewModel = App.injectPostViewModel()

    private fun setupHandlers() {
        postViewModel.getPostInfoLiveData().observe(this, Observer { status ->
            hideLoading()
            when (status) {
                is UIState.Loading -> {
                    showLoading(getString(R.string.title_loading_dialog), fragmentManager!!)
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
        listenerDeletePost()
    }

    override fun onStart() {
        super.onStart()
        if(listPostAdapter.getData().count() == 0) {
            getPost()
        }
    }

    private fun setupListPostsAdapter() {
        listPostAdapter = ListPostAdapter(clickPost = {idPost,idUser ->
            launchActivityUser(idUser)
            updateItemRead(idPost)
        }, changePostFav = {idPost, isChecked ->
            updateChangeFavorite(idPost, isChecked)
        }, deletePost = {
            launchBottomSheetDelete()
        })
        recyclerViewPosts.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = listPostAdapter
        }
    }

    private fun updateItemRead(idPost : Int) {
        listPostAdapter.updateRead(idPost)
    }

    private fun updateChangeFavorite(idPost : Int, isChecked: Boolean) {
        listPostAdapter.changeFavorite(idPost, isChecked)
    }

    private fun getPost() {
        postViewModel.getPosts()
    }

    private fun launchBottomSheetDelete() {
        bottomSheetDelete.show(activity!!.supportFragmentManager, BottomSheetFragment.TAG)
    }

    private fun listenerDeletePost() {
        bottomSheetDelete.listener = {
            Toast.makeText(context, "Eliminando", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchActivityUser(idUser: Int) {
        Intent(activity, UserActivity::class.java).run {
            putExtra("idUser", idUser)
            startActivity(this)
        }
    }

    companion object{
        const val TAG = "PostFragment"
    }
}
