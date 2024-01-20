package com.bonge.traveltest.web

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("{lang}/Attractions/All")
    suspend fun getAttractions(
        @Path("lang") lang: String,
        @Query("page") page: Int
    ): JsonResponse<List<TravelResponse>>

}