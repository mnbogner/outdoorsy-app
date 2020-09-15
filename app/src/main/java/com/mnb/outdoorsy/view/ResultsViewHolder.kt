package com.mnb.outdoorsy.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mnb.outdoorsy.ListActions
import com.mnb.outdoorsy.R
import com.mnb.outdoorsy.models.ResultItem

class ResultsViewHolder(val listActions: ListActions, itemView: View) : RecyclerView.ViewHolder(itemView) {

    // i'm pulling in an action interface and exposing the individual elements of the view to
    // keep this class small. i'm holding onto the corresponding data item so i don't have to
    // pull values out of the ui elements if i need to pass them to interface methods

    val resultImage: ImageView
    val resultText: TextView

    var resultItem: ResultItem? = null

    init {
        val item = itemView.findViewById<View>(R.id.search_item_container)
        item.setOnClickListener { listActions.doClick(resultItem?.name) }
        resultImage = itemView.findViewById(R.id.search_item_image)
        resultText = itemView.findViewById(R.id.search_item_text)
    }

    fun setData(resultItem: ResultItem) {
        this.resultItem = resultItem
    }
}