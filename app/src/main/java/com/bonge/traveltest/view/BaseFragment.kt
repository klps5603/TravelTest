package com.bonge.traveltest.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.airbnb.mvrx.BaseMvRxFragment
import com.bonge.traveltest.dialog.LoadingDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


abstract class BaseFragment<T : ViewBinding>(
    private val clazz: Class<T>,
    private val layoutResId: Int
) : BaseMvRxFragment() {

    var binding: T? = null
    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            val view = inflater.inflate(layoutResId, container, false)
            binding = bind(view)
        }
        context?.let {
            loadingDialog = LoadingDialog(it)
        }
        return binding?.root
    }

    fun setTitle(title: String) {
        (activity as? MainActivity)?.setTitle(title)
    }

    fun push(fragment: Fragment) {
        (activity as? MainActivity)?.push(fragment)
    }

    fun pop() {
        (activity as? MainActivity)?.pop()
    }

    fun <T : Any, VH : RecyclerView.ViewHolder> setPagingDataAdapterLoading(adapter: PagingDataAdapter<T, VH>) {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        loadingDialog.show()
                    }
                    else -> {
                        loadingDialog.dismiss()
                    }
                }
            }

        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : ViewBinding> bind(view: View): T {
        val bindMethod = clazz.getDeclaredMethod("bind", View::class.java)
        return bindMethod.invoke(null, view) as T
    }

}