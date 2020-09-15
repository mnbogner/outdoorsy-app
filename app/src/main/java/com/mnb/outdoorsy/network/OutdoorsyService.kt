package com.mnb.outdoorsy.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OutdoorsyService {

    @GET("rentals")
    fun searchRentals(@Query("filter[keywords]") keywords: String): Call<ResponseBody>

    @GET("rentals")
    fun searchRentalsPaged(@Query("filter[keywords]") keywords: String,
                           @Query("page[limit]") limit: Int,
                           @Query("page[offset]") offset: Int): Call<ResponseBody>

}