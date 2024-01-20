package com.bonge.traveltest.pagingDataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bonge.traveltest.web.Api
import com.bonge.traveltest.web.TravelResponse

class TravelPagingDataSource(
    private val api: Api,
    private val lang: String
) : PagingSource<Int, TravelResponse>() {
    override fun getRefreshKey(state: PagingState<Int, TravelResponse>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TravelResponse> {
        return try {
            val page = params.key ?: 1
            val list = api.getAttractions(lang, page).data
            val prePage = if (page > 1) page - 1 else null
            val nextPage = if (list.isNotEmpty()) page + 1 else null
            LoadResult.Page(list, prePage, nextPage)
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}