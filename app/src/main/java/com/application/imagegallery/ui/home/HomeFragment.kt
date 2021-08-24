package com.application.imagegallery.ui.home

import android.app.ProgressDialog
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
            callGetAllAPI();
        } else {

        }

        recyclerView = root.findViewById<View>(R.id.recycler_view) as RecyclerView

        initRecycler()


        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/



        return root
    }

    fun initRecycler() {
        val layoutManager = GridLayoutManager(
            activity,
            3
        )
        recyclerView!!.setLayoutManager(layoutManager)
    }

    private fun callGetAllAPI() {
        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Fetching...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()


        val callback: Callback<ApiResponse?> = object : Callback<ApiResponse?> {
            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                Log.i("onResponse", response.toString())


                response.body()?.hits?.let { showImages(it) }

                progressDialog.dismiss()

            }

            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                Log.i("onFailure", t.toString())

                progressDialog.dismiss()
            }
        }
        val repoRetriever = WebServiceHelper()
        repoRetriever.getImagesJsonCall("", "")?.enqueue(callback)

    }

    fun showImages(imagesData: ArrayList<HitDetail>) {
        if (imagesData.size != 0) {
            imageAdapter = activity?.let { ImageAdapter(it, imagesData) }
            imageAdapter?.setOnItemClickListener(object : ImageAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {

                }
            })
            recyclerView?.setAdapter(imageAdapter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}