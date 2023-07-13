package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sabanciuniv.tomofilyasprint1.R

class HomePageRecViewAdapter(private val context : Context, private val data: List<List<String>>) :
    RecyclerView.Adapter<HomePageRecViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.home_page_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.TextView.text = item[0].toString()
        Log.e("tag", "Text View Text: " + holder.TextView.text.toString())
        holder.TextView2.text = item[1].toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }



    inner class ViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView){
        val TextView : TextView = itemView.findViewById(R.id.result)
        val TextView2 : TextView = itemView.findViewById(R.id.category)

    }
}


