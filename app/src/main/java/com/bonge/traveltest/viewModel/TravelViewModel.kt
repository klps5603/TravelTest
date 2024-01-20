package com.bonge.traveltest.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.bonge.traveltest.utils.PagingFlow
import com.bonge.traveltest.web.TravelResponse
import kotlinx.coroutines.flow.Flow

data class TravelState(
    val list: List<TravelResponse> = listOf(),
    val travel: TravelResponse? = null
) : MvRxState

class TravelViewModel(state: TravelState) : BaseMvRxViewModel<TravelState>(state, false) {

    fun getPagingData(lang: String): Flow<PagingData<TravelResponse>> {
        return PagingFlow.getPagingData(lang).cachedIn(viewModelScope)
    }

    fun setTravel(travel: TravelResponse) {
        setState {
            copy(travel = travel)
        }
    }

}