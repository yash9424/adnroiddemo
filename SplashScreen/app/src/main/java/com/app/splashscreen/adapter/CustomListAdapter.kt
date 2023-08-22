package com.app.splashscreen.adapter

import android.app.Activity
import android.content.ClipDescription
import android.content.Context
import android.media.Image
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.app.splashscreen.R

class CustomListAdapter(
    private val context: Activity,
    private val title: Array<String>,
    private val description: Array<String>,
    private val imageId: Array<Int>,
) : ArrayAdapter<String>(context, R.layout.item_list, title) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val view = inflater.inflate(R.layout.item_list, null, true)

        val titleText = view.findViewById(R.id.tvTitle) as TextView
        val descriptionText = view.findViewById(R.id.tvDescription) as TextView
        val imageView = view.findViewById(R.id.image) as ImageView

        titleText.text = title[position]
        descriptionText.text = description[position]
        imageView.setImageResource(imageId[position])

        return view
    }
}