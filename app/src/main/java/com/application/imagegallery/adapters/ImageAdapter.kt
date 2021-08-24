package com.application.imagegallery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.application.imagegallery.R
import com.application.imagegallery.data.model.HitDetail
import com.bumptech.glide.Glide
import java.util.*


class ImageAdapter(context: Context, data: ArrayList<HitDetail>) :
    RecyclerView.Adapter<ImageAdapter.HomeViewHolder>() {
    private val data: ArrayList<HitDetail>
    private val rowLayout: Int
    private val context: Context

    var onItemClickListener: OnItemClickListener? = null

    inner class HomeViewHolder(var view: View) : RecyclerView.ViewHolder(
        view
    ), View.OnClickListener {

        var imageData: ImageView

        override fun onClick(v: View) {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(view, position)
            }
        }


        init {
            imageData = view.findViewById<View>(R.id.image_view) as ImageView
            imageData.setOnClickListener(this)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    @JvmName("setOnItemClickListener1")
    fun setOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        onItemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        if (!data.get(position).previewURL.equals("")) {
            Glide.with(context)
                .load(data[position].previewURL)
                .into(holder.imageData)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    init {
        this.data = data
        rowLayout = R.layout.image_recycler_items
        this.context = context
    }
}