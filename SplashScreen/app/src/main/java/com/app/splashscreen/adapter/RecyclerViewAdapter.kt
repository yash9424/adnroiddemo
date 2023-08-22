package com.app.splashscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.splashscreen.R
import com.app.splashscreen.model.CustomModel

class RecyclerViewAdapter(val itemList: List<CustomModel>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ModelViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item_list, parent, false
        )
        return ModelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.imageView.setImageResource(itemList[position].image)
        holder.itemText.text = itemList[position].description
    }

    class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var constraintLayout: ConstraintLayout
        var imageView: ImageView
        var itemText: TextView

        init {
            constraintLayout = itemView.findViewById(R.id.constraint_layout_recycler_view)
            imageView = itemView.findViewById(R.id.image_view_recycler_view)
            itemText = itemView.findViewById(R.id.text_view_recycler_view)
        }
    }
}