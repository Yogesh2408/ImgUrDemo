package com.yogesh.imgurdemo.activities

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yogesh.imgurdemo.R
import com.yogesh.imgurdemo.Utils
import com.yogesh.imgurdemo.adapter.ImageRecyclerViewAdapter
import com.yogesh.imgurdemo.adapter.RecyclerItemClickListener
import com.yogesh.imgurdemo.networks.ImgurRestApi
import com.yogesh.imgurdemo.viewmodel.MainActivityViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class GalleryActivity : AppCompatActivity(), KodeinAware,
    RecyclerItemClickListener {

    private val tag = GalleryActivity::class.java.simpleName
    override val kodein by closestKodein()
    private val restApi: ImgurRestApi by instance<ImgurRestApi>()
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mRecyclerViewAdapter: ImageRecyclerViewAdapter
    private lateinit var imageRecyclerView: RecyclerView
    private lateinit var searchImageEditText: AppCompatEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchImageEditText = findViewById(R.id.search_image_editText)
        imageRecyclerView = findViewById(R.id.countryRecyclerView)
        mProgressBar = findViewById(R.id.progressBar)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        setUpRecyclerView()
        getObservableImageList()
    }

    /**
     * Setting up recycler view.
     */
    private fun setUpRecyclerView() {
        mRecyclerViewAdapter =
            ImageRecyclerViewAdapter(this, this)
        imageRecyclerView.adapter = mRecyclerViewAdapter
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageRecyclerView.layoutManager = GridLayoutManager(this, 2)
        } else {
            imageRecyclerView.layoutManager = GridLayoutManager(this, 3)
        }
        imageRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        imageRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )
        imageRecyclerView.setHasFixedSize(true)
    }


    /**
     * Gets List Image.
     */
    private fun getObservableImageList() {
        viewModel.getImages().observe(this, Observer { imagesList ->
            Log.e(tag, imagesList.toString())
            mRecyclerViewAdapter.setUserList(imagesList)
            mProgressBar.visibility = View.GONE

        })
    }

    override fun onItemClickListener(position: Int, imageId: String) {
        startActivity(ImageActivity.getIntentInstance(imageId, this))
    }

    fun getImages(view: View) {
        if (Utils.isNetworkConnected(this)) {
            mProgressBar.visibility = View.VISIBLE
            viewModel.getImagesFromViewModel(restApi, searchImageEditText.text.toString().trim())
        } else {
            Utils.showToast(this, getString(R.string.no_internet))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}