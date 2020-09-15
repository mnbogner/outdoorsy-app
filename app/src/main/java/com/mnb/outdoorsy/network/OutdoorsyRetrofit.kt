package com.mnb.outdoorsy.network

import retrofit2.Retrofit

class OutdoorsyRetrofit {

    // split this into it's own class to isolate some of the details from other classes
    // TODO: this should probably be a singleton and wrap service methods

    val URL = "https://search.outdoorsy.co/"

    val service: OutdoorsyService

    init {
        val retrofit = Retrofit.Builder().baseUrl(URL).build()
        service = retrofit.create(OutdoorsyService::class.java)
    }
}