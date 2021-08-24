package com.application.imagegallery.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.application.imagegallery.R
import com.application.imagegallery.adapters.ImageAdapter
import com.application.imagegallery.data.apiClient.WebServiceHelper
import com.application.imagegallery.data.model.HitDetail
import com.application.imagegallery.data.webService.response.ApiResponse
import com.application.imagegallery.databinding.FragmentHomeBinding
import com.application.imagegallery.uitls.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    var recyclerView: RecyclerView? = null
    var imageAdapter: ImageAdapter? = null
    var layoutManager: StaggeredGridLayoutManager? = null

    var imagesDataAll: ArrayList<HitDetail> = ArrayList<HitDetail>()

    private var loaded = true

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Network.isConnected(requireActivity())) {
            callGetAllAPI("1")
        } else {

        }
        recyclerView = root.findViewById<View>(R.id.recycler_view) as RecyclerView
//        recyclerView?.itemAnimator = SlideInUpAnimator()

        initRecycler()
        return root
    }

    fun initRecycler() {

        layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )

        recyclerView?.layoutManager = layoutManager
    }

    private fun callGetAllAPI(page: String) {
        val callback: Callback<ApiResponse?> = object : Callback<ApiResponse?> {
            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                Log.i("onResponse", response.toString())
                loaded = true

                imagesDataAll.addAll(response.body()!!.hits!!)
                showImages()


            }

            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                Log.i("onFailure", t.toString())
            }
        }
        val repoRetriever = WebServiceHelper()
        repoRetriever.getImagesJsonCall("", "", page)?.enqueue(callback)

    }

    fun showImages() {
        if (imagesDataAll.size != 0) {
            imageAdapter = activity?.let { ImageAdapter(it, imagesDataAll) }
            imageAdapter?.setOnItemClickListener(object : ImageAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(imagesDataAll.get(position).largeImageURL)
                    )
                    context!!.startActivity(browserIntent)
                }
            })
            recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    /* if (dy > 0) {
                         var pastVisibleItems = 0
                         val visibleItemCount: Int
                         val totalItemCount: Int
                         val layoutManager = recyclerView.layoutManager
                         visibleItemCount = layoutManager!!.childCount
                         totalItemCount = layoutManager.itemCount
                         // Already covers the GridLayoutManager case
                         if (layoutManager is LinearLayoutManager) {
                             pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                         } else if (layoutManager is StaggeredGridLayoutManager) {
                             val positions = layoutManager.findFirstVisibleItemPositions(null)
                             if (positions != null && positions.size > 0) pastVisibleItems =
                                 positions[0]
                         }
                         if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                             loaded = false
                             var page = totalItemCount / 20
                             page++
                             if (Network.isConnected(requireActivity())) {
                                 callGetAllAPI(page.toString())
                             } else {

                             }
                         }
                     }*/

                    if (dy > 0) { //check for scroll down
                        var visibleItemCount = layoutManager!!.childCount
                        var totalItemCount = layoutManager!!.itemCount
                        var pastVisiblesItems =
                            findFirstVisiblePosition(recyclerView.layoutManager!!)
                        if (loaded) {
                            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                                loaded = false
                                //Log.v("...", "Last Item Wow !");
                                // Do pagination.. i.e. fetch new data
                                var page = totalItemCount / 20
                                page++
                                if (Network.isConnected(requireActivity())) {
                                    callGetAllAPI(page.toString())
                                } else {

                                }
                            }
                        }
                    }
                }
            })
            recyclerView?.adapter = imageAdapter
        }
    }

    private fun findFirstVisiblePosition(layoutManager: RecyclerView.LayoutManager): Int {
        var firstVisiblePosition = 0
        if (layoutManager is GridLayoutManager) {
            firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        } else if (layoutManager is LinearLayoutManager) {
            firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val into = IntArray(layoutManager.spanCount)
            layoutManager.findFirstVisibleItemPositions(into)
            firstVisiblePosition = Int.MAX_VALUE
            for (pos in into) {
                firstVisiblePosition = Math.min(pos, firstVisiblePosition)
            }
        }
        return firstVisiblePosition
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
