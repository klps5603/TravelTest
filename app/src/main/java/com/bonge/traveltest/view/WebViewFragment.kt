package com.bonge.traveltest.view

import android.os.Bundle
import android.view.View
import com.bonge.traveltest.R
import com.bonge.traveltest.databinding.FragmentWebViewBinding

class WebViewFragment(val url: String) : BaseFragment<FragmentWebViewBinding>(
    FragmentWebViewBinding::class.java,
    R.layout.fragment_web_view
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.webView?.apply {
            loadUrl(this@WebViewFragment.url)
            settings.javaScriptEnabled = true
        }
    }

    override fun invalidate() {

    }
}