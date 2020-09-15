package com.mnb.outdoorsy.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnb.outdoorsy.models.ResultItem

class ResultsViewModel: ViewModel() {

    val resultsData = MutableLiveData<ArrayList<ResultItem>>()

    // TODO: attach network call with actual data

    fun testData(count: Int) {
        val testList = ArrayList<ResultItem>()
        for (i in 0 until count) {
            val testItem = ResultItem("ITEM " + i, "url")
            testList.add(testItem)
        }
        resultsData.value = testList
    }
}