package com.bonge.traveltest.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.airbnb.mvrx.activityViewModel
import com.bonge.traveltest.R
import com.bonge.traveltest.adapter.TravelAdapter
import com.bonge.traveltest.databinding.FragmentTravelBinding
import com.bonge.traveltest.utils.Language
import com.bonge.traveltest.utils.Util
import com.bonge.traveltest.viewModel.TravelViewModel
import com.bonge.traveltest.web.TravelResponse
import kotlinx.coroutines.flow.collect
import utils.launch
import utils.withMain

class TravelFragment : BaseFragment<FragmentTravelBinding>(
    FragmentTravelBinding::class.java,
    R.layout.fragment_travel
) {

    companion object {
        var language = Language.繁體中文
    }

    private val viewModel: TravelViewModel by activityViewModel()
    private val adapter = TravelAdapter()
    private val selectedLanguageDialog by lazy {
        val array = context?.resources?.getStringArray(R.array.language)
        val builder = AlertDialog.Builder(context)
        builder.setItems(array) { dialog, position ->
            language = Language.values()[position]
            Util.setLocale(context, language)
            activity?.recreate()
            dialog?.dismiss()
        }
        return@lazy builder.create()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        selectedLanguageDialog.show()
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(R.string.app_name))
        setHasOptionsMenu(true)

        adapter.setOnItemClickListener(object : TravelAdapter.OnItemClickListener {
            override fun onItemClick(travelResponse: TravelResponse) {
                launch {
                    viewModel.setTravel(travelResponse)
                    withMain {
                        push(TravelContentFragment())
                    }
                }
            }

        })

        binding?.recyclerView?.adapter = adapter
        getAttractions(language)
    }

    private fun getAttractions(language: Language) {
        setPagingDataAdapterLoading(adapter)
        launch {
            viewModel.getPagingData(language.value).collect {
                adapter.submitData(it)
            }
        }
    }

    override fun invalidate() {
    }
}