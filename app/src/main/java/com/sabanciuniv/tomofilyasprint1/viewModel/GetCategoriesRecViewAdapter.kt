package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sabanciuniv.tomofilyasprint1.R

class GetCategoriesRecViewAdapter(private val context: Context,
                                  private val data: List<List<String>>) :
    RecyclerView.Adapter<GetCategoriesRecViewAdapter.ViewHolder>()
{



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetCategoriesRecViewAdapter.ViewHolder {
    val view =
        LayoutInflater.from(context).inflate(R.layout.get_brands_view_holder, parent, false)
    return ViewHolder(view)
}

    override fun onBindViewHolder(holder: GetCategoriesRecViewAdapter.ViewHolder, position: Int) {
        val item = data[position]
        if (item.isNotEmpty()){
            Log.e("tag", "Item is: $item")
            holder.brand.text = item[0].toString()
            Log.e("tag", "Text View Text: " + holder.brand.text.toString())
            holder.amount.text = item[1].toString()

        }

    }

    override fun getItemCount(): Int {
        return data.size
    }




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brand: TextView = itemView.findViewById(R.id.brandHolder)
        val amount: TextView = itemView.findViewById(R.id.amountHolder)

    }


}