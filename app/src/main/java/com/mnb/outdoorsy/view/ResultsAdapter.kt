package com.mnb.outdoorsy.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnb.outdoorsy.ListActions
import com.mnb.outdoorsy.R
import com.mnb.outdoorsy.models.ResultItem
import com.squareup.picasso.Picasso

class ResultsAdapter(val context: Context, val listActions: ListActions): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var resultsList = ArrayList<ResultItem>()

    // added this method so i could update the data set instead of creating a new adapter any
    // time the data set changes

    fun updateData(newList: ArrayList<ResultItem>) {
        resultsList.clear()
        resultsList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_item_layout, parent, false)
        val holder = ResultsViewHolder(listActions, view)
        return holder
    }

    override fun getItemCount(): Int {
        return resultsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val resultHolder = holder as ResultsViewHolder
        val resultItem = resultsList.get(position)
        resultHolder.setData(resultItem)
        resultHolder.resultText.text = resultItem.name
        // TODO: this seems slow, should investigate ways to optimize and add a placeholder
        Picasso.get().load(resultItem.imageUrl).into(resultHolder.resultImage);
    }
}