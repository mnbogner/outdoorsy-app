package com.mnb.outdoorsy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mnb.outdoorsy.models.ResultItem
import com.mnb.outdoorsy.view.ResultsAdapter
import com.mnb.outdoorsy.view.ResultsViewModel
import com.mnb.outdoorsy.view.ResultsViewModelFactory

class MainActivity : AppCompatActivity(), ListActions {

    var adapter: ResultsAdapter? = null
    var model: ResultsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // tweak ui to match specs
        window.statusBarColor = resources.getColor(R.color.darkerGreen, theme)
        supportActionBar?.hide()

        // set up recycler view, hold on to adapter to update with new data
        val recycler = findViewById<RecyclerView>(R.id.result_list)
        val manager = LinearLayoutManager(this)
        recycler.layoutManager = manager
        adapter = ResultsAdapter(this, this)
        recycler.adapter = adapter

        // set up view model
        model = ViewModelProvider(this, ResultsViewModelFactory(application)).get(ResultsViewModel::class.java)
        model?.resultsData?.observe(this, Observer { newList -> updateData(newList) })
        // TEMP: delete when actual data is available
        model?.testData(5)

    }

    // update recycler view adapter with new data
    private fun updateData(newList: ArrayList<ResultItem>) {
        adapter?.updateData(newList)
    }

    // simple placeholder for future list interactions
    override fun doClick(string: String?) {
        val output = string ?: "null"
        System.out.println("CLICK: " + output)
    }
}