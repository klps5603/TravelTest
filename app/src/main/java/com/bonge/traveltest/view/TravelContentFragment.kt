package com.bonge.traveltest.view

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.bonge.traveltest.R
import com.bonge.traveltest.databinding.FragmentTravelContentBinding
import com.bonge.traveltest.viewModel.TravelViewModel
import com.bumptech.glide.Glide

class TravelContentFragment : BaseFragment<FragmentTravelContentBinding>(
    FragmentTravelContentBinding::class.java,
    R.layout.fragment_travel_content
) {

    private val viewModel: TravelViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.officialSiteTextView?.setOnClickListener {
            val officialSite = withState(viewModel) { it }.travel?.officialSite
            officialSite?.let {
                push(WebViewFragment(it))
            }
        }
    }

    override fun invalidate() {
        withState(viewModel) {
            binding?.apply {
                it.travel?.let {
                    setTitle(it.name)
                    if (it.images.isNotEmpty()) {
                        Glide.with(context)
                            .load(it.images[0].src)
                            .into(travelImageView)
                    } else {
                        travelImageView.setImageDrawable(null)
                    }
                    nameTextView.text = it.name
                    introductionTextView.text = it.introduction
                    officialSiteTextView.text = it.officialSite
                }

            }
        }
    }
}