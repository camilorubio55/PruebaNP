package com.example.ingresonapoleon.view.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
import androidx.appcompat.app.AppCompatActivity

class PostFragment : Fragment(), Loading {

    // Adapters
    private lateinit var listPostAdapter: ListPostAdapter

    // Dialogs
    private val bottomSheetDelete: BottomSheetFragment = BottomSheetFragment()
    override var loadingDialog: LoadingDialog? = null

    // Inject
    private val postViewModel = App.injectPostViewModel()

    @Suppress("UNCHECKED_CAST")
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
        postViewModel.deleteAllPostLiveData().observe(this, Observer {status ->
            hideLoading()
            when (status) {
                is UIState.Loading -> {
                    showLoading(getString(R.string.title_loading_dialog), fragmentManager!!)
                }
                is UIState.Success -> {
                    listPostAdapter.clearData()
                }
                is UIState.Error -> {
                    Toast.makeText(context, status.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        postViewModel.deletePostLiveData().observe(this, Observer {status ->
            hideLoading()
            when (status) {
                is UIState.Loading -> {
                    showLoading(getString(R.string.title_loading_dialog), fragmentManager!!)
                }
                is UIState.Success -> {
                    postViewModel.getPosts()
                }
                is UIState.Error -> {
                    Toast.makeText(context, status.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
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
        setupToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.removeItem -> {
                deleteAllPosts()
                false
            }
            R.id.showFavoritesItem -> {
                showPostsFavorites()
                false
            }
            R.id.refreshItem ->{
                refreshPost()
                false
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getPost()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbarPost)
    }

    private fun setupListPostsAdapter() {
        listPostAdapter = ListPostAdapter(clickPost = {idPost,idUser ->
            launchActivityUser(idUser)
            updateItemRead(idPost)
        }, changePostFav = { _, _ ->
            updateChangeFavorite()
        }, deletePost = {idPost ->
            launchBottomSheetDelete(idPost)
        })
        recyclerViewPosts.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = listPostAdapter
        }
    }

    private fun deleteAllPosts() {
        postViewModel.deleteAllPostDB()
    }

    private fun showPostsFavorites() {
        fragmentManager!!.beginTransaction().run {
            replace(R.id.containerPost, PostFavoritesFragment(), PostFavoritesFragment.TAG)
            addToBackStack(PostFavoritesFragment.TAG)
            commit()
        }
    }

    private fun refreshPost() {
        Toast.makeText(context!!,"Refresh", Toast.LENGTH_SHORT).show()
    }

    private fun updateItemRead(idPost : Int) {
        postViewModel.readPostDB(idPost)
    }

    private fun updateChangeFavorite() {
        Toast.makeText(context!!,"Action change favorite", Toast.LENGTH_SHORT).show()
    }

    private fun getPost() {
        postViewModel.getPosts()
    }

    private fun launchBottomSheetDelete(idPost : Int) {
        val bundle = Bundle()
        bundle.putInt("idPost", idPost)
        bottomSheetDelete.arguments = bundle
        bottomSheetDelete.show(activity!!.supportFragmentManager, BottomSheetFragment.TAG)
    }

    private fun listenerDeletePost() {
        bottomSheetDelete.listener = {idPost ->
            postViewModel.deletePostDB(idPost)
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
