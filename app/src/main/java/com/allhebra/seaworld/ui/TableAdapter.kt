package com.allhebra.seaworld.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.allhebra.seaworld.R
import com.allhebra.seaworld.model.Cell
import com.allhebra.seaworld.model.CellType

class TableAdapter constructor(
    var items: ArrayList<Cell>,
    private val context: Context
) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    private val tag = TableAdapter::class.java.simpleName
    private var spanCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Log.d(tag, "onCreateViewHolder")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_item, parent, false)
        val width = parent.measuredWidth / spanCount
        v.layoutParams.height = width
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d(tag, "onBindViewHolder$position")
        when (items[position].cellType) {
            CellType.PENGUIN -> {
                holder.pic.background = context.getDrawable(R.drawable.penguin)
            }
            CellType.ORCA -> {
                holder.pic.background = context.getDrawable(R.drawable.orca)
            }
            CellType.EMPTY -> {
            }
        }
    }

    override fun getItemCount(): Int {
        //Log.d(tag, "item count:   " + items.size)
        return items.size
    }

    fun setSpanCount(spanCount: Int) {
        this.spanCount = spanCount
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pic: ImageView = itemView.findViewById(R.id.pic)
    }
}