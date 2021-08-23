package com.application.imagegallery.ui.home

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.imagegallery.data.apiClient.WebServiceHelper
import com.application.imagegallery.data.model.WebServiceRequest.HitsResponse
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

        if(Network.isConnected(requireActivity())){
            callPixabayAPI();
        }
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })



        return root
    }

    private fun callPixabayAPI() {
        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()


        val callback: Callback<HitsResponse> = object : Callback<HitsResponse> {
            override fun onResponse(call: Call<HitsResponse>, response: Response<HitsResponse>) {
                Log.i("onResponse", response.toString())

                progressDialog.dismiss()

            }

            override fun onFailure(call: Call<HitsResponse>, t: Throwable) {
                Log.i("onFailure", t.toString())

                progressDialog.dismiss()
            }
        }

        WebServiceHelper.getImagesJsonCall(username, password)?.enqueue(callback)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}