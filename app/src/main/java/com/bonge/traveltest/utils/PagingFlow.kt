package com.bonge.traveltest.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bonge.traveltest.pagingDataSource.TravelPagingDataSource
import com.bonge.traveltest.web.Server
import com.bonge.traveltest.web.TravelResponse
import kotlinx.coroutines.flow.Flow

object PagingFlow {

    fun getPagingData(lang: String): Flow<PagingData<TravelResponse>> {
        return Pager(
            PagingConfig(30),
            pagingSourceFactory = {
                TravelPagingDataSource(Server.api, lang)
            }
        ).flow
    }
}