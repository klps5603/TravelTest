package com.bonge.traveltest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bonge.traveltest.databinding.ItemTravelBinding
import com.bonge.traveltest.web.TravelResponse
import com.bumptech.glide.Glide

class TravelAdapter : PagingDataAdapter<TravelResponse, TravelAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object :
            DiffUtil.ItemCallback<TravelResponse>() {
            override fun areItemsTheSame(
                oldItem: TravelResponse,
                newItem: TravelResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TravelResponse,
                newItem: TravelResponse
            ): Boolean {
                return oldItem == newItem
            }

        }

    }

    inner class ViewHolder(val binding: ItemTravelBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(travelResponse: TravelResponse)
    }

    private lateinit var context: Context
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemTravelBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = getItem(position)
            item?.let {
                if (item.images.isNotEmpty()) {
                    val image = item.images.first()
                    Glide.with(context)
                        .load(image.src)
                        .into(travelImageView)
                } else {
                    travelImageView.setImageDrawable(null)
                }
                nameTextView.text = item.name
                introductionTextView.text = item.introduction

                root.setOnClickListener {
                    onItemClickListener?.onItemClick(item)
                }
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}