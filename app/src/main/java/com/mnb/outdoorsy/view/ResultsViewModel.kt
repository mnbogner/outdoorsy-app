package com.mnb.outdoorsy.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonParser
import com.mnb.outdoorsy.models.ResultData
import com.mnb.outdoorsy.models.ResultItem
import com.mnb.outdoorsy.network.OutdoorsyRetrofit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultsViewModel: ViewModel() {

    val retrofit = OutdoorsyRetrofit()
    val resultsData = MutableLiveData<ArrayList<ResultItem>>()

    fun search(keywords: String) {
        val call = retrofit.service.searchRentals(keywords)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                // TODO: error reporting
            }

            // this feels completely excessive but the data structure has so many layers i'm
            // honestly not sure how to build out a set of objects for gson/retrofit to do
            // the conversion automatically
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val dataMap = HashMap<Int, ResultData>()

                val responseElement = JsonParser().parse(response?.body()?.string());
                val responseObject = responseElement.asJsonObject
                val dataArray = responseObject.getAsJsonArray("data")
                val includedArray = responseObject.getAsJsonArray("included")

                // basic check for null results, return empty list
                if (dataArray == null || includedArray == null) {
                    val itemList = ArrayList<ResultItem>()
                    resultsData.value = itemList
                    return
                }

                // parse relevant items from data array
                for (dataItem in dataArray) {
                    val dataAttributes = dataItem.asJsonObject.get("attributes")
                    val dataName = dataAttributes.asJsonObject.get("name")
                    val dataRelationships = dataItem.asJsonObject.get("relationships")
                    val dataPrimary = dataRelationships.asJsonObject.get("primary_image")
                    val dataData = dataPrimary.asJsonObject.get("data")
                    val dataId = dataData.asJsonObject.get("id")
                    val result = ResultData(dataName.asString, dataId.asInt)
                    dataMap.put(result.id, result)
                }

                // parse relevant items from included array
                for (includedItem in includedArray) {
                    val includedId = includedItem.asJsonObject.get("id")
                    val includedType = includedItem.asJsonObject.get("type")
                    if (includedType.asString.equals("images") && dataMap.keys.contains(includedId.asInt)) {
                        val includedAttributes = includedItem.asJsonObject.get("attributes")
                        val includedUrl = includedAttributes.asJsonObject.get("url")
                        val matchingData = dataMap.get(includedId.asInt)
                        matchingData?.url = includedUrl.asString
                    } else {
                        // no-op
                    }
                }

                // convert results items to list items and update livedata
                val dataList = ArrayList<ResultData>(dataMap.values)
                val itemList = convertList(dataList)
                resultsData.value = itemList
            }
        })
    }

    // TODO: implement paged search

    fun convertList(dataList: ArrayList<ResultData>): ArrayList<ResultItem> {
        val itemList = ArrayList<ResultItem>()
        for (data in dataList) {
            val item = ResultItem(data.name, data.url ?: "")
            itemList.add(item)
        }
        return itemList
    }

    fun testData(count: Int) {
        val testList = ArrayList<ResultItem>()
        for (i in 0 until count) {
            val testItem = ResultItem("ITEM " + i, "url")
            testList.add(testItem)
        }
        resultsData.value = testList
    }
}