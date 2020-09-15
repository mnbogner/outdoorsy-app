package com.mnb.outdoorsy

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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

        // set up search entry
        val search = findViewById<EditText>(R.id.search_entry)
        // TODO: i don't think this is the best way to determine when to ingest text
        search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == null || event == null) {
                false
            } else if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)){
                val query = search.text.toString()
                doSearch(query)
                search.text.clear()
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val token = currentFocus?.windowToken
                if (manager != null && token != null) {
                    manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
                }
                true
            } else {
                false
            }
        }

        // set up recycler view, hold on to adapter to update with new data
        val recycler = findViewById<RecyclerView>(R.id.result_list)
        val manager = LinearLayoutManager(this)
        recycler.layoutManager = manager
        adapter = ResultsAdapter(this, this)
        recycler.adapter = adapter

        // set up view model
        model = ViewModelProvider(this, ResultsViewModelFactory(application)).get(ResultsViewModel::class.java)
        model?.resultsData?.observe(this, Observer { newList -> updateData(newList) })

    }

    // handle entered text and pass to search method
    private fun doSearch(string: String?) {
        if (string == null || string.isEmpty()) {
            // no-op
        } else {
            // TODO: validate, trim, clear bad characters, etc
            model?.search(string)
        }
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