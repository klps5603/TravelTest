package com.bonge.traveltest.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.airbnb.mvrx.BaseMvRxFragment
import com.bonge.traveltest.dialog.LoadingDialog


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


    @Suppress("UNCHECKED_CAST")
    private fun <T : ViewBinding> bind(view: View): T {
        val bindMethod = clazz.getDeclaredMethod("bind", View::class.java)
        return bindMethod.invoke(null, view) as T
    }

}